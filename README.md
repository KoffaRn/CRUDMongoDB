# CRUDMongoDB
A simple CRUD using MongoDB and Java. Built with Maven.

+ DBManager.java: Class that manages the connection with the database.
  + create(Document document): Creates a new document in the database.  
  + read(String key, String value): Reads a document from the database.
  + update(String key, String value, Document document): Updates a document from the database.
  + delete(String key, String value): Deletes a document from the database.
  + readAll(): Reads all the documents from the database.
  + search(String key, String value / int value): Searches a document from the database.
  + dropCollection(): Deletes the collection from the database.
+ PersonService.java: Class that manages the CRUD operations.
  + addPerson(Person person) adds a person to the database
  + getAllPeople() returns all people in the database
  + getPeopleByFieldValue(String field, String value) returns people by a field and value
  + getPersonById(String id) returns a person by id
  + removeCluster() drops the collection
  + removePerson(Person person) removes a person from the database
  + search(String field, String term / int value) searches a person by a field and term
  + update(Person person, String field, String value / int value) updates a person by a field and value
+ Person.java: Class that represents a person.
+ Employee.java: Class that represents an employee.
+ Customer.java: Class that represents a customer.
+ PersonFactory.java: Class that creates a person depending on the type.
+ ConfigManager.java: Class that manages the configuration of the database.
+ ConsoleInterface.java: Class that manages the console interface.
+ DataFaker.java: Class that generates random data.
+ Main.java: Class that runs the program.