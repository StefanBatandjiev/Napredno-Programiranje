package src.ZadachiZaVtorKolokvium;

import java.util.*;

class SeatNotAllowedException extends Exception{

}

class SeatTakenException extends Exception{

}

class Sector{
    private String name;
    private int numSeats;
    private Map<Integer,Integer> takenSeats;
    private Set<Integer> types;

    public Sector(String name, int numSeats) {
        this.name = name;
        this.numSeats = numSeats;
        this.takenSeats = new HashMap<>();
        this.types = new HashSet<>();
    }

    public boolean isTaken(int seat){
        return this.takenSeats.containsKey(seat);
    }

    public void takeSeat(int seat, int type) throws SeatNotAllowedException {
        if(type==1) {
            if (types.contains(2))
                throw new SeatNotAllowedException();
        }else if(type==2){
            if(types.contains(1))
                throw new SeatNotAllowedException();
        }
        types.add(type);
        takenSeats.put(seat,type);
    }

    public int free(){
        return this.numSeats - takenSeats.size();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%s\t%d/%d\t%.1f%%",name,free(),numSeats,
                (numSeats-free())*100.0/numSeats);
    }
}

class Stadium{
    private String name;
    private Map<String,Sector> sectors;

    public Stadium(String name) {
        this.name = name;
        this.sectors = new HashMap<>();
    }

    public void createSectors(String[] sectorNames, int[] sectorSizes) {
        for (int i = 0; i < sectorNames.length; ++i) {
            addSector(sectorNames[i], sectorSizes[i]);
        }
    }

    void addSector(String name, int size) {
        Sector sector = new Sector(name, size);
        sectors.put(name, sector);
    }

    public void buyTicket(String sectorName, int seat, int type) throws SeatTakenException, SeatNotAllowedException {
        Sector sector = this.sectors.get(sectorName);
        if(sector.isTaken(seat))
            throw new SeatTakenException();
        sector.takeSeat(seat,type);
    }

    public void showSectors() {
        this.sectors.values()
                .stream()
                .sorted(Comparator.comparing(Sector::free).reversed().thenComparing(Sector::getName))
                .forEach(System.out::println);
    }
}

public class StaduimTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] sectorNames = new String[n];
        int[] sectorSizes = new int[n];
        String name = scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            sectorNames[i] = parts[0];
            sectorSizes[i] = Integer.parseInt(parts[1]);
        }
        Stadium stadium = new Stadium(name);
        stadium.createSectors(sectorNames, sectorSizes);
        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            try {
                stadium.buyTicket(parts[0], Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]));
            } catch (SeatNotAllowedException e) {
                System.out.println("SeatNotAllowedException");
            } catch (SeatTakenException e) {
                System.out.println("SeatTakenException");
            }
        }
        stadium.showSectors();
    }
}

