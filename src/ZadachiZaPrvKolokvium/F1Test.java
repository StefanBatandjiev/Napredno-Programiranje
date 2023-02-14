package src.ZadachiZaPrvKolokvium;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class F1Test {

    public static void main(String[] args) {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }
}

class F1Race {
    // vashiot kod ovde
    private List<Driver> drivers;

    public F1Race() {
        this.drivers = new ArrayList<>();
    }

    public void readResults(InputStream input) {
        drivers = new BufferedReader(new InputStreamReader(input))
                .lines().map(Driver::create).collect(Collectors.toList());
//        Scanner scanner = new Scanner(input);
//        while (scanner.hasNext()) {
//            String [] line = scanner.nextLine().split(" ");
//            Driver driver = new Driver(line[0],Driver.stringToTime(line[1]),Driver.stringToTime(line[2]),Driver.stringToTime(line[3]));
//            drivers.add(driver);
//        }
//        scanner.close();
    }

    public void printSorted(OutputStream output) {
        PrintWriter printWriter = new PrintWriter(output);
        drivers.stream().sorted().forEach(i -> printWriter.printf(i.toString()));
        printWriter.flush();
//        Collections.sort(drivers);
//        int i =1;
//        for(Driver driver : drivers){
//            printWriter.printf("%d. %s\n",i++,driver.toString());
//        }
//        printWriter.close();
    }
}
class Driver implements Comparable<Driver>{

    private String Driver_name;
    private int lap1;
    private int lap2;
    private int lap3;
    private int bestLap;
    private static int driverNum=0;

    public Driver(String driver_name, int lap1, int lap2, int lap3) {
        Driver_name = driver_name;
        this.lap1 = lap1;
        this.lap2 = lap2;
        this.lap3 = lap3;
        this.bestLap = Math.min(Math.min(lap1,lap2),lap3);
    }

    public static Driver create(String line) {
        String [] parts = line.split("\\s+");
        return new Driver(parts[0],stringToTime(parts[1]),stringToTime(parts[2]),stringToTime(parts[3]));
    }

    public static int stringToTime(String time){
        String [] pom = time.split(":");
        return Integer.parseInt(pom[0])*60*1000+Integer.parseInt(pom[1])*1000+Integer.parseInt(pom[2]);
    }

    public static String timeToString(int time){
        int min = time / 1000 / 60;
        int sec = (time - min  * 1000 * 60 )/ 1000;
        int ms = time % 1000;
        return String.format("%d:%02d:%03d",min,sec,ms);
    }

    @Override
    public String toString() {
        return String.format("%d. %-10s%10s\n",Driver.driverNum+=1, Driver_name, timeToString(bestLap));
    }

    @Override
    public int compareTo(Driver o) {
        return this.bestLap - o.bestLap;
    }
}




