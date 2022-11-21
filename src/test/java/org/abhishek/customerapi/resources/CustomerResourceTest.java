package org.abhishek.customerapi.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.abhishek.customerapi.Main;
import org.abhishek.customerapi.dto.CustomerDTO;
import org.abhishek.customerapi.model.Customer;
import org.abhishek.customerapi.model.ErrorMessage;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CustomerResourceTest {
	
    private HttpServer server;
    private WebTarget target;
    
    @Before
    public void setUp() throws Exception {
    	System.out.println("setUp was invoked");
        // start the server
        server = Main.startServer();
        // create the client
        //Client c = ClientBuilder.newClient();
        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        Client client = clientBuilder.build();
        target = client.target("http://localhost:8080/customer-api/v2");

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        //c.getConfiguration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        //target = c.target(Main.BASE_URI);
        System.out.println("setUp completed");
    }
    
    @SuppressWarnings("deprecation")
	@After
    public void tearDown() throws Exception {
        server.stop();
        System.out.println("teardown completed");
    }
    
    @Test
    public void test_post_to_create_a_new_customer() {
    	CustomerDTO customerDTO = new CustomerDTO();
    	customerDTO.setEmailAddress("abc001client@gmail.com");
    	customerDTO.setCustomerName("ClientCustomer 1");
    	Invocation.Builder invocationBuilder = target.path("customer")
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    	Response response = invocationBuilder
                .post(Entity.entity(customerDTO, MediaType.APPLICATION_JSON));
    	System.out.println("Received response in test method");
    	//CustomerDTO createdEntity = (CustomerDTO) response.getEntity();
    	CustomerDTO createdEntity = response.readEntity(CustomerDTO.class);
    	if(createdEntity != null) {
    		System.out.println("Created Customer is active = " + createdEntity.isActive());
    	}
    	assertTrue(createdEntity.isActive());
    }
    
    @Test
    public void test_post_to_create_a_new_customer_with_DOB() {
    	CustomerDTO customerDTO = new CustomerDTO();
    	customerDTO.setEmailAddress("abc002client@gmail.com");
    	customerDTO.setCustomerName("Abhikriti Choudhary");
//        GregorianCalendar gc = new GregorianCalendar(2012, 3, 4);
//        Date dob = gc.getTime();
//        System.out.println(dob);
        customerDTO.setDateOfBirth("2012-04-04");
        customerDTO.setPhoneNumber("0046765994360");
        
    	Invocation.Builder invocationBuilder = target.path("customer")
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    	Response response = invocationBuilder
                .post(Entity.entity(customerDTO, MediaType.APPLICATION_JSON));
    	System.out.println("Received response in test method");
    	//CustomerDTO createdEntity = (CustomerDTO) response.getEntity();
    	CustomerDTO createdEntity = response.readEntity(CustomerDTO.class);
    	System.out.println("Created Customer is active = " + createdEntity.isActive());
    	assertTrue(createdEntity.isActive());
    }
    
    @Test
    public void test_update_a_customer_with_DOB_and_phoneNumber() {
    	CustomerDTO customerDTO = new CustomerDTO();
    	customerDTO.setEmailAddress("abc004client@gmail.com");
    	customerDTO.setDateOfBirth("1986-01-01");
    	customerDTO.setPhoneNumber("0046765994361");
//        GregorianCalendar gc = new GregorianCalendar(2012, 3, 4);
//        Date dob = gc.getTime();
//        System.out.println(dob);
        
    	Invocation.Builder invocationBuilder = target.path("customer")
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    	Response response = invocationBuilder
                .put(Entity.entity(customerDTO, MediaType.APPLICATION_JSON));
    	System.out.println("Received response in test method");
    	//CustomerDTO createdEntity = (CustomerDTO) response.getEntity();
    	CustomerDTO updatedDTO = response.readEntity(CustomerDTO.class);
    	System.out.println("Updated Customer is has new phone = " + updatedDTO.getPhoneNumber());
    	assertEquals("0046765994361", updatedDTO.getPhoneNumber());
    }
    
    @Test
    public void test_update_a_customer_with_DOB_and_phoneNumber_Method2() {
    	CustomerDTO customerDTO = new CustomerDTO();
    	customerDTO.setEmailAddress("marie_montessori@gmail.com");
    	customerDTO.setDateOfBirth("1987-01-01");
    	customerDTO.setPhoneNumber("0046765994362");
    	
    	Invocation buildPut = target
    			.path("customer")
    			.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .buildPut(Entity.entity(customerDTO, MediaType.APPLICATION_JSON));
        
//    	Invocation.Builder invocationBuilder = target.path("customer")
//                .request(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON);
//    	Response response = invocationBuilder
//                .put(Entity.entity(customerDTO, MediaType.APPLICATION_JSON));
    	
    	CustomerDTO updatedDTO = buildPut.invoke(CustomerDTO.class);
    	System.out.println("Received response in test method for update Customer");
    	//CustomerDTO updatedDTO = response.readEntity(CustomerDTO.class);
   
    	System.out.println("Updated Customer is has new phone = " + updatedDTO.getPhoneNumber());
    	assertEquals("0046765994362", updatedDTO.getPhoneNumber());
    }
    
    @Test
    public void test_register_a_new_customer_should_create_and_return_the_active_customer() {
    	CustomerDTO customerDTO = new CustomerDTO();
    	customerDTO.setEmailAddress("abc009client@gmail.com");
    	customerDTO.setCustomerName("ClientCustomer 9");
    	Invocation.Builder invocationBuilder = target.path("register")
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    	Response response = invocationBuilder
                .post(Entity.entity(customerDTO, MediaType.APPLICATION_JSON));
    	System.out.println("Received response in test method");
    	//CustomerDTO createdEntity = (CustomerDTO) response.getEntity();
    	CustomerDTO customer = response.readEntity(CustomerDTO.class);
    	System.out.println("Read DTO");
    	System.out.println("Registered Customer is active = " + customer.isActive());
    	assertTrue(customer.isActive());
    }
    
    @Test
    public void test_search_a_customer_using_emailAddress() {
    	String emailAddress = "pagecust_10_@gmail.com";

//        GregorianCalendar gc = new GregorianCalendar(2012, 3, 4);
//        Date dob = gc.getTime();
//        System.out.println(dob);
        
    	Invocation buildGet = target
    			.path("customer")
    			.queryParam("emailAddress", emailAddress)
    			.request()
                .accept(MediaType.APPLICATION_JSON)
                .buildGet();
    	System.out.println("Invocation Done");
    	CustomerDTO customer = buildGet.invoke(CustomerDTO.class);
    	if(customer != null ) {
    		System.out.println(
    				"Customer found for emailAddress '" + emailAddress + "' and is active: '" + customer.isActive() + "'"
    				);
    	} else {
    		System.out.println("Customer not found for emailAddress: '" + emailAddress + "'");
    	}
    	//CustomerDTO createdEntity = (CustomerDTO) response.getEntity();
    	//CustomerDTO createdEntity = customer.readEntity(CustomerDTO.class);
    	assertTrue(customer.isActive());
    }
    
    @Test
    public void test_search_a_customer_using_nonexisting_emailAddress_should_return_a_null_customer() {
    	String emailAddress = "abc004NONclient@gmail.com";

//        GregorianCalendar gc = new GregorianCalendar(2012, 3, 4);
//        Date dob = gc.getTime();
//        System.out.println(dob);
        
    	Invocation buildGet = target
    			.path("customer")
    			.queryParam("emailAddress", emailAddress)
    			.request()
                .accept(MediaType.APPLICATION_JSON)
                .buildGet();
    	System.out.println("Invocation Done");
    	CustomerDTO customer = buildGet.invoke(CustomerDTO.class);
    	if(customer != null ) {
    		System.out.println(
    				"Customer found for emailAddress '" + emailAddress + "' and is active: '" + customer.isActive() + "'"
    				);
    	} else {
    		System.out.println("Customer not found for emailAddress: '" + emailAddress + "'");
    	}
    	//CustomerDTO createdEntity = (CustomerDTO) response.getEntity();
    	//CustomerDTO createdEntity = customer.readEntity(CustomerDTO.class);
    	assertNull(customer);
    }

    //2022-11-15 works fine to read the ErrorMessage entity and assert is fine with error message.
    @Test
    public void test_search_a_customer_using_nonexisting_emailAddress_should_return_response_object_with_ErrorMessageObjectInstance() {
    	String emailAddress = "abc004NONclient@gmail.com";
        
    	Invocation buildGet = target
    			.path("customerV2")
    			.queryParam("emailAddress", emailAddress)
    			.request()
                .accept(MediaType.APPLICATION_JSON)
                .buildGet();
    	System.out.println("Invocation Done");
    	Response response = buildGet.invoke(Response.class);
    	System.out.println("Http Status from response: " + response.getStatus());
    	ErrorMessage errObj = response.readEntity(ErrorMessage.class);
    	//System.out.println("ActualError: " + errObj.getErrorMessage());
    	assertEquals(404, response.getStatus());
    	assertEquals("Customer with email " + emailAddress + " not found", errObj.getErrorMessage());
    }
    
    //2022-11-15 works fine to read the entity and assert is fine with customer name.
    @Test
    public void test_search_a_customer_using_existing_emailAddress_should_return_response_object_And_Customer() {
    	String emailAddress = "abc001@gmail.com";
        
    	Invocation buildGet = target
    			.path("customerV2")
    			.queryParam("emailAddress", emailAddress)
    			.request()
                .accept(MediaType.APPLICATION_JSON)
                .buildGet();
    	System.out.println("Invocation Done");
    	Response response = buildGet.invoke(Response.class);
    	System.out.println("Http Status from response: " + response.getStatus());
    	//CustomerDTO customerDTO = response.readEntity(CustomerDTO.class);
    	//System.out.println("Customer name: " + customerDTO.getCustomerName());
    	assertEquals(200, response.getStatus());
    	assertEquals("Customer 1", response.readEntity(CustomerDTO.class).getCustomerName());
    }
    
    @Test
    public void test_create_multiple_Customers_for_Pagination_Testing1() {
    	int numberOfCustObjects = 10;
    	List<Customer> customers = createCustomerObjects(numberOfCustObjects);
    	for(Customer customer : customers) {
    		//createCustomer(customer);
    	}
    	customers.forEach(System.out::println);
    }

	private List<Customer> createCustomerObjects(int numberOfCustObjects) {
		List<Customer> customerObjects = new ArrayList<>();
		for(int i = 1; i <= numberOfCustObjects; i++) {
			Customer customer = new Customer();
			customer.setCustomerName("PaginatedCustomer " + i);
			customer.setEmailAddress("pagecust_" + i + "_@gmail.com");
			customerObjects.add(customer);
		}
		return customerObjects;
	}
	
    //2022-11-16 works fine to read the entity and assert is fine with customer name.
    @Test
    public void test_deregister_a_customer_using_existing_emailAddress_should_return_response_object_And_Customer_removed() {
    	String emailAddress = "pagecust_12_@gmail.com";
        
    	Invocation buildDelete = target
    			.path("customer/{emailAddress}")
    			.resolveTemplate("emailAddress", emailAddress)
    			.request()
                .accept(MediaType.APPLICATION_JSON)
                .buildDelete();
    	System.out.println("Invocation Done");
    	CustomerDTO response = buildDelete.invoke(CustomerDTO.class);//this will work when the controller is returning CustomerDTO.class object
    	System.out.println("Customer name for the removed customer: " + response.getCustomerName());
    	assertEquals("PaginatedCustomer 12", response.getCustomerName());
    	//assertEquals("Customer 1", response.readEntity(CustomerDTO.class).getCustomerName());
    }
    
    //2022-11-16 works fine to read the error message object from the response object and then extracts the error message and asserts
    @Test
    public void test_deregister_a_customer_using_nonexisting_emailAddress_should_return_Errorresponse_object() {
    	String emailAddress = "pagecust_12_@gmail.com";
    	Invocation buildDelete = target
    			.path("customer/{emailAddress}")
    			.resolveTemplate("emailAddress", emailAddress)
    			.request()
                .accept(MediaType.APPLICATION_JSON)
                .buildDelete();
    	System.out.println("Invocation Done");
    	
    	//CustomerDTO response = buildDelete.invoke(CustomerDTO.class);//this will work when the controller is returning CustomerDTO.class object
    	//NOTE: We are invoking the end point and expecting the Response.class object. 
    	//Here controller will return ErrorMessage wrapped inside Response object 
    	//because we are throwing DataNotFoundException when customer is not found and 
    	//there is also an ExceptionMapper class defined for this exception 
    	//which will translate the exception to a Respnse.class object with ErrorMessage object wrapped inside it.
    	Response response = buildDelete.invoke(Response.class);
    
    	System.out.println("Response status: " + response.getStatus());			//Extract the HTTP response status from the Response.class object
    	ErrorMessage errorMessage = response.readEntity(ErrorMessage.class);	//Extract the ErrorMessage object from the Response.class object
    	System.out.println("De-register endpoint returned exception message: " + errorMessage.getErrorMessage());
    	assertEquals(404, errorMessage.getErrorCode());
    }
}
