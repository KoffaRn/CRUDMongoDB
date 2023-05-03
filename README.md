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
  + addPerson(Person person): Adds a new person to the database.
  + getPeopleFromDocs