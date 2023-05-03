package org.koffa.People;
import org.bson.Document;
import org.koffa.DBManager;

public class PersonService {
    private final DBManager dbManager;

    public PersonService() {
        String collectionName = "people";
        this.dbManager = new DBManager("Cluster0", collectionName);
    }

    /**
     * Creates a new person in the database
     * @param person to add
     */
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

    /**
     * Gets all customers from the database
     * @return Customer[] of all customers
     */
    public Customer[] getAllCustomers() {
        Document[] documents = dbManager.getByField("type", "customer");
        Customer[] customers = new Customer[documents.length];
        int i = 0;
        for(Document document : documents) {
            customers[i] = new Customer().fromDocument(document);
            i++;
        }
        return customers;
    }

    /**
     * Gets all employees from the database
     * @return Employee[] of all employees
     */
    public Employee[] getAllEmployees() {
        Document[] documents = dbManager.getByField("type", "employee");
        Employee[] employees = new Employee[documents.length];
        int i = 0;
        for(Document document : documents) {
            employees[i] = new Employee().fromDocument(document);
            i++;
        }
        return employees;
    }

    /**
     * Gets all persons from the database
     * @return Person[] of all persons
     */
    public Person[] getAllPersons() {
        Document[] documents = dbManager.getAll();
        return getPeople(documents);
    }

    /**
     * Gets one person object based on ID from the database
     * @param id of the person
     * @return Person object with id
     */
    public Person getPersonByID(String id) {
        Document document = dbManager.read(id);
        if(document.getString("type") != null) {
            switch(document.getString("type")) {
                case "customer" -> {
                    return new Customer().fromDocument(document);
                }
                case "employee" -> {
                    return new Employee().fromDocument(document);
                }
                default -> {
                    return new Person().fromDocument(document);
                }
            }
        }
        return new Person().fromDocument(document);
    }

    /**
     * Drops the collection from the database, removes all people
     */
    public void removeCluster() {
        dbManager.dropCollection();
    }

    /**
     * Removes a person from the database
     * @param person to remove
     */
    public void removePerson(Person person) {
        dbManager.delete(person.getId());
    }

    /**
     * Searches the database for a term in a field
     * @param field to search in
     * @param term to search for
     * @return Person[] of all persons that match the search
     */
    public Person[] search(String field, String term) {
        Document[] documents = dbManager.search(field,term);
        return getPeople(documents);
    }
    public Person[]search(String field, int value) {
        Document[] documents = dbManager.search(field,value);
        return getPeople(documents);
    }

    private Person[] getPeople(Document[] documents) {
        Person[] persons = new Person[documents.length];
        int i = 0;
        for(Document document : documents) {
            persons[i] = getPersonByID(String.valueOf(document.getObjectId("_id")));
            i++;
        }
        return persons;
    }
    public void update(Person person, String field, String value) {
        Document document = person.toDocument();
        document.replace(field,value);
        updatePerson(person,document);
    }
    public void update(Person person, String field, int value) {
        Document document = person.toDocument();
        document.replace(field,value);
        updatePerson(person,document);
    }
    /**
     * Updates a person in the database
     * @param person to update
     * @param document to update with
     */
    private void updatePerson(Person person, Document document) {
        dbManager.update(person.getId(), document);
    }

}
