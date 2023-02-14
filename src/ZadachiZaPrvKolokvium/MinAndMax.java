package src.ZadachiZaPrvKolokvium;

import java.util.ArrayList;
import java.util.Scanner;

public class MinAndMax {
    public static void main(String[] args) throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        MinMax<String> strings = new MinMax<String>();
        for(int i = 0; i < n; ++i) {
            String s = scanner.next();
            strings.update(s);
        }
        System.out.println(strings);
        MinMax<Integer> ints = new MinMax<Integer>();
        for(int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            ints.update(x);
        }
        System.out.println(ints);
    }

    static class MinMax<T extends Comparable<T>>{

        private T min;
        private T max;
        private int counter;
        private int counterMin;
        private int counterMax;

        public MinMax() {
            this.counter = 0;
            this.counterMin = 0;
            this.counterMax = 0;
        }

        void update(T element){
            counter++;
            if (min == null && max == null){
                min = element;
                max = element;
            }
            if (min.compareTo(element)>0){
                min = element;
                counterMin = 0;
            }
            if(max.compareTo(element)<0){
                max = element;
                counterMax = 0;
            }
            if(min.compareTo(element)==0){
                counterMin++;
            }
            if(max.compareTo(element)==0){
                counterMax++;
            }
        }

        public T max(){
            return max;
        }

        public T min(){
            return min;
        }

        @Override
        public String toString() {
            return String.format(min + " " + max + " " + (counter-counterMin-counterMax) + "\n");
        }
    }
}