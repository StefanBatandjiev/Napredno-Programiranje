package src.ZadachiZaVtorKolokvium;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

class File implements Comparable<File>{

    private String name;
    private Integer size;
    private LocalDateTime createdAt;

    public File(String name, Integer size, LocalDateTime createdAt) {
        this.name = name;
        this.size = size;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public Integer getSize() {
        return size;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isHidden(){
        return this.name.startsWith(".");
    }

    public String toMonthDay() {
        return String.format("%s-%d", createdAt.getMonth(),
                createdAt.getDayOfMonth());
    }

    @Override
    public String toString() {
        return String.format("%-10s %5dB %s",this.name,this.size,this.createdAt);
    }

    @Override
    public int compareTo(File o) {
        return Comparator.comparing(File::getCreatedAt)
                .thenComparing(File::getName)
                .thenComparing(File::getSize)
                .compare(this,o);
    }
}

class FileSystem{

    private Map<Character,Set<File>> files;

    public FileSystem() {
        this.files = new HashMap<>();
    }

    public void addFile(char folder, String name, int size, LocalDateTime createdAt){
        File file = new File(name,size,createdAt);
        Set<File> set = this.files.computeIfAbsent(folder, k -> new TreeSet<>());
        set.add(file);
    }

    public List<File> findAllHiddenFilesWithSizeLessThen(int size){
        List<File> list = new ArrayList<>();
        for(Set<File> files : this.files.values()){
            for(File file : files){
                if(file.isHidden()&&file.getSize()<size){
                    list.add(file);
                }
            }
        }
        return list;
//        return this.files.values()
//                .stream()
//                .flatMap(Collection::stream)
//                .filter(File::isHidden)
//                .filter(f -> f.getSize()<size)
//                .collect(Collectors.toList());
    }

    public int totalSizeOfFilesFromFolders(List<Character> folders) {
        return folders.stream()
                .mapToInt(folder ->this.files.get(folder).stream().mapToInt(File::getSize).sum())
                .sum();
    }

    public Map<Integer, Set<File>> byYear() {
        return this.files.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(
                        Collectors.groupingBy(
                                file -> file.getCreatedAt().getYear(),
                                Collectors.toSet()));
    }

    public Map<String, Long> sizeByMonthAndDay() {
        return this.files.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(
                        Collectors.groupingBy(
                                File::toMonthDay,
                                Collectors.summingLong(File::getSize)));
    }
}

/**
 * Partial exam II 2016/2017
 */
public class FileSystemTest {
    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            fileSystem.addFile(parts[0].charAt(0), parts[1],
                    Integer.parseInt(parts[2]),
                    LocalDateTime.of(2016, 12, 29, 0, 0, 0).minusDays(Integer.parseInt(parts[3]))
            );
        }
        int action = scanner.nextInt();
        if (action == 0) {
            scanner.nextLine();
            int size = scanner.nextInt();
            System.out.println("== Find all hidden files with size less then " + size);
            List<File> files = fileSystem.findAllHiddenFilesWithSizeLessThen(size);
            files.forEach(System.out::println);
        } else if (action == 1) {
            scanner.nextLine();
            String[] parts = scanner.nextLine().split(":");
            System.out.println("== Total size of files from folders: " + Arrays.toString(parts));
            int totalSize = fileSystem.totalSizeOfFilesFromFolders(Arrays.stream(parts)
                    .map(s -> s.charAt(0))
                    .collect(Collectors.toList()));
            System.out.println(totalSize);
        } else if (action == 2) {
            System.out.println("== Files by year");
            Map<Integer, Set<File>> byYear = fileSystem.byYear();
            byYear.keySet().stream().sorted()
                    .forEach(key -> {
                        System.out.printf("Year: %d\n", key);
                        Set<File> files = byYear.get(key);
                        files.stream()
                                .sorted()
                                .forEach(System.out::println);
                    });
        } else if (action == 3) {
            System.out.println("== Size by month and day");
            Map<String, Long> byMonthAndDay = fileSystem.sizeByMonthAndDay();
            byMonthAndDay.keySet().stream().sorted()
                    .forEach(key -> System.out.printf("%s -> %d\n", key, byMonthAndDay.get(key)));
        }
        scanner.close();
    }
}

// Your code here
