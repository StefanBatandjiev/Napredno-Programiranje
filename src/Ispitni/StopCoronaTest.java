package src.Ispitni;

import javax.xml.stream.Location;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import static java.util.Map.Entry.comparingByKey;
import static java.util.Map.Entry.comparingByValue;

class LocationUtils {
    public static double distanceBetween(ILocation location1, ILocation location2) {
        return Math.sqrt(Math.pow(location1.getLatitude() - location2.getLatitude(), 2)
                + Math.pow(location1.getLongitude() - location2.getLongitude(), 2));
    }

    public static double timeBetweenInSeconds(ILocation location1, ILocation location2) {
        return Math.abs(Duration.between(location1.getTimestamp(), location2.getTimestamp()).getSeconds());
    }

    public static boolean isDanger(ILocation location1, ILocation location2) {
        return distanceBetween(location1, location2) <= 2.0&&timeBetweenInSeconds(location1, location2) <= 300;
    }

    public static int dangerContactsBetween(User u1, User u2) {
        int counter = 0;
        for (ILocation iLocation : u1.getLocations()) {
            for (ILocation iLocation1 : u2.getLocations()) {
                if (isDanger(iLocation, iLocation1))
                    ++counter;
            }
        }

        return counter;
    }
}

class UserAlreadyExistException extends Exception{
    public UserAlreadyExistException(String id) {
        super(String.format("User with id %s already exists",id));
    }
}

class User{

    private String id;
    private String name;
    private boolean isInfected;
    private LocalDateTime timestamp;
    private List<ILocation> locations;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.isInfected = false;
        this.locations = new ArrayList<>();
    }

    public void addLocations(List<ILocation> iLocations) {
        locations.addAll(iLocations);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isInfected() {
        return isInfected;
    }

    public void setInfected() {
        isInfected = true;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDateTime getTimestamp() {
        return timestamp!=null?timestamp:LocalDateTime.MAX;
    }

    public List<ILocation> getLocations() {
        return locations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name) &&
                id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}

class StopCoronaApp{

    private Map<String,User> users;
    private Map<User,Map<User,Integer>> countContacts;

    public StopCoronaApp() {
        this.users = new HashMap<>();
        this.countContacts = new TreeMap<>(Comparator.comparing(User::getTimestamp).thenComparing(User::getId));
    }

    public void addUser(String name,String id) throws UserAlreadyExistException {
        if(this.users.containsKey(id))
            throw new UserAlreadyExistException(id);
        this.users.put(id,new User(id, name));
    }

    public void addLocations(String id, List<ILocation> locations) {
        this.users.get(id).addLocations(locations);
    }

    public void detectNewCase(String id, LocalDateTime timestamp) {
        User user = this.users.get(id);
        user.setInfected();
        user.setTimestamp(timestamp);
    }

    public Map<User, Integer> getDirectContacts (User u){
        return this.countContacts.get(u)
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() != 0)
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }

    public Collection<User> getIndirectContacts (User u){
        Set<User> indirectContacts = new TreeSet<>(Comparator.comparing(User::getName).thenComparing(User::getId));
        Map<User,Integer> directContacts = this.getDirectContacts(u);
        directContacts.keySet()
                .stream()
                .flatMap(user -> this.getDirectContacts(user).keySet().stream())
                .filter(user -> !indirectContacts.contains(user) && !directContacts.containsKey(user) && !user.equals(u))
                .forEach(indirectContacts::add);
        return indirectContacts;
    }

    private void getCountContacts(){
        for(User user1:this.users.values()){
            for(User user2:this.users.values()){
                if(user1!=user2){
                    this.countContacts.putIfAbsent(user1,new TreeMap<>(Comparator.comparing(User::getTimestamp).thenComparing(User::getId)));
                    this.countContacts.computeIfPresent(user1, (k,v) ->{
                        v.putIfAbsent(user2,0);
                        v.computeIfPresent(user2,(key,value)->{
                            value += LocationUtils.dangerContactsBetween(user1,user2);
                            return value;
                        });
                        return v;
                    });
                }
            }
        }
    }

    public void createReport() {
        this.getCountContacts();

        List<Integer> directContactsCounts = new ArrayList<>();
        List<Integer> indirectContactsCounts = new ArrayList<>();

        for (User user:this.countContacts.keySet()){
            if(user.isInfected()) {
                System.out.printf("%s %s %s\n", user.getName(), user.getId(), user.getTimestamp());
                Map<User, Integer> directContacts = this.getDirectContacts(user);
                System.out.println("Direct contacts:");
                directContacts.entrySet()
                        .stream()
                        .sorted(comparingByValue(Comparator.reverseOrder()))
                        .forEach(entry -> System.out.printf("%s %s*** %d\n", entry.getKey().getName(),entry.getKey().getId().substring(0, 4),entry.getValue()));
//                for (Map.Entry<User, Integer> direct : directContacts.entrySet()) {
//                    System.out.printf("%s %s*** %d\n",
//                            direct.getKey().getName(),
//                            direct.getKey().getId().substring(0, 4),
//                            direct.getValue());
//                }
                int count = directContacts.values().stream().mapToInt(i -> i).sum();
                System.out.printf("Count of direct contacts: %d\n", count);
                directContactsCounts.add(count);

                System.out.println("Indirect contacts: ");
                Collection<User> indirectContacts = this.getIndirectContacts(user);
                indirectContacts.forEach(indirect -> System.out.printf("%s %s***\n", indirect.getName(), indirect.getId().substring(0, 4)));
//                for (User indirect : indirectContacts) {
//                    System.out.printf("%s %s*** \n", indirect.getName(), indirect.getId().substring(0, 4));
//                }
                System.out.printf("Count of indirect contacts: %d\n", indirectContacts.size());
                indirectContactsCounts.add(indirectContacts.size());
            }
        }

        System.out.printf("Average direct contacts: %.4f\n", directContactsCounts.stream().mapToInt(i->i).average().getAsDouble());
        System.out.printf("Average indirect contacts: %.4f\n", indirectContactsCounts.stream().mapToInt(i->i).average().getAsDouble());
    }
}


