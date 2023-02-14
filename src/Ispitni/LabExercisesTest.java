package src.Ispitni;

import java.util.*;
import java.util.stream.Collectors;

class Student{

    private String index;
    private List<Integer> points;

    public Student(String index, List<Integer> points) {
        this.index = index;
        this.points = points;
    }

    public String getIndex() {
        return index;
    }

    public List<Integer> getPoints() {
        return points;
    }

    public double summaryPoints(){
        return this.points.stream().mapToInt(i -> i).sum()/10.0;
    }

    @Override
    public String toString() {
        return String.format("%s %s %.2f",
                this.index,
                this.points.size()>7?"YES":"NO",
                this.summaryPoints());
    }
}

class LabExercises{

    private Map<String,Student> students;

    public LabExercises() {
        this.students = new HashMap<>();
    }

    public void addStudent(Student student){
        this.students.putIfAbsent(student.getIndex(),student);
    }

    public void printByAveragePoints(boolean ascending, int n) {
        Comparator<Student> comparator = Comparator.comparing(Student::summaryPoints);
        if(!ascending){
            comparator = comparator.thenComparing(Student::getIndex).reversed();
        }else{
            comparator = comparator.thenComparing(Student::getIndex);
        }
        this.students.values().stream().sorted(comparator).limit(n).forEach(System.out::println);
    }


    public List<Student> failedStudents() {
        Comparator<Student> comparator = Comparator.comparing(Student::getIndex).thenComparing(Student::summaryPoints);
        return this.students.values()
                .stream()
                .filter(student -> student.getPoints().size()<8)
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public Map<Integer, Double> getStatisticsByYear() {
        return this.students.values()
                .stream()
                .filter(student -> student.getPoints().size()>7)
                .collect(Collectors.groupingBy(
                        student -> 20 - Integer.parseInt(student.getIndex().substring(0,2)),
                        Collectors.averagingDouble(Student::summaryPoints)
                ));
    }
}

public class LabExercisesTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LabExercises labExercises = new LabExercises();
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            String[] parts = input.split("\\s+");
            String index = parts[0];
            List<Integer> points = Arrays.stream(parts).skip(1)
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toList());

            labExercises.addStudent(new Student(index, points));
        }

        System.out.println("===printByAveragePoints (ascending)===");
        labExercises.printByAveragePoints(true, 100);
        System.out.println("===printByAveragePoints (descending)===");
        labExercises.printByAveragePoints(false, 100);
        System.out.println("===failed students===");
        labExercises.failedStudents().forEach(System.out::println);
        System.out.println("===statistics by year");
        labExercises.getStatisticsByYear().entrySet().stream()
                .map(entry -> String.format("%d : %.2f", entry.getKey(), entry.getValue()))
                .forEach(System.out::println);

    }
}
