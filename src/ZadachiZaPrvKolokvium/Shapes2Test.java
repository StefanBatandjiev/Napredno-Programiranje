package src.ZadachiZaPrvKolokvium;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Shapes2Test {

    public static void main(String[] args) throws IrregularCanvasException {

        ShapesApplication shapesApplication = new ShapesApplication(10000);

        System.out.println("===READING CANVASES AND SHAPES FROM INPUT STREAM===");
        shapesApplication.readCanvases(System.in);

        System.out.println("===PRINTING SORTED CANVASES TO OUTPUT STREAM===");
        shapesApplication.printCanvases(System.out);


    }
}

class ShapesApplication{

    private double maxArea;
    private List<Canvas> canvases;

    public ShapesApplication(double maxArea) {
        this.maxArea = maxArea;
        this.canvases = new ArrayList<Canvas>();
    }

    public void readCanvases(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        while(scanner.hasNext()){
            String [] line = scanner.nextLine().split(" ");
            char [] types = new char[line.length/2];
            int [] sizes = new int[line.length/2];
            for(int i=1,t=0,s=0;i<line.length;i++){
                if(i%2==0){
                    sizes[s++] = Integer.parseInt(line[i]);
                }
                else types[t++] = line[i].charAt(0);
            }
            try {
                Canvas canvas = new Canvas(line[0], types, sizes);
                canvases.add(canvas);
            }catch (IrregularCanvasException e){
                e.message();
            }
        }
        scanner.close();
    }

    public void printCanvases(PrintStream printStream) {
        PrintWriter printWriter = new PrintWriter(printStream);
        Collections.sort(canvases);
        Collections.reverse(canvases);
        for(Canvas canvas : canvases){
            printWriter.printf("%s\n",canvas.toString());
        }
        printWriter.close();
    }
}

class Canvas implements Comparable<Canvas>{

    private String canvas_id;
    private char [] types;
    private int [] sizes;

    public Canvas(String canvas_id, char[] types, int[] sizes) throws IrregularCanvasException {
        this.canvas_id = canvas_id;
        this.types = types;
        this.sizes = sizes;
        if(maxArea()>10000)throw new IrregularCanvasException(canvas_id);
    }

    private double calculateArea(char type,int size){
        if (type == 'S') {
            return size*size;
        }
        return Math.PI * size * size;
    }

    private double minArea(){
        double minArea = 10000000;
        for (int i=0;i<types.length;i++){
            double pom = calculateArea(types[i],sizes[i]);
            if(pom<minArea){
                minArea = pom;
            }
        }
        return minArea;
    }

    public double maxArea(){
        double maxArea = 0;
        for (int i=0;i<types.length;i++){
            double pom = calculateArea(types[i],sizes[i]);
            if(pom>maxArea){
                maxArea = pom;
            }
        }
        return maxArea;
    }

    private double avgArea(){
        return sumArea() / types.length;
    }

    private int getSquaresNumber(){
        int counter = 0;
        for (char type : types){
            if (type == 'S')counter++;
        }
        return counter;
    }

    private double sumArea(){
        double sum = 0;
        for (int i=0;i<types.length;i++){
            sum += calculateArea(types[i],sizes[i]);
        }
        return sum;
    }

    @Override
    public String toString() {
        return String.format("%s %d %d %d %.2f %.2f %.2f",
                canvas_id,
                sizes.length,
                sizes.length-getSquaresNumber(),
                getSquaresNumber(),
                minArea(),
                maxArea(),
                avgArea());
    }

    @Override
    public int compareTo(Canvas o) {
        return (int) (this.sumArea() - o.sumArea());
    }
}
class IrregularCanvasException extends Exception{
    private String id;

    public IrregularCanvasException(String id) {
        this.id = id;
    }

    public void message(){
        System.out.println("Canvas "+id+" has a shape with area larger than 10000.00");
    }
}
