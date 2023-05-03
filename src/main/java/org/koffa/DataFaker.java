package org.koffa;

import net.datafaker.Faker;
import org.koffa.People.Customer;
import org.koffa.People.Employee;
import org.koffa.People.Person;
import org.koffa.People.PersonService;

public class DataFaker extends Faker {
    PersonService personService;
    public DataFaker() {
        this.personService = new PersonService();
    }
    private Customer[] generateCustomers(int amount) {
        Customer[] customers = new Customer[amount];
        for(int i = 0; i < amount; i++) {
            customers[i] = new Customer();
            customers[i].setName(name().fullName());
            customers[i].setAge(number().numberBetween(18, 100));
            customers[i].setAdress(address().fullAddress());
            customers[i].setCustomerNumber(number().numberBetween(1000, 9999));
        }
        return customers;
    }
    public void generateCustomersToDB(int amount) {
        Customer[] customers = generateCustomers(amount);
        for(Customer customer : customers) {
            personService.addPerson(customer);
        }
    }
    private Employee[] generateEmployees(int amount) {
        Employee[] employees = new Employee[amount];
        for(int i = 0; i < amount; i++) {
            employees[i] = new Employee();
            employees[i].setName(name().fullName());
            employees[i].setAge(number().numberBetween(18, 100));
            employees[i].setAdress(address().fullAddress());
            employees[i].setEmployeeNumber(number().numberBetween(1000, 9999));
        }
        return employees;
    }
    public void generateEmployeesToDB(int amount) {
        Employee[] employees = generateEmployees(amount);
        for(Employee employee : employees) {
            personService.addPerson(employee);
        }
    }
    private Person[] generatePeople(int amount) {
        Person[] people = new Person[amount];
        for(int i = 0; i < amount; i++) {
            people[i] = new Person();
            people[i].setName(name().fullName());
            people[i].setAge(number().numberBetween(18, 100));
            people[i].setAdress(address().fullAddress());
        }
        return people;
    }
    public void generatePeopleToDB(int amount) {
        Person[] people = generatePeople(amount);
        for(Person person : people) {
            personService.addPerson(person);
        }
    }
}
