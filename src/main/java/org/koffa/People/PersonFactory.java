package org.koffa.People;
import org.bson.Document;
public class PersonFactory {
    public static Person createPerson(Document document) {
        String type = document.getString("type");
        if(type != null) {
            if(type.equals("employee")) {
                return new Employee().fromDocument(document);
            } else if(type.equals("customer")) {
                return new Customer().fromDocument(document);
            }
        }
        return new Person().fromDocument(document);
    }
}
