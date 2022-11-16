**Overview**
1. To start the project, go to Main and run main method.
You should then see something like this:
Jersey app started with endpoints available at http://localhost:8080/

2. Project developed using JDK 8, HK2 injection, Jersey Grizzly (no need of Tomcat), mapstruct for entity to dto conversion and vice versa, Hibernate to save data to database, MySQL 8 database instance.

3. Go to src/main/resources/hibernate.cfg.xml and adjust the entry values for the DB, userName, password, hibernate.hbm2ddl.auto
For the very first run, make hibernate.hbm2ddl.auto as create and then change it once schema is created.

4. There is also a possibility of inserting seed data in the database. This is done by running the main method in CustomerDAO class.
NOTE: Be careful, the main method will create 56 customer records in the database.
The actual customer creation is done by createMultipleCustomers() method in CustomerDAO class

**End Points**
Entry to the api is via the endpoint: http://localhost:8080/customer-api

Available end points (mostly happy paths have been implemented and a few negative paths as well);
a) /register: (POST) This creates a new customer or returns the customer if it exists. EmailAddress is the primary key.  
b) /customer?emailAddress=pagecust_1_@gmail.com: (GET) Seraches for a customer using email address and returns the customer info or customer not found message.  
c) /customer: (PUT) Customer can update phone number (free format) and date of birth YYYY-MM-DD format when invoking the api.    
d) /customer/pagecust_1_@gmail.com: (DELETE) De-registers a customer.
e) /allCustomersPaginated?page=2 (or /allCustomersPaginated): Paginated customer list from the Database. It will return first 10 records as page 1. Provide query parameter "page=2" to get the page 2 result set. 

**Minor Info**
HTTP Status code is also sent in response.
Also one header is sent in some cases (just for trial)

**Example JSON**
POST request:  
{
    "customerName": "John Doe",
    "emailAddress": "johndoe4@gmail.com",
    "phoneNumber": "0046739182157",
    "dateOfBirth": "2012-10-20"
}

GET Response:  
{
    "active": true,
    "customerName": "John Doe II",
    "dateOfBirth": "2010-06-12",
    "emailAddress": "johndoe3@gmail.com",
    "phoneNumber": "001800500400"
    }

**Rainy Day Scenarios**
1. If a customer doesn't exist while searching, removing(de-registering) then return proper exception. (Works in postman and rest client in the test class)


**Requirements**

REQ-2022-11-12-001: Saving the customer profile data.

Requirement Added on: 2022-11-12

Requirement Details:
In this iteration, we will save customer's email address, customer name, phone, timestampcreated, active(true by default), phone number in database.

Implementation Method Name:

Test Cases:

Test Methods:

Tech Note:

Status: To do/Done
Requirement Finished On:

*****************************
REQ-2022-11-12-002: Fetch customer using email address

Requirement Added on: 2022-11-12

Business Requirement: We need to fetch customer using email address.
Properties to be shown to the client are email address, customer name, active, phonenumber.
We are not showing timestampcreated to the client.

Implementation Method Name:

Test Cases:

Test Methods:

Tech Note:

Status: To do/Done
Requirement Finished On:
**************************************
REQ-2022-11-12-003: Customer will have a field for date of birth. It is optional data for customer profile.
Customer can update profile and update either phone number or date of birth or both in a single transaction.

Requirement Added on: 2022-11-12

Business Requirement: This date of birth will later be used to give discounts to the customer.

Implementation Method Name:

Test Cases:

Test Methods:

Tech Note:

Status: To do/Done
Requirement Finished On:
**************************************
REQ-2022-11-15-004: Instead of just sending back the DTO class, we want to send Response with Http Status, Http header, and corresponding entity. Implement Response. 

Requirement Added on: 2022-11-15

Business Requirement:

Implementation Method Name:

Test Cases:

Test Methods: 2 test methods. 1st to check positive case when customer exists and 2nd for the case when customer doesn't exist in DB
test_search_a_customer_using_existing_emailAddress_should_return_response_object_And_Customer
test_search_a_customer_using_nonexisting_emailAddress_should_return_response_object_with_ErrorMessageObjectInstance()


Tech Note: Currently implemented as an extra method for the fetchCustomerByEmailReturnResponse() in class CustomerResource

Status: To do/Done
Requirement Finished On:
**************************************
REQ-2022-11-15-005: When api receives a request for all customers, then it should default to pagination.

Requirement Added on: 2022-11-15

Business Requirement: 1st 10 customers should be returned with a link in the response to fetch next set of customers

Implementation Method Name:

Test Cases:

Test Methods:

Tech Note: Using criteria api to fetch the paginated customer list

Status: To do/Done
Requirement Finished On:
**************************************
REQ-2022-11-16-006: Customer registration. A customer could register using email address. If the customer already exists with provided email address, then system will just return the matching customer.

Requirement Added on: 2022-11-16

Business Requirement: Create new customer or return an existing customer using email address.

Implementation Method Name:

Test Cases:

Test Methods:

Tech Note: What HTTP status code should be returned when a customer is newly created? 201-CREATED
What HTTP status code should be returned when a customer is already existing? TBD

Status: To do/Done
Requirement Finished On:
**************************************
REQ-2022-11-16-005: When de-registering a customer, if the customer does not exist then return an exception to the client stating that the customer was not found.

Requirement Added on: 2022-11-16

Business Requirement: Detailed Requirement

Implementation Method Name:

Test Cases:

Test Methods:

Tech Note: From DAO class throwing DataNotFoundException and it shows correctly in the response of Postman

Status: To do/Done
Requirement Finished On:
**************************************
REQ-2022-11-15-005: Brief requirement one-liner

Requirement Added on: YYYY-MM-DD

Business Requirement: Detailed Requirement

Implementation Method Name:

Test Cases:

Test Methods:

Tech Note: 

Status: To do/Done
Requirement Finished On:
**************************************
REQ-2022-11-15-005: Brief requirement one-liner

Requirement Added on: YYYY-MM-DD

Business Requirement: Detailed Requirement

Implementation Method Name:

Test Cases:

Test Methods:

Tech Note: 

Status: To do/Done
Requirement Finished On:
**************************************
REQ-2022-11-15-005: Brief requirement one-liner

Requirement Added on: YYYY-MM-DD

Business Requirement: Detailed Requirement

Implementation Method Name:

Test Cases:

Test Methods:

Tech Note: 

Status: To do/Done
Requirement Finished On:
**************************************
REQ-2022-11-15-005: Brief requirement one-liner

Requirement Added on: YYYY-MM-DD

Business Requirement: Detailed Requirement

Implementation Method Name:

Test Cases:

Test Methods:

Tech Note: 

Status: To do/Done
Requirement Finished On:
**************************************
REQ-2022-11-15-005: Brief requirement one-liner

Requirement Added on: YYYY-MM-DD

Business Requirement: Detailed Requirement

Implementation Method Name:

Test Cases:

Test Methods:

Tech Note: 

Status: To do/Done
Requirement Finished On:
**************************************
REQ-2022-11-15-005: Brief requirement one-liner

Requirement Added on: YYYY-MM-DD

Business Requirement: Detailed Requirement

Implementation Method Name:

Test Cases:

Test Methods:

Tech Note: 

Status: To do/Done
Requirement Finished On:
**************************************
REQ-2022-11-15-005: Brief requirement one-liner

Requirement Added on: YYYY-MM-DD

Business Requirement: Detailed Requirement

Implementation Method Name:

Test Cases:

Test Methods:

Tech Note: 

Status: To do/Done
Requirement Finished On:
**************************************
