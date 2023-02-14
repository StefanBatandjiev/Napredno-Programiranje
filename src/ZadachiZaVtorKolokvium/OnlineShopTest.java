package src.ZadachiZaVtorKolokvium;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

enum COMPARATOR_TYPE {
    NEWEST_FIRST,
    OLDEST_FIRST,
    LOWEST_PRICE_FIRST,
    HIGHEST_PRICE_FIRST,
    MOST_SOLD_FIRST,
    LEAST_SOLD_FIRST
}

class ProductNotFoundException extends Exception {
    ProductNotFoundException(String message) {
        super(message);
    }
}


class Product {

    private String category;
    private String id;
    private String name;
    private LocalDateTime createdAt;
    private double price;
    private int quantity;

    public Product(String category, String id, String name, LocalDateTime createdAt, double price) {
        this.category = category;
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.price = price;
        this.quantity = 0;
    }

    public String getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", price=" + price +
                ", quantitySold=" + quantity +
                '}';
    }
}


class OnlineShop {

    private Map<String,Product> products;

    OnlineShop() {
        this.products = new HashMap<>();
    }

    void addProduct(String category, String id, String name, LocalDateTime createdAt, double price){
        this.products.put(id,new Product(category, id, name, createdAt, price));
    }

    double buyProduct(String id, int quantity) throws ProductNotFoundException{
        if(!this.products.containsKey(id))
            throw new ProductNotFoundException("");
        Product product = this.products.get(id);
        product.setQuantity(quantity);
        return product.getPrice()*quantity;
    }

    List<List<Product>> listProducts(String category, COMPARATOR_TYPE comparatorType, int pageSize) {
        List<List<Product>> result = new ArrayList<>();
        List<Product> list = new ArrayList<>();
        if(comparatorType.equals(COMPARATOR_TYPE.NEWEST_FIRST)){
            list = this.products.values()
                    .stream()
                    .sorted(Comparator.comparing(Product::getCreatedAt).reversed())
                    .limit(pageSize)
                    .collect(Collectors.toList());
        }else if (comparatorType.equals(COMPARATOR_TYPE.OLDEST_FIRST)){
            list = this.products.values()
                    .stream()
                    .sorted(Comparator.comparing(Product::getCreatedAt))
                    .limit(pageSize)
                    .collect(Collectors.toList());
        } else if (comparatorType.equals(COMPARATOR_TYPE.LOWEST_PRICE_FIRST)) {
            list = this.products.values()
                    .stream()
                    .sorted(Comparator.comparing(Product::getPrice))
                    .limit(pageSize)
                    .collect(Collectors.toList());
        }else if(comparatorType.equals(COMPARATOR_TYPE.HIGHEST_PRICE_FIRST)){
            list = this.products.values()
                    .stream()
                    .sorted(Comparator.comparing(Product::getPrice).reversed())
                    .limit(pageSize)
                    .collect(Collectors.toList());
        }else if(comparatorType.equals(COMPARATOR_TYPE.MOST_SOLD_FIRST)){
            list = this.products.values()
                    .stream()
                    .sorted(Comparator.comparing(Product::getQuantity).reversed())
                    .limit(pageSize)
                    .collect(Collectors.toList());
        }else if(comparatorType.equals(COMPARATOR_TYPE.LEAST_SOLD_FIRST)){
            list = this.products.values()
                    .stream()
                    .sorted(Comparator.comparing(Product::getQuantity))
                    .limit(pageSize)
                    .collect(Collectors.toList());
        }
        if(category != null){
            list = list.stream().filter(i -> i.getCategory().equals(category)).collect(Collectors.toList());
        }
        result.add(list);
        return result;
    }

}

public class OnlineShopTest {

    public static void main(String[] args) {
        OnlineShop onlineShop = new OnlineShop();
        double totalAmount = 0.0;
        Scanner sc = new Scanner(System.in);
        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            String[] parts = line.split("\\s+");
            if (parts[0].equalsIgnoreCase("addproduct")) {
                String category = parts[1];
                String id = parts[2];
                String name = parts[3];
                LocalDateTime createdAt = LocalDateTime.parse(parts[4]);
                double price = Double.parseDouble(parts[5]);
                onlineShop.addProduct(category, id, name, createdAt, price);
            } else if (parts[0].equalsIgnoreCase("buyproduct")) {
                String id = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                try {
                    totalAmount += onlineShop.buyProduct(id, quantity);
                } catch (ProductNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                String category = parts[1];
                if (category.equalsIgnoreCase("null"))
                    category=null;
                String comparatorString = parts[2];
                int pageSize = Integer.parseInt(parts[3]);
                COMPARATOR_TYPE comparatorType = COMPARATOR_TYPE.valueOf(comparatorString);
                printPages(onlineShop.listProducts(category, comparatorType, pageSize));
            }
        }
        System.out.println("Total revenue of the online shop is: " + totalAmount);

    }

    private static void printPages(List<List<Product>> listProducts) {
        for (int i = 0; i < listProducts.size(); i++) {
            System.out.println("PAGE " + (i + 1));
            listProducts.get(i).forEach(System.out::println);
        }
    }
}