interface ILocation{
    double getLongitude();

    double getLatitude();

    LocalDateTime getTimestamp();
}

public class StopCoronaTest {

    public static double timeBetweenInSeconds(ILocation location1, ILocation location2) {
        return Math.abs(Duration.between(location1.getTimestamp(), location2.getTimestamp()).getSeconds());
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        StopCoronaApp stopCoronaApp = new StopCoronaApp();

        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");

            switch (parts[0]) {
                case "REG": //register
                    String name = parts[1];
                    String id = parts[2];
                    try {
                        stopCoronaApp.addUser(name, id);
                    } catch (UserAlreadyExistException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "LOC": //add locations
                    id = parts[1];
                    List<ILocation> locations = new ArrayList<>();
                    for (int i = 2; i < parts.length; i += 3) {
                        locations.add(createLocationObject(parts[i], parts[i + 1], parts[i + 2]));
                    }
                    stopCoronaApp.addLocations(id, locations);

                    break;
                case "DET": //detect new cases
                    id = parts[1];
                    LocalDateTime timestamp = LocalDateTime.parse(parts[2]);
                    stopCoronaApp.detectNewCase(id, timestamp);

                    break;
                case "REP": //print report
                    stopCoronaApp.createReport();
                    break;
                default:
                    break;
            }
        }
    }

    private static ILocation createLocationObject(String lon, String lat, String timestamp) {
        return new ILocation() {
            @Override
            public double getLongitude() {
                return Double.parseDouble(lon);
            }

            @Override
            public double getLatitude() {
                return Double.parseDouble(lat);
            }

            @Override
            public LocalDateTime getTimestamp() {
                return LocalDateTime.parse(timestamp);
            }
        };
    }
}

