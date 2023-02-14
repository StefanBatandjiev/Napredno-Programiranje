package src.ZadachiZaPrvKolokvium;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class DailyTemperatureTest {
    public static void main(String[] args) {
        DailyTemperatures dailyTemperatures = new DailyTemperatures();
        dailyTemperatures.readTemperatures(System.in);
        System.out.println("=== Daily temperatures in Celsius (C) ===");
        dailyTemperatures.writeDailyStats(System.out, 'C');
        System.out.println("=== Daily temperatures in Fahrenheit (F) ===");
        dailyTemperatures.writeDailyStats(System.out, 'F');
    }
}

enum TempType{
    C,F
}

class Temp{
    private double temp;
    private TempType tempType;

    public Temp(double temp, TempType tempType) {
        this.temp = temp;
        this.tempType = tempType;
    }

    public Temp(double temp){
        this.temp = temp;
    }

    public double getTemp(TempType type) {
        if(!this.tempType.equals(type)&&type.equals(TempType.C)){
            this.temp = ((this.temp - 32)*5)/9;
        }
        else if(!this.tempType.equals(type)&&type.equals(TempType.F)){
            this.temp = (this.temp*9)/5 +32;
        }
        return this.temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public TempType getTempType() {
        return tempType;
    }

    public void setTempType(TempType tempType) {
        this.tempType = tempType;
    }

    @Override
    public String toString() {
        return String.format("%f%c",temp, tempType);
    }
}

class DayTemp{
    private final int day;
    private final List<Temp> temps;

    public DayTemp(int day, List<Temp> temps) {
        this.day = day;
        this.temps = temps;
    }

    public int getDay() {
        return day;
    }

    public List<Temp> getTemps() {
        return temps;
    }

    public static DayTemp create(String line) {
        String [] parts = line.split("\\s+");
        int day = Integer.parseInt(parts[0]);
        List<Temp> tmps = new ArrayList<>();
        Arrays.stream(parts)
                .skip(1)
                .forEach(i -> {
                    char type = i.charAt(i.length()-1);
                    tmps.add(new Temp(Double.parseDouble(i.substring(0,i.length()-1)),TempType.valueOf(String.valueOf(type))));
                });
        return new DayTemp(day,tmps);
    }

    public double tempSum(TempType type){
        return this.temps.stream().mapToDouble(i -> i.getTemp(type)).sum();
    }
}

class DailyTemperatures{

    private List<DayTemp> dayTempList;

    public DailyTemperatures() {
        this.dayTempList = new ArrayList<DayTemp>();
    }

    public void readTemperatures(InputStream in) {
        dayTempList = new BufferedReader(new InputStreamReader(in))
                .lines()
                .map(DayTemp::create)
                .collect(Collectors.toList());
    }

    public void writeDailyStats(PrintStream out, char c) {
        PrintWriter printWriter = new PrintWriter(out);
        TempType type = TempType.valueOf(String.valueOf(c));
        DoubleSummaryStatistics summaryStatistics = dayTempList.stream()
                                    .mapToDouble(i -> i.tempSum(TempType.valueOf(String.valueOf(c)))).summaryStatistics();
        //11: Count:   7 Min:  38.33C Max:  40.56C Avg:  39.44C
        dayTempList.stream().forEach(i -> printWriter.println(String.format("%d: Count:\t %d Min:\t %.2f%c Max:\t %.2f%c Avg:\t %.2f%c",
                                    i.getDay(),
                                    summaryStatistics.getCount(),
                                    summaryStatistics.getMin(),c,
                                    summaryStatistics.getMax(),c,
                                    summaryStatistics.getAverage(),c)));
        printWriter.flush();
    }
}
