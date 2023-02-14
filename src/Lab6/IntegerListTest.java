//package src.Lab6;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//class IntegerList{
//
//    private List<Integer> list;
//
//    public IntegerList() {
//        list = new ArrayList<>();
//    }
//
//    public void add(int element, int index) {
//        if(list.get(index)==null){
//            list.add(index,element);
//        }
//        else{
//
//        }
//    }
//
//    public int remove(int index) {
//        return list.remove(index);
//    }
//
//    public int count(int element) {
//    }
//
//    public void removeDuplicates() {
//    }
//
//    public IntegerList addValue(int nextInt) {
//    }
//
//    public void shiftLeft(int nextInt, int nextInt1) {
//    }
//
//    public void shiftRight(int nextInt, int nextInt1) {
//    }
//
//    public boolean sumLast(int nextInt) {
//    }
//
//    public int size() {
//        return list.size();
//    }
//
//    public Integer get(int index) {
//        return list.get(index);
//    }
//
//    public void set(int element, int index){
//        list.set(index, element);
//    }
//
//    public boolean sumFirst(int nextInt) {
//    }
//}
//
//public class IntegerListTest {
//
//    public static void main(String[] args) {
//        Scanner jin = new Scanner(System.in);
//        int k = jin.nextInt();
//        if ( k == 0 ) { //test standard methods
//            int subtest = jin.nextInt();
//            if ( subtest == 0 ) {
//                IntegerList list = new IntegerList();
//                while ( true ) {
//                    int num = jin.nextInt();
//                    if ( num == 0 ) {
//                        list.add(jin.nextInt(), jin.nextInt());
//                    }
//                    if ( num == 1 ) {
//                        list.remove(jin.nextInt());
//                    }
//                    if ( num == 2 ) {
//                        print(list);
//                    }
//                    if ( num == 3 ) {
//                        break;
//                    }
//                }
//            }
//            if ( subtest == 1 ) {
//                int n = jin.nextInt();
//                Integer a[] = new Integer[n];
//                for ( int i = 0 ; i < n ; ++i ) {
//                    a[i] = jin.nextInt();
//                }
//                IntegerList list = new IntegerList(a);
//                print(list);
//            }
//        }
//        if ( k == 1 ) { //test count,remove duplicates, addValue
//            int n = jin.nextInt();
//            Integer a[] = new Integer[n];
//            for ( int i = 0 ; i < n ; ++i ) {
//                a[i] = jin.nextInt();
//            }
//            IntegerList list = new IntegerList(a);
//            while ( true ) {
//                int num = jin.nextInt();
//                if ( num == 0 ) { //count
//                    System.out.println(list.count(jin.nextInt()));
//                }
//                if ( num == 1 ) {
//                    list.removeDuplicates();
//                }
//                if ( num == 2 ) {
//                    print(list.addValue(jin.nextInt()));
//                }
//                if ( num == 3 ) {
//                    list.add(jin.nextInt(), jin.nextInt());
//                }
//                if ( num == 4 ) {
//                    print(list);
//                }
//                if ( num == 5 ) {
//                    break;
//                }
//            }
//        }
//        if ( k == 2 ) { //test shiftRight, shiftLeft, sumFirst , sumLast
//            int n = jin.nextInt();
//            Integer a[] = new Integer[n];
//            for ( int i = 0 ; i < n ; ++i ) {
//                a[i] = jin.nextInt();
//            }
//            IntegerList list = new IntegerList(a);
//            while ( true ) {
//                int num = jin.nextInt();
//                if ( num == 0 ) { //count
//                    list.shiftLeft(jin.nextInt(), jin.nextInt());
//                }
//                if ( num == 1 ) {
//                    list.shiftRight(jin.nextInt(), jin.nextInt());
//                }
//                if ( num == 2 ) {
//                    System.out.println(list.sumFirst(jin.nextInt()));
//                }
//                if ( num == 3 ) {
//                    System.out.println(list.sumLast(jin.nextInt()));
//                }
//                if ( num == 4 ) {
//                    print(list);
//                }
//                if ( num == 5 ) {
//                    break;
//                }
//            }
//        }
//    }
//
//    public static void print(IntegerList il) {
//        if ( il.size() == 0 ) System.out.print("EMPTY");
//        for ( int i = 0 ; i < il.size() ; ++i ) {
//            if ( i > 0 ) System.out.print(" ");
//            System.out.print(il.get(i));
//        }
//        System.out.println();
//    }
//
//}