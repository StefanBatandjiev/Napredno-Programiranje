package src.ZadachiZaVtorKolokvium;

import javax.xml.stream.events.Characters;
import java.util.*;

class Airport{

    private final String name;
    private final String country;
    private final String code;
    private final int passengers;
    private final Map<String,Set<Flight>> flights;

    public Airport(String name, String country, String code, int passengers) {
        this.name = name;
        this.country = country;
        this.code = code;
        this.passengers = passengers;
        this.flights = new TreeMap<>();
    }

    public void addFlight(Flight flight){
        Set<Flight> flightsSet = flights.get(flight.getTo());
        if (flightsSet == null) {
            flightsSet = new TreeSet<>();
            flights.put(flight.getTo(), flightsSet);
        }
        flightsSet.add(flight);
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCode() {
        return code;
    }

    public int getPassengers() {
        return passengers;
    }

    public Map<String, Set<Flight>> getFlights() {
        return flights;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)\n%s\n%d",name,code,country,passengers);
    }
}

class Flight implements Comparable<Flight>{

    private final String from;
    private final String to;
    private final int time;
    private final int duration;
    static int i = 0;

    public Flight(String from, String to, int time, int duration) {
        this.from = from;
        this.to = to;
        this.time = time;
        this.duration = duration;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getTime() {
        return time;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        int end = time + duration;
        int plus = end / (24 * 60);
        end %= (24 * 60);
        i++;
        return String.format("%s-%s %02d:%02d-%02d:%02d%s %dh%02dm",from, to, time / 60, time % 60,
                end / 60, end % 60, plus > 0 ? " +1d" : "", duration / 60, duration % 60);
    }

    public int compareTo(Flight o) {
        int res = Integer.compare(this.time, o.time);
        if (res == 0) {
            return this.from.compareTo(o.from);
        }
        return res;
    }
}

class Airports{

    private final Map<String,Airport> airports;

    public Airports() {
        this.airports = new TreeMap<>();
    }

    public void addAirport(String name,String country,String code,int passengers){
        this.airports.put(code,new Airport(name, country, code, passengers));
    }

    public void addFlights(String from, String to, int time, int duration){
        this.airports.get(from).addFlight(new Flight(from, to, time, duration));
    }

    public void showFlightsFromAirport(String code){
        Airport airport = this.airports.get(code);
        System.out.println(airport);
//        this.airports.get(code).getFlights().values().forEach(System.out::println);
        int i = 1;
        for(Set<Flight> flights : this.airports.get(code).getFlights().values()){
            for(Flight flight : flights){
                System.out.printf("%d. %s\n",i,flight);
                i++;
            }
        }
    }

    public void showDirectFlightsFromTo(String from,String to){
        Airport airport = this.airports.get(from);
        Set<Flight> flights = airport.getFlights().get(to);
        if(flights == null) {
            System.out.printf("No flights from %s to %s\n", from, to);
        }else{
            flights.forEach(System.out::println);
        }
    }

    public void showDirectFlightsTo(String to) {
//        this.airports.values().stream().map(i -> i.getFlights().get(to)).filter(Objects::nonNull).forEach(System.out::println);
        Set<Flight> flights = new TreeSet<>();
        for(Airport airport : this.airports.values()){
            if (airport.getFlights().get(to)!=null) {
                flights.addAll(airport.getFlights().get(to));
            }
        }
        flights.forEach(System.out::println);
    }
}


public class AirportsTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Airports airports = new Airports();
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] codes = new String[n];
        for (int i = 0; i < n; ++i) {
            String al = scanner.nextLine();
            String[] parts = al.split(";");
            airports.addAirport(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
            codes[i] = parts[2];
        }
        int nn = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < nn; ++i) {
            String fl = scanner.nextLine();
            String[] parts = fl.split(";");
            airports.addFlights(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
        }
        int f = scanner.nextInt();
        int t = scanner.nextInt();
        String from = codes[f];
        String to = codes[t];
        System.out.printf("===== FLIGHTS FROM %S =====\n", from);
        airports.showFlightsFromAirport(from);
        System.out.printf("===== DIRECT FLIGHTS FROM %S TO %S =====\n", from, to);
        airports.showDirectFlightsFromTo(from, to);
        t += 5;
        t = t % n;
        to = codes[t];
        System.out.printf("===== DIRECT FLIGHTS TO %S =====\n", to);
        airports.showDirectFlightsTo(to);
    }
}

// vashiot kod ovde

