package src.ZadachiZaVtorKolokvium;

import java.util.*;

class Candidate{

    private final String city;
    private final String code;
    private final String name;
    private final int age;

    public Candidate(String city, String code, String name, int age) {
        this.city = city;
        this.code = code;
        this.name = name;
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d",code,name,age);
    }
}

class Audition{

    private final Map<String,HashSet<Candidate>> participants;
    private static final Comparator<Candidate> comparator = Comparator.comparing(Candidate::getName).thenComparing(Candidate::getAge).thenComparing(Candidate::getCode);

    public Audition() {
        this.participants = new HashMap<>();
    }

    public void addParticipant(String city, String code, String name, int age){
        Candidate candidate = new Candidate(city,code,name,age);
        HashSet<Candidate> set = this.participants.get(city);
        if(set==null){
            set = new HashSet<>();
            participants.put(city, set);
        }
        if (this.participants.get(city).stream().noneMatch(p->p.getCode().equals(code)))
            set.add(candidate);
    }

    public void listByCity(String city){
        this.participants.get(city).stream().sorted(comparator).forEach(System.out::println);
    }
}

public class AuditionTest {
    public static void main(String[] args) {
        Audition audition = new Audition();
        List<String> cities = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 1) {
                audition.addParticipant(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]));
            } else {
                cities.add(line);
            }
        }
        for (String city : cities) {
            System.out.printf("+++++ %s +++++\n", city);
            audition.listByCity(city);
        }
        scanner.close();
    }
}