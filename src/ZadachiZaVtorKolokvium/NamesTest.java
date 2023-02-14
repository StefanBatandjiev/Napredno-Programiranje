package src.ZadachiZaVtorKolokvium;
import java.util.*;

public class NamesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Names names = new Names();
        for (int i = 0; i < n; ++i) {
            String name = scanner.nextLine();
            names.addName(name);
        }
        n = scanner.nextInt();
        System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
        names.printN(n);
        System.out.println("===== FIND NAME =====");
        int len = scanner.nextInt();
        int index = scanner.nextInt();
        System.out.println(names.findName(len, index));
        scanner.close();

    }
}

// vashiot kod ovde

class Names{

    private Map<String,Integer> names;

    public Names() {
        this.names = new TreeMap<>();
    }


    public void addName(String name) {
        Integer count = this.names.get(name);
        if(count==null){
            count=0;
        }
        count++;
        this.names.put(name,count);
    }

    public void printN(int n) {
        for (Map.Entry<String,Integer> entry : names.entrySet()){
            if(entry.getValue()>=n){
                Set<Character> uniques = new HashSet<>();
                for(char c : entry.getKey().toCharArray()){
                    uniques.add(Character.toLowerCase(c));
                }
                System.out.printf("%s (%d) %d\n",entry.getKey(),entry.getValue(),uniques.size());
            }
        }
    }

    public String findName(int len, int index) {
        List<String> names = new LinkedList<>(this.names.keySet());
        ListIterator<String> iterator = names.listIterator();
        while(iterator.hasNext()){
            String name = iterator.next();
            if(name.length()>=len){
                iterator.remove();
            }
        }
        iterator = names.listIterator();
        String name = null;
        index = index % names.size();
        for(int i=0;i<=index;i++){
            name = iterator.next();
        }
        return name;
    }
}