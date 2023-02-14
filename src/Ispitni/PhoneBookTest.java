//package src.Ispitni;
//
//import java.util.*;
//
//class Contact{
//
//    private String name;
//    private String number;
//
//    public Contact(String name, String number) {
//        this.name = name;
//        this.number = number;
//    }
//}
//
//class PhoneBook{
//
//    private Map<String, Set<Contact>> contacts;
//    private Set<String> numbers;
//
//    public PhoneBook() {
//        this.contacts = new HashMap<>();
//        this.numbers = new HashSet<>();
//    }
//
//    void addContact(String name, String number){
//        Contact contact = new Contact(name,number);
//
//    }
//}
//
//public class PhoneBookTest {
//
//    public static void main(String[] args) {
//        PhoneBook phoneBook = new PhoneBook();
//        Scanner scanner = new Scanner(System.in);
//        int n = scanner.nextInt();
//        scanner.nextLine();
//        for (int i = 0; i < n; ++i) {
//            String line = scanner.nextLine();
//            String[] parts = line.split(":");
//            try {
//                phoneBook.addContact(parts[0], parts[1]);
//            } catch (DuplicateNumberException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//        while (scanner.hasNextLine()) {
//            String line = scanner.nextLine();
//            System.out.println(line);
//            String[] parts = line.split(":");
//            if (parts[0].equals("NUM")) {
//                phoneBook.contactsByNumber(parts[1]);
//            } else {
//                phoneBook.contactsByName(parts[1]);
//            }
//        }
//    }
//
//}
//
//// Вашиот код овде
//
//
