package src.ZadachiZaPrvKolokvium;

import java.util.Scanner;

public class TripleTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
        System.out.printf("%.2f\n", tInt.max());
        System.out.printf("%.2f\n", tInt.average());
        tInt.sort();
        System.out.println(tInt);
        float fa = scanner.nextFloat();
        float fb = scanner.nextFloat();
        float fc = scanner.nextFloat();
        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
        System.out.printf("%.2f\n", tFloat.max());
        System.out.printf("%.2f\n", tFloat.average());
        tFloat.sort();
        System.out.println(tFloat);
        double da = scanner.nextDouble();
        double db = scanner.nextDouble();
        double dc = scanner.nextDouble();
        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
        System.out.printf("%.2f\n", tDouble.max());
        System.out.printf("%.2f\n", tDouble.average());
        tDouble.sort();
        System.out.println(tDouble);
    }
}
// vasiot kod ovde

class Triple<T extends Number> {
    private T number1;
    private T number2;
    private T number3;

    public Triple(T number1, T number2, T number3) {
        this.number1 = number1;
        this.number2 = number2;
        this.number3 = number3;
    }

    public double max() {
        double max = number1.doubleValue();
        if (number2.doubleValue() > max)
            max = number2.doubleValue();
        if (number3.doubleValue() > max)
            max = number3.doubleValue();
        return max;
    }

    public double sum() {
        return number1.doubleValue()
                + number2.doubleValue()
                + number3.doubleValue();
    }

    public double average() {
        return sum() / 3;
    }

    void sort() {
        if (number1.doubleValue() > number2.doubleValue()) {
            T tmp = number2;
            number2 = number1;
            number1 = tmp;
        }
        if (number2.doubleValue() > number3.doubleValue()) {
            T tmp = number3;
            number3 = number2;
            number2 = tmp;
        }
        if (number1.doubleValue() > number2.doubleValue()) {
            T tmp = number2;
            number2 = number1;
            number1 = tmp;
        }
    }

    @Override
    public String toString() {
        return String.format("%.2f %.2f %.2f",
                number1.doubleValue(),
                number2.doubleValue(),
                number3.doubleValue());
    }
}
