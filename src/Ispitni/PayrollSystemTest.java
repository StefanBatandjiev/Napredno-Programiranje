//package src.Ispitni;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.*;
//import java.util.stream.Collectors;
//
//enum EmployeeType{
//    H,F
//}
//
//interface Salary{
//    double getSalary();
//}
//
//class Employee{
//
//    private EmployeeType type;
//    private String id;
//    private int level;
//
//    public Employee(String type, String id, int level) {
//        this.type = EmployeeType.valueOf(type);
//        this.id = id;
//        this.level = level;
//    }
//
//    public EmployeeType getType() {
//        return type;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public int getLevel() {
//        return level;
//    }
//}
//
//class HourlyEmployee extends Employee implements Salary{
//
//    private int hours;
//
//    public HourlyEmployee(String type, String id, int level,int hours) {
//        super(type, id, level);
//        this.hours = hours;
//    }
//
//    public int getHours() {
//        return hours;
//    }
//
//    @Override
//    public double getSalary() {
//        if(hours<40){
//            return this.hours * getLevel();
//        }else{
//            return 40 * getLevel() + (this.hours-40)*getLevel()*1.5;
//        }
//    }
//}
//
//class FreelancerEmployee extends Employee implements Salary{
//
//    private List<Integer> tickets;
//
//    public FreelancerEmployee(String type, String id, int level,List<Integer> tickets) {
//        super(type, id, level);
//        this.tickets = tickets;
//    }
//
//    public List<Integer> getTickets() {
//        return tickets;
//    }
//
//    @Override
//    public double getSalary() {
//        return this.tickets.stream().mapToInt(i -> i).sum()*getLevel();
//    }
//}
//
//class PayrollSystem{
//
//    private Map<String,Double> hourlyRateByLevel;
//    private Map<String,Double> ticketRateByLevel;
//    private List<Employee> employees;
//
//    public PayrollSystem(Map<String, Double> hourlyRateByLevel, Map<String, Double> ticketRateByLevel) {
//        this.hourlyRateByLevel = hourlyRateByLevel;
//        this.ticketRateByLevel = ticketRateByLevel;
//        this.employees = new ArrayList<>();
//    }
//
//
//    public void readEmployees(InputStream in) {
//        this.employees = new BufferedReader(new InputStreamReader(in))
//                .lines()
//                .map(line -> {
//                    String [] parts = line.split(";");
//                    Employee employee = null;
//                    if(parts[0].equals("H")){
//                        employee =  new HourlyEmployee(parts[0],parts[1],parts[2],Integer.parseInt(parts[3]));
//                    }else if(parts[0].equals("F")){
//                        List<Integer> tickets = Arrays.stream(parts).skip(3).map(Integer::parseInt).collect(Collectors.toList());
//                        employee = new FreelancerEmployee(parts[0],parts[1],parts[2],tickets);
//                    }
//                   return employee;
//                }).collect(Collectors.toList());
//    }
//}
//
//public class PayrollSystemTest {
//
//    public static void main(String[] args) {
//
//        Map<String, Double> hourlyRateByLevel = new LinkedHashMap<>();
//        Map<String, Double> ticketRateByLevel = new LinkedHashMap<>();
//        for (int i = 1; i <= 10; i++) {
//            hourlyRateByLevel.put("level" + i, 10 + i * 2.2);
//            ticketRateByLevel.put("level" + i, 5 + i * 2.5);
//        }
//
//        PayrollSystem payrollSystem = new PayrollSystem(hourlyRateByLevel, ticketRateByLevel);
//
//        System.out.println("READING OF THE EMPLOYEES DATA");
//        payrollSystem.readEmployees(System.in);
//
//        System.out.println("PRINTING EMPLOYEES BY LEVEL");
//        Set<String> levels = new LinkedHashSet<>();
//        for (int i=5;i<=10;i++) {
//            levels.add("level"+i);
//        }
//        Map<String, Set<Employee>> result = payrollSystem.printEmployeesByLevels(System.out, levels);
//        result.forEach((level, employees) -> {
//            System.out.println("LEVEL: "+ level);
//            System.out.println("Employees: ");
//            employees.forEach(System.out::println);
//        });
//
//
//    }
//}
