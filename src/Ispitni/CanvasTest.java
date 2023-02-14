package src.Ispitni;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class InvalidIDException extends Exception{

    public InvalidIDException(String id) {
        super(String.format("ID %s is not valid\n",id));
    }
}

class InvalidDimensionsException extends Exception{

    public InvalidDimensionsException() {
        super(String.format("Dimension 0 is not allowed!\n"));
    }
}

enum ShapeType{
    CIRCLE,
    SQUARE,
    RECTANGLE
}

abstract class Shape implements Comparable<Shape>{
    private ShapeType type;
    private String userId;

    public Shape(ShapeType type, String userId) {
        this.type = type;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    abstract double area();
    abstract double perimeter();
    abstract void scale(double coef);
    public abstract String toString();
}

class Circle extends Shape{

    private double radius;

    public Circle(ShapeType type, String userId, double radius) {
        super(type, userId);
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * Math.pow(radius,2);
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public void scale(double coef) {
        this.radius *= coef;
    }

    @Override
    public String toString() {
        return String.format("Circle -> Radius: %.2f Area: %.2f Perimeter: %.2f",
                this.radius,
                area(),
                perimeter());
    }

    @Override
    public int compareTo(Shape o) {
        return Double.compare(this.area(),o.area());
    }
}

class Square extends Shape{

    private double a;

    public Square(ShapeType type, String userId, double a) {
        super(type, userId);
        this.a = a;
    }

    @Override
    public double area() {
        return Math.pow(a,2);
    }

    @Override
    public double perimeter() {
        return 4 * a;
    }

    @Override
    public void scale(double coef) {
        this.a *= coef;
    }

    @Override
    public String toString() {
        return String.format("Square -> Side: %.2f Area: %.2f Perimeter: %.2f",
                this.a,
                area(),
                perimeter());
    }

    @Override
    public int compareTo(Shape o) {
        return Double.compare(this.area(),o.area());
    }
}

class Rectangle extends Shape{

    private double a;
    private double b;

    public Rectangle(ShapeType type, String userId, double a, double b) {
        super(type, userId);
        this.a = a;
        this.b = b;
    }

    @Override
    public double area() {
        return a * b;
    }

    @Override
    public double perimeter() {
        return 2 * a + 2 * b;
    }

    @Override
    public void scale(double coef) {
        this.a *= coef;
        this.b *= coef;
    }

    @Override
    public String toString() {
        return String.format("Rectangle -> Sides: %.2f, %.2f Area: %.2f Perimeter: %.2f",
                this.a,
                this.b,
                area(),
                perimeter());
    }

    @Override
    public int compareTo(Shape o) {
        return Double.compare(this.area(),o.area());
    }
}

class Canvas{

    private final Map<String,Set<Shape>> shapesPerUser;
    private final Set<Shape> shapes;
    private List<String> users;

    public Canvas() {
        this.shapesPerUser = new TreeMap<>();
        this.shapes= new TreeSet<>();
        this.users = new ArrayList<>();
    }

    private static boolean checkId(String id){
        for (Character c : id.toCharArray()){
            if(!Character.isDigit(c)&&!Character.isAlphabetic(c)){
                return false;
            }
        }
        return true;
    }

    public void readShapes(InputStream is) throws InvalidIDException, InvalidDimensionsException {
        Scanner scanner = new Scanner(is);
        while(scanner.hasNextLine()){
            String [] parts = scanner.nextLine().split("\\s");
            String type = parts[0];
            String userId = parts[1];
            double dimension = Double.parseDouble(parts[2]);
            if(userId.length()!=6 && !checkId(userId)){
                throw new InvalidIDException(userId);
            }
            Set<Shape> set = this.shapesPerUser.computeIfAbsent(userId, k -> new TreeSet<>());
            this.users.add(userId);
            if(dimension==0){
                throw new InvalidDimensionsException();
            }
            Shape shape;
            if(type.equals("1")){
                shape = new Circle(ShapeType.CIRCLE,userId,dimension);
            }else if(type.equals("2")){
                shape = new Square(ShapeType.SQUARE,userId,dimension);
            }else{
                double dimension2 = Double.parseDouble(parts[3]);
                if(dimension2==0){
                    throw new InvalidDimensionsException();
                }
                shape = new Rectangle(ShapeType.RECTANGLE,userId,dimension,dimension2);
            }
            this.shapes.add(shape);
            set.add(shape);
        }
    }

    public void scaleShapes(String userId, double coef){
        this.shapesPerUser.get(userId).forEach(shape -> shape.scale(coef));
    }

    public void printAllShapes(OutputStream os){
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(os));
        this.shapes.forEach(shape -> printWriter.println(shape.toString()));
        printWriter.flush();
    }

    public void printByUserId(OutputStream os){
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(os));
        Map<String,Set<Shape>> byUserId = this.shapesPerUser.values()
                .stream()
                .sorted(Comparator.comparing(Set::size))
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(Shape::area))
                .collect(Collectors.groupingBy(
                        Shape::getUserId,
                        Collectors.toSet()
                ));
        for (Map.Entry<String,Set<Shape>> entry:byUserId.entrySet()){
            printWriter.printf("Shapes of user: %s\n",entry.getKey());
            entry.getValue().forEach(printWriter::println);
        }
        printWriter.flush();
    }

    public void statistics(PrintStream out) {
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(out));
        DoubleSummaryStatistics summaryStatistics = this.shapes.stream().mapToDouble(Shape::area).summaryStatistics();
        printWriter.printf("count: %d\nsum: %.2f\nmin: %.2f\naverage: %.2f\nmax: %.2f",
                summaryStatistics.getCount(),
                summaryStatistics.getSum(),
                summaryStatistics.getMin(),
                summaryStatistics.getAverage(),
                summaryStatistics.getMax());
        printWriter.flush();
    }
}

public class CanvasTest {

    public static void main(String[] args) {
        Canvas canvas = new Canvas();

        System.out.println("READ SHAPES AND EXCEPTIONS TESTING");
        try {
            canvas.readShapes(System.in);
        } catch (InvalidIDException | InvalidDimensionsException e) {
            e.printStackTrace();
        }

        System.out.println("BEFORE SCALING");
        canvas.printAllShapes(System.out);
        canvas.scaleShapes("123456", 1.5);
        System.out.println("AFTER SCALING");
        canvas.printAllShapes(System.out);

        System.out.println("PRINT BY USER ID TESTING");
        canvas.printByUserId(System.out);

        System.out.println("PRINT STATISTICS");
        canvas.statistics(System.out);
    }
}
