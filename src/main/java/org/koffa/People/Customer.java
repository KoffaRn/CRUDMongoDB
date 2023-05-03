package org.koffa.People;

import org.bson.Document;

public class Customer extends Person{
    private int customerNumber;

    public Customer() {

    }
    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }
    @Override
    public Customer fromDocument(Document doc) {
        Customer customer = new Customer();
        customer.setName(doc.getString("name"));
        customer.setAge(doc.getInteger("age"));
        customer.setAdress(doc.getString("adress"));
        customer.setCustomerNumber(doc.getInteger("customer_number"));
        customer.setId(String.valueOf(doc.getObjectId("_id")));
        return customer;
    }
    @Override
    public Document toDocument() {
        Document doc = new Document();
        doc.append("name", getName());
        doc.append("age", getAge());
        doc.append("adress", getAdress());
        doc.append("type", "customer");
        doc.append("customer_number", getCustomerNumber());
        return doc;
    }
    @Override
    public String toString() {
        return """
                Customer
                Name: %s
                Age: %d
                Adress: %s
                Customer number: %d
                """.formatted(getName(), getAge(), getAdress(), getCustomerNumber());
    }
}