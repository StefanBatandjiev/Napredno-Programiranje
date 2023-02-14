package src.ZadachiZaPrvKolokvium;

import java.util.Scanner;

public class GenericFractionTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double n1 = scanner.nextDouble();
        double d1 = scanner.nextDouble();
        float n2 = scanner.nextFloat();
        float d2 = scanner.nextFloat();
        int n3 = scanner.nextInt();
        int d3 = scanner.nextInt();
        try {
            GenericFraction<Double, Double> gfDouble = new GenericFraction<Double, Double>(n1, d1);
            GenericFraction<Float, Float> gfFloat = new GenericFraction<Float, Float>(n2, d2);
            GenericFraction<Integer, Integer> gfInt = new GenericFraction<Integer, Integer>(n3, d3);
            System.out.printf("%.2f\n", gfDouble.toDouble());
            System.out.println(gfDouble.add(gfFloat));
            System.out.println(gfInt.add(gfFloat));
            System.out.println(gfDouble.add(gfInt));
            gfInt = new GenericFraction<Integer, Integer>(n3, 0);
        } catch(ZeroDenominatorException e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }

}

// вашиот код овде

class GenericFraction<T extends Number,U extends Number>{
    private final T numerator;
    private final U denominator;


    GenericFraction(T numerator, U denominator) throws ZeroDenominatorException {
        if (denominator.doubleValue()==0) throw new ZeroDenominatorException();
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public GenericFraction<Double,Double> add(GenericFraction<? extends Number, ? extends Number> element) throws ZeroDenominatorException {
        return new GenericFraction<Double,Double>
                (this.numerator.doubleValue()*element.denominator.doubleValue()
                        +element.numerator.doubleValue()*this.denominator.doubleValue(),
                this.denominator.doubleValue()*element.denominator.doubleValue());
    }

    public double toDouble(){
        return numerator.doubleValue()/denominator.doubleValue();
    }

    static double gcd(double a, double b) {
        if (b == 0)
            return a;
        if (a < b)
            return gcd(a, b - a);
        else
            return gcd(b, a - b);
    }

    @Override
    public String toString() {
        double gcd = gcd(this.numerator.doubleValue(),this.denominator.doubleValue());
        return String.format("%.2f / %.2f",
                numerator.doubleValue()/gcd,
                denominator.doubleValue()/gcd);
    }
}

class ZeroDenominatorException extends Exception{
    public ZeroDenominatorException() {
        super("Denominator cannot be zero");
    }
}