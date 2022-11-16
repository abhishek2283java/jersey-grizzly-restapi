package org.abhishek.customerapi.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.abhishek.customerapi.Main;
import org.abhishek.customerapi.model.Customer;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CustomerResourceCreateMultipleCustomersTest {
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
        target = client.target("http://localhost:8080/customer-api");

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        //c.getConfiguration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        //target = c.target(Main.BASE_URI);
        System.out.println("setUp completed");
    }
    
    @After
    public void tearDown() throws Exception {
        server.stop();
        System.out.println("teardown completed");
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

}
