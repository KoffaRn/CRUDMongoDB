package org.koffa.Person;

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
}