package org.koffa.People;

import org.bson.Document;

public class Person {
    private String name;
    private int age;
    private String adress;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Person(String name, int age, String adress, String id) {
        this.name = name;
        this.age = age;
        this.adress = adress;
        this.id = id;
    }
    public Person() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
    public Person fromDocument(Document doc) {
        Person person = new Person();
        person.setName(doc.getString("name"));
        person.setAge(doc.getInteger("age"));
        person.setAdress(doc.getString("adress"));
        person.setId(String.valueOf(doc.getObjectId("_id")));
        return person;
    }
    public Document toDocument() {
        Document doc = new Document();
        doc.append("name", getName());
        doc.append("age", getAge());
        doc.append("adress", getAdress());
        doc.append("_id", getId());
        return doc;
    }
    public String getType() {
        return "person";
    }
    @Override
    public String toString() {
        return """
                Person
                Name: %s
                Age: %d
                Adress: %s
                """.formatted(getName(), getAge(), getAdress());
    }
}