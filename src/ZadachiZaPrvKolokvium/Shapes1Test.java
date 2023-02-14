//package src.ZadachiZaPrvKolokvium;
//
//import java.awt.*;
//import java.io.*;
//import java.util.*;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class Shapes1Test {
//
//    public static void main(String[] args) {
//        ShapesApplication shapesApplication = new ShapesApplication();
//
//        System.out.println("===READING SQUARES FROM INPUT STREAM===");
//        System.out.println(shapesApplication.readCanvases(System.in));
//        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
//        shapesApplication.printLargestCanvasTo(System.out);
//
//    }
//}
//class ShapesApplication{
//
//    private List<Canvas> canvases;
//
//    public ShapesApplication() {
//        this.canvases = new ArrayList<>();
//    }
//
//    public int readCanvases (InputStream inputStream){
//        canvases = new BufferedReader(new InputStreamReader(inputStream))
//                .lines()
//                .map(Canvas::create)
//                .collect(Collectors.toList());
////        Scanner scanner = new Scanner(inputStream);
////        int counter = 0;
////        while(scanner.hasNext()){
////            String [] line = scanner.nextLine().split(" ");
////            int [] sizes = new int[line.length-1];
////            for(int i=1,j=0;i<line.length;i++,j++){
////                sizes[j] = Integer.parseInt(line[i]);
////                counter++;
////            }
////            Canvas canvas = new Canvas(line[0],sizes);
////            canvases.add(canvas);
////        }
////        scanner.close();
////        return counter;
//        return Canvas.getCounter();
//    }
//
//    public void printLargestCanvasTo(OutputStream outputStream) {
//        PrintWriter printWriter = new PrintWriter(outputStream);
//        printWriter.println(canvases.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).get(0));
//        printWriter.flush();
////        Collections.sort(canvases);
////        Collections.reverse(canvases);
////        printWriter.printf("%s",canvases.get(0).toString());
////        printWriter.close();
//    }
//}
//class Canvas implements Comparable<Canvas>{
//
//    private String canvas_id;
//    private  int [] sizes;
//    private static int counter = 0;
//
//    public Canvas(String canvas_id, int[] sizes) {
//        this.canvas_id = canvas_id;
//        this.sizes = sizes;
//    }
//
//    public static Canvas create(String line){
//        String [] parts = line.split("\\s+");
//        int [] sizes = new int[parts.length-1];
//        for(int i=1,j=0;i<parts.length;i++,j++){
//            sizes[j] = Integer.parseInt(parts[i]);
//            Canvas.counter +=1;
//        }
//        return new Canvas(parts[0],sizes);
//    }
//
//    private int totalPerimetar() {
//        int sum = 0;
//        for (int i=0;i<sizes.length;i++){
//            sum += sizes[i];
//        }
//        return sum*4;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("%s %d %d",canvas_id,sizes.length,totalPerimetar());
//    }
//
//
//    @Override
//    public int compareTo(Canvas o) {
//        return this.totalPerimetar()-o.totalPerimetar();
//    }
//
//    public static int getCounter() {
//        return counter;
//    }
//}