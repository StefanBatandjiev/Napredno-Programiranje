package src.ZadachiZaPrvKolokvium;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class AmountNotAllowedException extends Exception{

    public AmountNotAllowedException(int totalAmount) {
        super(String.format("Receipt with amount %d is not allowed to be scanned",totalAmount));
    }
}

enum TaxType{
    A,B,V
}

class Item{
    private int price;
    private TaxType type;

    public Item(int price, TaxType type) {
        this.price = price;
        this.type = type;
    }

    public Item(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public TaxType getType() {
        return type;
    }

    public void setType(TaxType type) {
        this.type = type;
    }

    public double getTaxReturn(){
        if(type.equals(TaxType.A)){
            return price * 0.18 * 0.15;
        }
        else if (type.equals(TaxType.B)){
            return price * 0.05 * 0.15;
        }
        else return 0;
    }
}

class Receipt{

    private Long id;
    private List<Item> items;

    public Receipt(Long id, List<Item> items) {
        this.id = id;
        this.items = items;
    }

    public static Receipt create(String line) throws AmountNotAllowedException {
        String [] parts = line.split("\\s+");
        long id = Long.parseLong(parts[0]);
        List<Item> items = new ArrayList<>();

        Arrays.stream(parts)
                .skip(1)
                .forEach(i -> {
                    if(Character.isDigit(i.charAt(0))){
                        items.add(new Item(Integer.parseInt(i)));
                    }
                    else{
                        items.get(items.size()-1).setType(TaxType.valueOf(i));
                    }
                });
        if(totalAmount(items)>30000)
            throw new AmountNotAllowedException(totalAmount(items));

        return new Receipt(id,items);
    }

    public static int totalAmount(List<Item> items){
        return items.stream().mapToInt(Item::getPrice).sum();
    }

    public double taxReturn(){
        return this.items.stream().mapToDouble(Item::getTaxReturn).sum();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("%10s\t%10d\t%10.5f", this.id, totalAmount(this.items), taxReturn());
    }

}

class MojDDV{

    private List<Receipt> receipts;

    public MojDDV() {
        this.receipts = new ArrayList<>();
    }

    public void readRecords(InputStream in) {
        receipts = new BufferedReader(new InputStreamReader(in))
                .lines()
                .map(line -> {
                    try {
                        return Receipt.create(line);
                    } catch (AmountNotAllowedException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                }).collect(Collectors.toList());

        receipts = receipts.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    public void printTaxReturns(PrintStream out) {
        PrintWriter printWriter = new PrintWriter(out);
        receipts.forEach(i -> printWriter.println(i.toString()));
        printWriter.flush();
    }

    public void printStatistics(PrintStream out) {
        PrintWriter printWriter = new PrintWriter(out);
        DoubleSummaryStatistics summaryStatistics = receipts.stream()
                .mapToDouble(Receipt::taxReturn).summaryStatistics();
        printWriter.println(String.format("min:\t%.3f\nmax:\t%.3f\nsum:\t%.3f\ncount:\t%-5d\navg:\t%.3f",
                                            summaryStatistics.getMin(),
                                            summaryStatistics.getMax(),
                                            summaryStatistics.getSum(),
                                            summaryStatistics.getCount(),
                                            summaryStatistics.getAverage()));
        printWriter.flush();
    }
}

public class MojDDVTest {

    public static void main(String[] args) {

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        mojDDV.readRecords(System.in);

        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);

        System.out.println("===PRINTING SUMMARY STATISTICS FOR TAX RETURNS TO OUTPUT STREAM===");
        mojDDV.printStatistics(System.out);

    }
}