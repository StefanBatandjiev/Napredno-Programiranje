package src.ZadachiZaVtorKolokvium;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class EventCalendarTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int year = scanner.nextInt();
        scanner.nextLine();
        EventCalendar eventCalendar = new EventCalendar(year);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            String name = parts[0];
            String location = parts[1];
            Date date = df.parse(parts[2]);
            try {
                eventCalendar.addEvent(name, location, date);
            } catch (WrongDateException e) {
                System.out.println(e.getMessage());
            }
        }
        Date date = df.parse(scanner.nextLine());
        eventCalendar.listEvents(date);
        eventCalendar.listByMonth();
    }
}

class Event{

    private String name;
    private String location;
    private Date date;

    public Event(String name, String location, Date date) {
        this.name = name;
        this.location = location;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("%s at %s, %s",date.toString(),location,name);
    }
}

class EventCalendar{

    private Map<String,Event> calendar;
    private int year;
    private static final Comparator<Event> comparator = Comparator.comparing(Event::getDate).thenComparing(Event::getName);

    public EventCalendar(int year) {
        this.calendar = new HashMap<>();
        this.year = year;
    }

    public void addEvent(String name, String location, Date date) throws WrongDateException {
        Event event = new Event(name, location, date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        if(year != this.year){
            throw new WrongDateException(date);
        }
        this.calendar.putIfAbsent(name, event);
    }

    public void listEvents(Date date) {
        this.calendar.values()
                .stream()
                .sorted(comparator)
                .filter(event -> event.getDate().equals(date))
                .forEach(System.out::println);

    }

    public void listByMonth() {
    }
}

class WrongDateException extends Exception{
    public WrongDateException(Date date) {
        super(String.format("Wrong date: %s",date.toString()));
    }
}