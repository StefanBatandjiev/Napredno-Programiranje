//package src.Lab2;
//
//import java.text.DecimalFormat;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.Scanner;
//
//
//
//public class ContactsTester {
//
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        int tests = scanner.nextInt();
//        Faculty faculty = null;
//
//        int rvalue = 0;
//        long rindex = -1;
//
//        DecimalFormat df = new DecimalFormat("0.00");
//
//        for (int t = 0; t < tests; t++) {
//
//            rvalue++;
//            String operation = scanner.next();
//
//            switch (operation) {
//                case "CREATE_FACULTY": {
//                    String name = scanner.nextLine().trim();
//                    int N = scanner.nextInt();
//
//                    Student[] students = new Student[N];
//
//                    for (int i = 0; i < N; i++) {
//                        rvalue++;
//
//                        String firstName = scanner.next();
//                        String lastName = scanner.next();
//                        String city = scanner.next();
//                        int age = scanner.nextInt();
//                        long index = scanner.nextLong();
//
//                        if ((rindex == -1) || (rvalue % 13 == 0))
//                            rindex = index;
//
//                        Student student = new Student(firstName, lastName, city,
//                                age, index);
//                        students[i] = student;
//                    }
//
//                    faculty = new Faculty(name, students);
//                    break;
//                }
//
//                case "ADD_EMAIL_CONTACT": {
//                    long index = scanner.nextInt();
//                    String date = scanner.next();
//                    String email = scanner.next();
//
//                    rvalue++;
//
//                    if ((rindex == -1) || (rvalue % 3 == 0))
//                        rindex = index;
//
//                    faculty.getStudent(index).addEmailContact(date, email);
//                    break;
//                }
//
//                case "ADD_PHONE_CONTACT": {
//                    long index = scanner.nextInt();
//                    String date = scanner.next();
//                    String phone = scanner.next();
//
//                    rvalue++;
//
//                    if ((rindex == -1) || (rvalue % 3 == 0))
//                        rindex = index;
//
//                    faculty.getStudent(index).addPhoneContact(date, phone);
//                    break;
//                }
//
//                case "CHECK_SIMPLE": {
//                    System.out.println("Average number of contacts: "
//                            + df.format(faculty.getAverageNumberOfContacts()));
//
//                    rvalue++;
//
//                    String city = faculty.getStudent(rindex).getCity();
//                    System.out.println("Number of students from " + city + ": "
//                            + faculty.countStudentsFromCity(city));
//
//                    break;
//                }
//
//                case "CHECK_DATES": {
//
//                    rvalue++;
//
//                    System.out.print("Latest contact: ");
//                    Contact latestContact = faculty.getStudent(rindex)
//                            .getLatestContact();
//                    if (latestContact.getType().equals("Email"))
//                        System.out.println(((EmailContact) latestContact)
//                                .getEmail());
//                    if (latestContact.getType().equals("Phone"))
//                        System.out.println(((PhoneContact) latestContact)
//                                .getPhone()
//                                + " ("
//                                + ((PhoneContact) latestContact).getOperator()
//                                .toString() + ")");
//
//                    if (faculty.getStudent(rindex).getEmailContacts().length > 0
//                            && faculty.getStudent(rindex).getPhoneContacts().length > 0) {
//                        System.out.print("Number of email and phone contacts: ");
//                        System.out
//                                .println(faculty.getStudent(rindex)
//                                        .getEmailContacts().length
//                                        + " "
//                                        + faculty.getStudent(rindex)
//                                        .getPhoneContacts().length);
//
//                        System.out.print("Comparing dates: ");
//                        int posEmail = rvalue
//                                % faculty.getStudent(rindex).getEmailContacts().length;
//                        int posPhone = rvalue
//                                % faculty.getStudent(rindex).getPhoneContacts().length;
//
//                        System.out.println(faculty.getStudent(rindex)
//                                .getEmailContacts()[posEmail].isNewerThan(faculty
//                                .getStudent(rindex).getPhoneContacts()[posPhone]));
//                    }
//
//                    break;
//                }
//
//                case "PRINT_FACULTY_METHODS": {
//                    System.out.println("Faculty: " + faculty.toString());
//                    System.out.println("Student with most contacts: "
//                            + faculty.getStudentWithMostContacts().toString());
//                    break;
//                }
//
//            }
//
//        }
//
//        scanner.close();
//    }
//    class Contact{
//
//        private String date;
//
//        public Contact(String date) {
//            this.date = date;
//        }
//
//        public boolean isNewerThan(Contact c){
//            NewerDate newerDate = new NewerDate(date,c.date);
//            return newerDate.calculate();
//        }
//
//        public String getType(){
//            return "Email";
//        }
//    }
//
//    final class NewerDate{
//         private String date1;
//         private String date2;
//
//        public NewerDate(String date1, String date2) {
//            this.date1 = date1;
//            this.date2 = date2;
//        }
//
//        public boolean calculate(){
//            String [] pom1 = date1.split("-");
//            String [] pom2 = date2.split("-");
//
//            if(Integer.parseInt(pom1[0])>Integer.parseInt(pom2[0]))
//                return true;
//            else if(Integer.parseInt(pom1[0])<Integer.parseInt(pom2[0]))
//                return false;
//            else {
//                if(Integer.parseInt(pom1[1])>Integer.parseInt(pom2[1]))
//                    return true;
//                else if(Integer.parseInt(pom1[1])<Integer.parseInt(pom2[1]))
//                    return false;
//                else {
//                    if(Integer.parseInt(pom1[2])>Integer.parseInt(pom2[2]))
//                        return true;
//                    else if(Integer.parseInt(pom1[2])<Integer.parseInt(pom2[2]))
//                        return false;
//                    else return true;
//                }
//            }
//
//        }
//    }
//    class EmailContact extends Contact {
//
//        String email;
//
//        public EmailContact(String date,String email) {
//            super(date);
//            this.email = email;
//        }
//
//    }
//
//}
//
