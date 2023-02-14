package src.Lab7;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class TermFrequencyTest {
    public static void main(String[] args) throws FileNotFoundException {
        String[] stop = new String[] { "во", "и", "се", "за", "ќе", "да", "од",
                "ги", "е", "со", "не", "тоа", "кои", "до", "го", "или", "дека",
                "што", "на", "а", "но", "кој", "ја" };
        TermFrequency tf = new TermFrequency(System.in,
                stop);
        System.out.println(tf.countTotal());
        System.out.println(tf.countDistinct());
        System.out.println(tf.mostOften(10));
    }
}
// vasiot kod ovde
class TermFrequency{
    private final Map<String,Integer> terms;
    private final Set<String> stopWords;

    public TermFrequency(InputStream inputStream, String [] stop){
        this.terms = new TreeMap<>();
        this.stopWords = new HashSet<>();
        this.stopWords.addAll(Arrays.asList(stop));
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()){
            String line = scanner.nextLine();
            line = line.trim();
            if(line.length()>0){
                String [] words = line.split(" ");
                for(String word : words){
                    String key = normalize(word);
                    if(key.isEmpty() || stopWords.contains(key))
                        continue;
                    if(this.terms.containsKey(key)){
                        Integer count = this.terms.get(key);
                        this.terms.put(key,count+1);
                    }else {
                        this.terms.put(key,1);
                    }
                }
            }
        }
        scanner.close();
    }

    private static String normalize(String word){
        return word.toLowerCase().replace(",","").replace(".","").trim();
    }

    public int countTotal(){
        int sum = 0;
        for (Integer count : this.terms.values())
            sum += count;
        return sum;
    }

    public int countDistinct() {
        return this.terms.keySet().size();
    }

    public List<String> mostOften(int k) {
        List<String> mostOften = new ArrayList<>();
        SortedSet<Map.Entry<String, Integer>> sortedEntries = new TreeSet<>(
                (e1, e2) -> {
                    int res = e1.getValue().compareTo(e2.getValue());
                    return res != 0 ? -res : 1;
                });
       sortedEntries.addAll(terms.entrySet());
        for (Map.Entry<String, Integer> entry : sortedEntries) {
            mostOften.add(entry.getKey());
            --k;
            if (k == 0) {
                break;
            }
        }
        return mostOften;
    }
}