package src.ZadachiZaPrvKolokvium;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class TimesTest {

    public static void main(String[] args) {
        TimeTable timeTable = new TimeTable();
        timeTable.readTimes(System.in);
        System.out.println("24 HOUR FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_24);
        System.out.println("AM/PM FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_AMPM);
    }

}

enum TimeFormat {
    FORMAT_24, FORMAT_AMPM
}

class UnsupportedFormatException extends Exception{
    private String line;

    public UnsupportedFormatException(String line) {
        this.line = line;
    }

    public String getMessage() {
        return String.format("UnsupportedFormatException: %s",line);
    }
}

class InvalidTimeException extends Exception{
    private String line;

    public InvalidTimeException(String line) {
        this.line = line;
    }

    public String getMessage() {
        return String.format("InvalidTimeException: %s",line);
    }
}

class TimeValue implements Comparable<TimeValue>{
    private int hours;
    private int minutes;

    public TimeValue(String line) throws UnsupportedFormatException, InvalidTimeException {
        String [] tmp = line.split("\\.");;
        if(tmp.length == 1) {
            tmp = line.split(":");
        }
        if(tmp.length == 1)
            throw new UnsupportedFormatException(line);

        this.hours = Integer.parseInt(tmp[0]);
        this.minutes = Integer.parseInt(tmp[1]);
        if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59)
            throw new InvalidTimeException(line);
    }

    public int inMinutes(){
        return hours*60+minutes;
    }

    public String getTime(TimeFormat timeFormat){
        if (timeFormat.equals(TimeFormat.FORMAT_AMPM)) {
            String tmp = "AM";
            if (hours == 0) {
                hours += 12;
            } else if (hours == 12) {
                tmp = "PM";
            } else if (hours > 12) {
                hours -= 12;
                tmp = "PM";
            }
            return String.format("%2d:%02d %s", hours,minutes,tmp);
        }
        return String.format("%2d:%02d",hours,minutes);
    }

    @Override
    public int compareTo(TimeValue o) {
        return this.inMinutes()-o.inMinutes();
    }
}

class TimeTable{
    private List<TimeValue> times;

    public TimeTable() {
        this.times = new ArrayList<>();
    }

    public void readTimes(InputStream in) {
        Scanner scanner = new Scanner(in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            for (String p : parts) {
                TimeValue time = null;
                try {
                    time = new TimeValue(p);
                } catch (UnsupportedFormatException | InvalidTimeException e) {
                    System.out.println(e.getMessage());
                }
                times.add(time);
            }
        }
        times = times.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    public void writeTimes(PrintStream out, TimeFormat format24) {
        PrintWriter printWriter = new PrintWriter(out);
        times.forEach(time -> printWriter.println(time.getTime(format24)));
        printWriter.flush();
    }
}