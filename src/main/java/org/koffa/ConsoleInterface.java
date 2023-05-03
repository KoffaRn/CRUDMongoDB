package org.koffa;

import org.koffa.People.Customer;
import org.koffa.People.Employee;
import org.koffa.People.Person;
import org.koffa.People.PersonService;
import java.util.Scanner;

public class ConsoleInterface {
    private final PersonService personService;
    private final DataFaker dataFaker;
    private boolean running = true;
    public ConsoleInterface() {
        this.personService = new PersonService();
        this.dataFaker = new DataFaker();
    }
    public boolean isRunning() {
        return running;
    }
    public void printMainMenu() {
        System.out.println("""
                1. Create person\s
                2. View, update & delete people\s
                3. Search people\s
                4. Generate random people\s
                5. Drop cluster (remove all people)\s
                6. Quit""");
        switch (takeIntInput(1, 6, "Enter choice")) {
            case 1 -> addNewPerson();
            case 2 -> viewPeople();
            case 3 -> searchPeople();
            case 4 -> generateRandomPeople();
            case 5 -> dropCluster();
            case 6 -> running = false;
        }
    }

    private void generateRandomPeople() {
        System.out.println("""
                1. Generate employees\s
                2. Generate customers\s
                3. Generate persons\s
                4. Back to main menu
                """);
        switch (takeIntInput(1,3, "Enter choice")) {
            case 1 -> generateEmployees();
            case 2 -> generateCustomers();
            case 3 -> generatePeople();
            case 4 -> printMainMenu();
        }
    }

    private void generateEmployees() {
        dataFaker.generateEmployeesToDB(takeIntInput(1, 1000, "Enter amount of employees to generate (1-1000)"));
    }
    private void generateCustomers() {
        dataFaker.generateCustomersToDB(takeIntInput(1, 1000, "Enter amount of customers to generate (1-1000)"));
    }
    private void generatePeople() {
        dataFaker.generatePeopleToDB(takeIntInput(1, 1000, "Enter amount of people to generate (1-1000)"));
    }

    private void searchPeople() {
        System.out.println("""
                1. Search by name\s
                2. Search by adress\s
                3. Search by employee number\s
                4. Search by customer number\s
                5. Search by age\s
                6. Back to main menu
                """);
        switch (takeIntInput(1,6, "Enter choice:")) {
            case 1 -> search("name", takeStringInput("Enter name:"));
            case 2 -> search("adress", takeStringInput("Enter adress:"));
            case 3 -> search("employee_number", takeIntInput(0, 1000000, "Enter employee number:"));
            case 4 -> search("customer_number", takeIntInput(0, 1000000, "Enter customer number:"));
            case 5 -> search("age", takeIntInput(0, 150, "Enter age:"));
            case 6 -> printMainMenu();
        }
    }
    private void search(String field, String term) {
        Person[] result = personService.search(field, term);
        showPeople(result);
    }
    private void search(String field, int value) {
        Person[] result = personService.search(field, value);
        showPeople(result);
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
        System.out.println(person + "\n--------------------------------------------------");
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
    private void updatePerson(Person person) {
        System.out.println("""
                What do you want to edit\s
                1. Name
                2. Age
                3. Adress""");
        //max for takeIntInput is 4 if person is not customer or employee
        int max = 4;
        if(person instanceof Customer) {
            System.out.println("4. Customer number\n5. Exit to main menu");
            //max for takeIntInput is 5 if person is customer
            max = 5;
        } else if (person instanceof Employee) {
            System.out.println("4. Employee number \n5. Exit to main menu");
            //max for takeIntInput is 5 if person is employee
            max = 5;
        }
        else {
            System.out.println("4. Exit");
        }
        int choice = takeIntInput(1, max, "Enter choice");
        if(choice == max) printMainMenu();
        else {
            switch (choice) {
                case 1 -> personService.update(person, "name", takeStringInput("Enter new name:"));
                case 2 -> personService.update(person, "age", takeIntInput(0, 150, "Enter new age:"));
                case 3 -> personService.update(person, "adress", takeStringInput("Enter new adress:"));
                case 4 -> {
                    if(person instanceof Customer) {
                        personService.update(person, "customer_number", takeIntInput(0, Integer.MAX_VALUE, "Enter customer number:"));
                    }
                    else if (person instanceof Employee) {
                        personService.update(person, "employee_number", takeIntInput(0, Integer.MAX_VALUE, "Enter employee number:"));
                    }
                    else {
                        System.err.println("Whoopsie, the object was neither a customer nor a employee, nothing to update!");
                    }
                }
            }
        }
    }

    private void showPeople(Person[] people) {
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

    private void viewPeople() {
        System.out.println("""
                1. View all customers\s
                2. View all employees\s
                3. View all people\s
                4. Back to main menu""");
        switch (takeIntInput(1,4, "Enter choice")) {
            case 1 -> showPeople(personService.getPeopleByFieldValue("type", "customer"));
            case 2 -> showPeople(personService.getPeopleByFieldValue("type", "employee"));
            case 3 -> showPeople(personService.getAllPeople());
        }
    }
}