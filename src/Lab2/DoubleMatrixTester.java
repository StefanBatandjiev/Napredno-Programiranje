//package src.Lab2;
//
//import java.io.*;
//import java.text.DecimalFormat;
//import java.util.*;
//
//public class DoubleMatrixTester {
//
//    public static void main(String[] args) throws Exception {
//        Scanner scanner = new Scanner(System.in);
//
//        int tests = scanner.nextInt();
//        DoubleMatrix fm = null;
//
//        double[] info = null;
//
//        DecimalFormat format = new DecimalFormat("0.00");
//
//        for (int t = 0; t < tests; t++) {
//
//            String operation = scanner.next();
//
//            switch (operation) {
//                case "READ": {
//                    int N = scanner.nextInt();
//                    int R = scanner.nextInt();
//                    int C = scanner.nextInt();
//
//                    double[] f = new double[N];
//
//                    for (int i = 0; i < f.length; i++)
//                        f[i] = scanner.nextDouble();
//
//                    try {
//                        fm = new DoubleMatrix(f, R, C);
//                        info = Arrays.copyOf(f, f.length);
//
//                    } catch (InsufficientElementsException e) {
//                        System.out.println("Exception caught: " + e.getMessage());
//                    }
//
//                    break;
//                }
//
//                case "INPUT_TEST": {
//                    int R = scanner.nextInt();
//                    int C = scanner.nextInt();
//
//                    StringBuilder sb = new StringBuilder();
//
//                    sb.append(R + " " + C + "\n");
//
//                    scanner.nextLine();
//
//                    for (int i = 0; i < R; i++)
//                        sb.append(scanner.nextLine() + "\n");
//
//                    fm = MatrixReader.read(new ByteArrayInputStream(sb
//                            .toString().getBytes()));
//
//                    info = new double[R * C];
//                    Scanner tempScanner = new Scanner(new ByteArrayInputStream(sb
//                            .toString().getBytes()));
//                    tempScanner.nextDouble();
//                    tempScanner.nextDouble();
//                    for (int z = 0; z < R * C; z++) {
//                        info[z] = tempScanner.nextDouble();
//                    }
//
//                    tempScanner.close();
//
//                    break;
//                }
//
//                case "PRINT": {
//                    System.out.println(fm.toString());
//                    break;
//                }
//
//                case "DIMENSION": {
//                    System.out.println("Dimensions: " + fm.getDimensions());
//                    break;
//                }
//
//                case "COUNT_ROWS": {
//                    System.out.println("Rows: " + fm.rows());
//                    break;
//                }
//
//                case "COUNT_COLUMNS": {
//                    System.out.println("Columns: " + fm.columns());
//                    break;
//                }
//
//                case "MAX_IN_ROW": {
//                    int row = scanner.nextInt();
//                    try {
//                        System.out.println("Max in row: "
//                                + format.format(fm.maxElementAtRow(row)));
//                    } catch (InvalidRowNumberException e) {
//                        System.out.println("Exception caught: " + e.getMessage());
//                    }
//                    break;
//                }
//
//                case "MAX_IN_COLUMN": {
//                    int col = scanner.nextInt();
//                    try {
//                        System.out.println("Max in column: "
//                                + format.format(fm.maxElementAtColumn(col)));
//                    } catch (InvalidColumnNumberException e) {
//                        System.out.println("Exception caught: " + e.getMessage());
//                    }
//                    break;
//                }
//
//                case "SUM": {
//                    System.out.println("Sum: " + format.format(fm.sum()));
//                    break;
//                }
//
//                case "CHECK_EQUALS": {
//                    int val = scanner.nextInt();
//
//                    int maxOps = val % 7;
//
//                    for (int z = 0; z < maxOps; z++) {
//                        double work[] = Arrays.copyOf(info, info.length);
//
//                        int e1 = (31 * z + 7 * val + 3 * maxOps) % info.length;
//                        int e2 = (17 * z + 3 * val + 7 * maxOps) % info.length;
//
//                        if (e1 > e2) {
//                            double temp = work[e1];
//                            work[e1] = work[e2];
//                            work[e2] = temp;
//                        }
//
//                        DoubleMatrix f1 = fm;
//                        DoubleMatrix f2 = new DoubleMatrix(work, fm.rows(),
//                                fm.columns());
//                        System.out
//                                .println("Equals check 1: "
//                                        + f1.equals(f2)
//                                        + " "
//                                        + f2.equals(f1)
//                                        + " "
//                                        + (f1.hashCode() == f2.hashCode()&&f1
//                                        .equals(f2)));
//                    }
//
//                    if (maxOps % 2 == 0) {
//                        DoubleMatrix f1 = fm;
//                        DoubleMatrix f2 = new DoubleMatrix(new double[]{3.0, 5.0,
//                                7.5}, 1, 1);
//
//                        System.out
//                                .println("Equals check 2: "
//                                        + f1.equals(f2)
//                                        + " "
//                                        + f2.equals(f1)
//                                        + " "
//                                        + (f1.hashCode() == f2.hashCode() && f1
//                                        .equals(f2)));
//                    }
//
//                    break;
//                }
//
//                case "SORTED_ARRAY": {
//                    double[] arr = fm.toSortedArray();
//
//                    String arrayString = "[";
//
//                    if (arr.length > 0)
//                        arrayString += format.format(arr[0]) + "";
//
//                    for (int i = 1; i < arr.length; i++)
//                        arrayString += ", " + format.format(arr[i]);
//
//                    arrayString += "]";
//
//                    System.out.println("Sorted array: " + arrayString);
//                    break;
//                }
//
//            }
//
//        }
//
//        scanner.close();
//    }
//
//    static final class DoubleMatrix{
//
//        private double [] [] matrix;
//        private double [] a;
//        private int n;
//        private int m;
//
//        public DoubleMatrix(double[] a, int n, int m) {
//            this.a = a;
//            this.n = n;
//            this.m = m;
//        }
//
//        public String getDimensions(){
//            return String.format("[%d x x%d]",n,m);
//        }
//        public int rows(){
//            return n;
//        }
//        public int columns(){
//            return m;
//        }
//        public double maxElementAtRow(int row){
//            if(row>n)throw new InvalidRowNumberException();
//            double maxNum = 0;
//            for(int i=0;i<m;i++){
//                if(matrix[row][i]>maxNum){
//                    maxNum = matrix[row][i];
//                }
//            }
//            return maxNum;
//        }
//        public double maxElementAtColumn(int column){
//            if(column>m)throw new InvalidColumnNumberException();
//            double maxNum = 0;
//            for(int i=0;i<n;i++){
//                if(matrix[i][column]>maxNum){
//                    maxNum = matrix[i][column];
//                }
//            }
//            return maxNum;
//        }
//        public double sum(){
//            double sum = 0;
//            for(int i=0;i<n;i++){
//                for(int j=0;j<m;j++){
//                    sum += matrix[i][j];
//                }
//            }
//            return sum;
//        }
//        public double [] toSortedArray(){
//            Arrays.sort(a);
//            final Comparator<T> tComparator = Collections.reverseOrder(a);
//            return a;
//        }
//        public String toString(){
//            StringBuilder stringBuilder = new StringBuilder();
//            for(int i=0;i<n;i++){
//                for(int j=0;j<m;j++){
//                    stringBuilder.append(String.format("%.2f\t",matrix[i][j]));
//                }
//                stringBuilder.append("\n");
//            }
//            return stringBuilder.toString();
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            DoubleMatrix that = (DoubleMatrix) o;
//            return n == that.n && m == that.m && Arrays.equals(matrix, that.matrix) && Arrays.equals(a, that.a);
//        }
//
//        @Override
//        public int hashCode() {
//            int result = Objects.hash(n, m);
//            result = 31 * result + Arrays.hashCode(matrix);
//            result = 31 * result + Arrays.hashCode(a);
//            return result;
//        }
//    }
//    class InsufficientElementsException extends Exception{
//        public InsufficientElementsException() {
//            super("Insufficient number of elements");
//        }
//    }
//    static class InvalidRowNumberException extends Exception{
//        public InvalidRowNumberException() {
//            super("Invalid row number");
//        }
//    }
//    static class InvalidColumnNumberException extends Exception{
//        public InvalidColumnNumberException() {
//            super("Invalid column number");
//        }
//    }
//    static class MatrixReader{
//
//        public static DoubleMatrix read(InputStream inputStream) throws IOException {
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            String [] pom = bufferedReader.readLine().split(" ");
//            int n = Integer.parseInt(pom[0]);
//            int m = Integer.parseInt(pom[1]);
//            double [] array = new double[n+m];
//            int j = 0;
//            for(int i=0;i<n;i++){
//                pom = bufferedReader.readLine().split(" ");
//                for(int k=0;k< pom.length;k++){
//                    array[j++] = Double.parseDouble(pom[k]);
//                }
//            }
//            return new DoubleMatrix(array,n,m);
//        }
//    }
//}
