package org.koffa.People;

import org.bson.Document;

public class Employee extends Person {
    private int employeeNumber;
    public Employee() {
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
    @Override
    public Employee fromDocument(Document doc) {
        if(doc == null) return new Employee();
        Employee employee = new Employee();
        employee.setName(doc.getString("name"));
        employee.setAge(doc.getInteger("age"));
        employee.setAdress(doc.getString("adress"));
        employee.setEmployeeNumber(doc.getInteger("employee_number"));
        employee.setId(String.valueOf(doc.getObjectId("_id")));
        return employee;
    }
    @Override
    public Document toDocument() {
        return new Document().append("name", getName())
        .append("age", getAge())
        .append("adress", getAdress())
        .append("type", "employee")
        .append("employee_number", getEmployeeNumber())
        .append("_id", getId());
    }
    @Override
    public String getType() {
        return "employee";
    }
    @Override
    public String toString() {
        return """
                Employee
                Name: %s
                Age: %d
                Adress: %s
                Employee number: %d
                """.formatted(getName(), getAge(), getAdress(), getEmployeeNumber());
    }
}
