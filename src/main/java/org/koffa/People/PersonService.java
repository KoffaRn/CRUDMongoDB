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
        dbManager.create(person.toDocument());
    }
    /**
     * Gets all persons from the database
     * @return Person[] of all persons
     */
    public Person[] getAllPeople() {
        return getPeopleFromDocs(dbManager.readAll());
    }
    public Person[] getPeopleByFieldValue(String field, String value) {
        return getPeopleFromDocs(dbManager.readByField(field,value));
    }

    /**
     * Gets one person object based on ID from the database
     * @param id of the person
     * @return Person object with id
     */
    public Person getPersonByID(String id) {
        Document document = dbManager.read(id);
        return PersonFactory.createPerson(document);
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
        return getPeopleFromDocs(documents);
    }
    public Person[]search(String field, int value) {
        Document[] documents = dbManager.search(field,value);
        return getPeopleFromDocs(documents);
    }
    private Person[] getPeopleFromDocs(Document[] documents) {
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
