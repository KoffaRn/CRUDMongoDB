package org.koffa.Person;
import org.bson.Document;
import org.koffa.DBManager;

public class PersonService {
    private final DBManager dbManager;
    private final String collectionName = "people";
    public PersonService() {
        this.dbManager = new DBManager("Cluster0", collectionName);
    }
    public void addPerson(Person person) {
        Document document = new Document("name", person.getName())
                .append("age", person.getAge())
                .append("adress", person.getAdress());
        if(person instanceof Customer) {
            document.append("type", "customer");
            document.append("customer_number", ((Customer) person).getCustomerNumber());
        }
        if(person instanceof Employee) {
            document.append("type", "employee");
            document.append("employee_number", ((Employee) person).getEmployeeNumber());
        }
        dbManager.create(document);
    }
    public void removePerson(Person person) {
        dbManager.delete(person.getId());
    }
    public Person getPersonByID(String id) {
        Document document = dbManager.read(id);
        if(document.getString("type") != null) {
            switch(document.getString("type")) {
                case "customer" -> {
                    return newCustomerFromDocument(document);
                }
                case "employee" -> {
                    return newEmployeeFromDocument(document);
                }
                default -> {
                    return newPersonFromDocument(document);
                }
            }
        }
        return newPersonFromDocument(document);
    }
    public Person[] getAllPersons() {
        Document[] documents = dbManager.getAll();
        Person[] persons = new Person[documents.length];
        int i = 0;
        for(Document document : documents) {
            persons[i] = getPersonByID(String.valueOf(document.getObjectId("_id")));
            i++;
        }
        return persons;
    }
    public Customer[] getAllCustomers() {
        Document[] documents = dbManager.getByField("type", "customer");
        Customer[] customers = new Customer[documents.length];
        int i = 0;
        for(Document document : documents) {
            customers[i] = newCustomerFromDocument(document);
            i++;
        }
        return customers;
    }
    public Employee[] getAllEmployees() {
        Document[] documents = dbManager.getByField("type", "employee");
        Employee[] employees = new Employee[documents.length];
        int i = 0;
        for(Document document : documents) {
            employees[i] = newEmployeeFromDocument(document);
            i++;
        }
        return employees;
    }
    private Person newPersonFromDocument(Document document) {
        Person person = new Person();
        setPersonDetails(person, document);
        return person;
    }
    private Customer newCustomerFromDocument(Document document) {
        Customer customer = new Customer();
        setCustomerDetails(customer, document);
        return customer;
    }
    private Employee newEmployeeFromDocument(Document document) {
        Employee employee = new Employee();
        setEmployeeDetails(employee, document);
        return employee;
    }
    private void setPersonDetails(Person person, Document document) {
        person.setAdress(document.getString("adress"));
        person.setName(document.getString("name"));
        person.setAge(document.getInteger("age"));
        person.setId(String.valueOf(document.getObjectId("_id")));
    }
    private void setEmployeeDetails(Employee employee, Document document) {
        setPersonDetails(employee, document);
        employee.setEmployeeNumber(document.getInteger("employee_number"));
    }
    private void setCustomerDetails(Customer customer, Document document) {
        setPersonDetails(customer, document);
        customer.setCustomerNumber(document.getInteger("customer_number"));
    }
    public void updateName(Person person, String name) {
        Document document = documentFromPerson(person);
        document.replace("name",name);
        updatePerson(person,document);
    }
    public void updateAge(Person person, int age) {
        Document document = documentFromPerson(person);
        document.replace("age", age);
        updatePerson(person,document);
    }
    public void updateAdress(Person person, String adress) {
        Document document = documentFromPerson(person);
        document.replace("adress",adress);
        updatePerson(person,document);
    }
    public void updateCustomerNumber(Customer customer, int customerNumber) {
        Document document = documentFromPerson(customer);
        document.replace("costumer_number", customerNumber);
        updatePerson(customer,document);
    }
    public void updateEmployeeNumber(Employee employee, int employeeNumber) {
        Document document = documentFromPerson(employee);
        document.replace("employee_number", employeeNumber);
        updatePerson(employee,document);
    }
    private Document documentFromPerson(Person person) {
        Document document = new Document("name", person.getName())
                .append("_id",person.getId())
                .append("age",person.getAge())
                .append("adress",person.getAdress());
        if(person instanceof Customer) {
            document.append("customer_number",((Customer) person).getCustomerNumber());
            document.append("type","customer");
        }
        if(person instanceof Employee) {
            document.append("employee_number",((Employee) person).getEmployeeNumber());
            document.append("type","employee");
        }
        return document;
    }
    private void updatePerson(Person person, Document document) {
        dbManager.update(person.getId(), document);
    }
    public void removeCluster() {
        dbManager.dropCluster();
    }
}
