package org.koffa;

import org.koffa.Person.Customer;
import org.koffa.Person.Employee;
import org.koffa.Person.Person;
import org.koffa.Person.PersonService;
import java.util.Scanner;

public class ConsoleInterface {
    private final PersonService personService;
    private boolean running = true;
    public ConsoleInterface() {
        this.personService = new PersonService();
    }
    public boolean isRunning() {
        return running;
    }
    public void printMainMenu() {
        System.out.println("""
                1. Create person\s
                2. View, update & delete people\s
                3. Drop cluster (remove all people)\s
                4. Quit""");
        switch (takeIntInput(1, 4, "Enter choice")) {
            case 1 -> addNewPerson();
            case 2 -> viewPeople();
            case 3 -> dropCluster();
            case 4 -> running = false;
        }
    }
    private void addNewPerson() {
        System.out.println("""
                1. Add new customer\s
                2. Add new employee\s
                3. Add new person\s
                4. Back to main menu""");
        switch (takeIntInput(1,4, "Enter choice")) {
            case 1 -> personService.addPerson(customerBuilder());
            case 2 -> personService.addPerson(employeeBuilder());
            case 3 -> personService.addPerson(personBuilder());
            case 4 -> printMainMenu();
        }
    }
    private Customer customerBuilder() {
        Customer customer = new Customer();
        setPersonAttributes(customer);
        setCustomerAttributes(customer);
        return customer;
    }
    private void dropCluster() {
        if(takeStringInput("Are you sure? yes/no").equalsIgnoreCase("yes")) {
            personService.removeCluster();
        }
        else printMainMenu();
    }
    private Employee employeeBuilder() {
        Employee employee = new Employee();
        setPersonAttributes(employee);
        setEmployeeAttributes(employee);
        return employee;
    }
    private Person personBuilder() {
        Person person = new Person();
        setPersonAttributes(person);
        return person;
    }
    private void removePerson(Person person) {
        personService.removePerson(person);
    }
    private void setCustomerAttributes(Customer customer) {
        customer.setCustomerNumber(takeIntInput(0,Integer.MAX_VALUE, "Enter customer number:"));
    }
    private void setEmployeeAttributes(Employee employee) {
        employee.setEmployeeNumber(takeIntInput(0,Integer.MAX_VALUE, "Enter employee number:"));
    }
    private void setPersonAttributes(Person person) {
        person.setName(takeStringInput("Enter name:"));
        person.setAge(takeIntInput(0,150, "Enter age (0 - 150):"));
        person.setAdress(takeStringInput("Enter adress:"));
    }
    private void showPersonDetails(Person person) {
        if(person instanceof Customer) {
            System.out.println("Customer");
            System.out.println("Customer number: " + ((Customer) person).getCustomerNumber());
        }
        else if(person instanceof Employee) {
            System.out.println("Employee");
            System.out.println("Employee number: " + ((Employee) person).getEmployeeNumber());
        }
        else System.out.println("Person");
        System.out.println("Name: " + person.getName());
        System.out.println("Age: " + person.getAge());
        System.out.println("Adress: " + person.getAdress());
        System.out.println("--------------------------------------------------");
        System.out.println("""
                1. Remove person
                2. Update person
                3. Back to main menu""");
        switch (takeIntInput(1,3, "Enter choice")) {
            case 1 -> removePerson(person);
            case 2 -> updatePerson(person);
            case 3 -> printMainMenu();
        }
    }
    private int takeIntInput(int min, int max, String prompt) {
        int value;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println(prompt);
            try {
                value = sc.nextInt();
            } catch (Exception e) {
                value = takeIntInput(min, max, prompt);
            }
        }
        while (value < min || value > max);
        return value;
    }
    private String takeStringInput(String prompt) {
        Scanner sc = new Scanner(System.in);
        System.out.println(prompt);
        return sc.nextLine();
    }
    private void updateAdress(Person person) {
        personService.updateAdress(person, takeStringInput("Enter adress:"));
    }
    private void updateAge(Person person) {
        personService.updateAge(person, takeIntInput(1, 150, "Enter age (0-150):"));
    }
    private void updateCustomerNumber(Customer customer) {
        personService.updateCustomerNumber(customer, takeIntInput(0, Integer.MAX_VALUE, "Enter customer number:"));
    }
    private void updateEmployeeNumber(Employee employee) {
        personService.updateEmployeeNumber(employee, takeIntInput(0, Integer.MAX_VALUE, "Enter employee number:"));
    }
    private void updateName(Person person) {
        personService.updateName(person, takeStringInput("Enter new name:"));
    }
    private void updatePerson(Person person) {
        System.out.println("""
                What do you want to edit\s
                1. Name
                2. Age
                3. Adress""");
        int max = 4;
        if(person instanceof Customer) {
            System.out.println("4. Customer number\n5. Exit to main menu");
            max = 5;
        } else if (person instanceof Employee) {
            System.out.println("4. Employee number \n5. Exit to main menu");
            max = 5;
        }
        else {
            System.out.println("4. Exit");
        }
        int choice = takeIntInput(1, max, "Enter choice");
        if(choice == max) printMainMenu();
        else {
            switch (choice) {
                case 1 -> updateName(person);
                case 2 -> updateAge(person);
                case 3 -> updateAdress(person);
                case 4 -> {
                    if(person instanceof Customer) {
                        updateCustomerNumber((Customer) person);
                    }
                    else if (person instanceof Employee) {
                        updateEmployeeNumber((Employee) person);
                    }
                    else {
                        System.err.println("Whoopsie, the object was neither a customer nor a employee, nothing to update!");
                    }
                }
            }
        }
    }
    private void viewAllPeople() {
        Person[] people = personService.getAllPersons();
        int i = 1;
        for(Person person : people) {
            System.out.println(i + ". " + person.getName());
            i++;
        }
        System.out.println(i + ". Exit to main menu");
        int choice = takeIntInput(1, i, "Enter choice");
        if(choice == i) printMainMenu();
        else {
            showPersonDetails(people[choice-1]);
        }
    }

    private void viewCustomers() {
        int i = 1;
        Customer[] customers = personService.getAllCustomers();
        for(Customer customer : customers) {
            System.out.println(i + ". " + customer.getName());
            i++;
        }
        System.out.println(i + ". Exit to main menu");
        int choice = takeIntInput(1, i, "Enter choice:");
        if(choice == i) printMainMenu();
        else {
            showPersonDetails(customers[choice-1]);
        }
    }

    private void viewEmployees() {
        int i = 1;
        Employee[] employees = personService.getAllEmployees();
        for(Employee employee : employees) {
            System.out.println(i + ". " + employee.getName());
            i++;
        }
        System.out.println(i + ". Exit to main menu");
        int choice = takeIntInput(1, i, "Enter choice");
        if(choice == i) printMainMenu();
        else {
            showPersonDetails(employees[choice-1]);
        }
    }

    private void viewPeople() {
        System.out.println("""
                1. View all customers\s
                2. View all employees\s
                3. View all people\s
                4. Back to main menu""");
        switch (takeIntInput(1,4, "Enter choice")) {
            case 1 -> viewCustomers();
            case 2 -> viewEmployees();
            case 3 -> viewAllPeople();
        }
    }
}