# CRUDMongoDB
A simple CRUD for a Person-cluster with employees and customers.
## DBManager
Manager for the MongoDB described in mongodb.config, takes string dbName and string collectionName as inputs to decide which db and collection to manage.
Explanation for the public methods and calls to collection.
### create(Document)
takes document as input and inserts into collection
### read(String id)
Takes id-string and returns document
### search(String field, String term)
Searches collection for term in field and returns Document[].
### delete(String id)
Takes id-string and deletes document from collection
### getByField(String field, String term)
takes string field and string term and returns Document[] based on field and term
### getAll
returns Document[] of all documents in collection
### update(String id, Document document)
Takes string id and document and uptades based on id (removes "_id" from document if it has it to not make duplicates).

## PersonService
Business-logic service for Person, Employee and Customer. Uses DBManager with people-collection.
Explanation of public methods:
### addPerson(Person person)
Takes person-object as input and adds to database. Checks if the object is customer or employee and inserts relevant info.
### removePerson(Person person)
Removes person from collection based on id.
### getPersonByID(String id)
Returns person-object depending on id. If the person is Costumer, returns Customer-object, if employee returns Employee-object, otherwise Person-object.
### getAllPersons()
returns Person[] with all persons in collection.
### getAllCustomers()
returns Customer[] with all customers in collection using DBManager.getByField
### getAllEmployees()
returns Employee[] with all employees in collection using DBManager.getByField
### search(String field, String term)
returns Person[] with search term in field.
### updateName(Person person, String name)
updates name of person to new name.
### updateAge(Person person, int age)
updates age of person to new age.
### updateAdress(Person person, String adress)
updates adress of person to new adress.
### updateEmployeeNumber(Employee employee, int emplyeeNumber)
updates employeenumber of employee to new number
### updateCustomerNumber(Customer customer, int customerNumber)
Updates customernumber of customer to new number
