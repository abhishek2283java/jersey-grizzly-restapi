package org.abhishek.customerapi.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.abhishek.customerapi.dto.CustomerDTO;
import org.abhishek.customerapi.exception.DataNotFoundException;
import org.abhishek.customerapi.model.Customer;
import org.abhishek.customerapi.service.CustomerService;
import org.abhishek.customerapi.util.CustomerMapper;

@Path("customer-api")
public class CustomerResource {
	
	//private CustomerServiceImpl customerService = new CustomerServiceImpl();
	
	@Inject
    private CustomerService customerService;
	
	/**
     * Registers a customer. If the customer exists, just return the customer data else create and return
     * 
     * @param CustomerDTO customerDTO
     * @return CustomerDTO Customer instance converted to CustomerDTO
     * @throws Exception
     */
	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerCustomer(CustomerDTO customerDTO) throws Exception {
		System.out.println("Customer Resource registerCustomer called");
		Customer customer = toEntity(customerDTO);
		Customer createdCustomer = customerService.registerCustomer(customer);
		//Convert to DTO and return
		CustomerDTO createdCustomerDTO = toDTO(createdCustomer);
		System.out.println("Customer Resource registerCustomer will return the customer dto: " + createdCustomerDTO);
		return Response
				.status(Status.CREATED)
				.header("PoweredBy", "X-SAFE Tech")
				.entity(customerDTO)
				.build();
	}
	
	/**
     * Registers a customer. If the customer exists, just return the customer data else create and return
     * 
     * @param CustomerDTO customerDTO
     * @return CustomerDTO Customer instance converted to CustomerDTO
     * @throws Exception
     */
	@POST
	@Path("/registerWithoutResponse")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Deprecated
	public CustomerDTO registerCustomerWithoutResponse(CustomerDTO customerDTO) throws Exception {
		System.out.println("Customer Resource registerCustomer called");
		Customer customer = toEntity(customerDTO);
		Customer createdCustomer = customerService.registerCustomer(customer);
		//Convert to DTO and return
		CustomerDTO createdCustomerDTO = toDTO(createdCustomer);
		System.out.println("Customer Resource registerCustomer will return the customer dto: " + createdCustomerDTO);
		return createdCustomerDTO;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CustomerDTO> fetchAllCustomers() {
		System.out.println("Customer Resource called");
		List<Customer> customers = customerService.fetchAllCustomers();
		System.out.println("Customer Resource returning cusomers with size: " + customers.size());
		//System.out.println("Customer: " + customers.get(0));
		
		//Convert to DTO and return
		List<CustomerDTO> list = customers.stream().map(cust -> toDTO(cust)).collect(Collectors.toList());
		return list;
	}
	
	@Path("/allCustomersPaginated")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CustomerDTO> fetchAllCustomersWithPagination(@QueryParam("page") int page) {
		//Not start and year will be defaulted to 0 if you don't pass these query parameters while invoking the endpoint. 
		//We will make use of this fact to fetch the customers
		System.out.println("Customer Resource fetchAllCustomersWithPagination called with page: " + page);
		List<Customer> customers = customerService.fetchAllCustomersPaginated(page);
		
		//convert the list to list of customerDTO and return the response
		List<CustomerDTO> list = customers.stream().map(cust -> toDTO(cust)).collect(Collectors.toList());
		return list;
	}
	
	@POST
	@Path("/customer")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CustomerDTO createCustomer(CustomerDTO customerDTO) throws Exception {
		System.out.println("Customer Resource createCustomer called");
		Customer customer = toEntity(customerDTO);
		Customer createdCustomer = customerService.createCustomer(customer);
		//Convert to DTO and return
		CustomerDTO createdCustomerDTO = toDTO(createdCustomer);
		System.out.println("Customer Resource createCustomer will return the customer dto: " + createdCustomerDTO);
		return createdCustomerDTO;
	}
	
	//utility method to convert the customer entity to customer dto
	private CustomerDTO toDTO(Customer customer) {
		//System.out.println("Converting entity to dto in CustomerResource");
		CustomerDTO dto = CustomerMapper.INSTANCE.toDto(customer);
		System.out.println("Converted dto instance is: " + dto);
		return dto;
	}
	
	//utility method to convert the customer entity to customer dto
	private Customer toEntity(CustomerDTO customerDTO) {
		//System.out.println("Converting dto to entity in CustomerResource");
		Customer customer = CustomerMapper.INSTANCE.toEntity(customerDTO);
		System.out.println("Converted entity instance is: " + customer);
		return customer;
	}
	
	/**
     * Searches a customer using emailAddress. If the customer exists, just return the customer data else I don't know now
     * 
     * @param String emailAddress
     * @return CustomerDTO Customer instance converted to CustomerDTO
     * @throws Nothing
     */
	@GET
	@Path("/customer_deprecated")
	@Produces(MediaType.APPLICATION_JSON)
	@Deprecated
	public CustomerDTO fetchCustomerByEmail(@QueryParam("emailAddress") String emailAddress) {
		CustomerDTO customerDTO = null;
		System.out.println("CustomerResource fetchCustomerByEmail() was called with query param '" + emailAddress + "'");
		Customer customer = customerService.fetchCustomerByEmail(emailAddress);
		if(customer != null) {
			customerDTO = toDTO(customer);
		} else {
			System.out.println("Received null customer in result of CustomerResource fetchCustomerByEmail()");
		}
		return customerDTO;
	}
	
	/**
     * Searches a customer using emailAddress. If the customer exists, just return the customer data else I don't know now
     * 
     * @param String emailAddress
     * @return CustomerDTO Customer instance converted to CustomerDTO
     * @throws Nothing
     */
	@GET
	@Path("/customer")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchCustomerByEmailReturnResponse(@QueryParam("emailAddress") String emailAddress) {
		Response response = null;
		CustomerDTO customerDTO = null;
		System.out.println("CustomerResource fetchCustomerByEmail() was called with query param '" + emailAddress + "'");
		Customer customer = customerService.fetchCustomerByEmail(emailAddress);
		if(customer != null) {
			customerDTO = toDTO(customer);
			response = Response
					.status(Status.OK)
					.header("PoweredBy", "X-SAFE Tech")
					.entity(customerDTO)
					.build();
		} else {
			System.out.println("Received null customer in result of CustomerResource fetchCustomerByEmail()");
			throw new DataNotFoundException("Customer with email " + emailAddress + " not found");
		}
		return response;
	}
	
	/**
     * Updates a customer using. If the customer exists, just return the updated customer data else I don't know now
     * Working fine from Client code and Postman
     * 
     * @param CustomerDTO customerDTO
     * @return CustomerDTO Customer instance converted to CustomerDTO
     * @throws Nothing
     */
	@PUT
	@Path("/customer")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
		System.out.println("Customer Resource updateCustomer called");
		CustomerDTO updatedCustomerDTO = null;
		Customer customer = toEntity(customerDTO);
		Customer updatedCustomer = customerService.updateCustomer(customer);
		if(customer != null) {
			updatedCustomerDTO = toDTO(updatedCustomer);
		} else {
			System.out.println("Customer could not be updated due to null was received from Service layer updateCustomer method");
		}
		return updatedCustomerDTO;
	}
	
	/**
     * De-register customer using path parameter. If the customer exists, remove the customer from DB, if it does not exist then I don't know
     * Working fine from Client code and Postman
     * 
     * @param PathParam emailAddress
     * @return Nothing
     * @throws Nothing
     */
	@DELETE
	@Path("/customer/{emailAddress}")
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerDTO deregisterCustomer(@PathParam("emailAddress") String emailAddress) {
		System.out.println("Customer Resource deregisterCustomer called");
		CustomerDTO removedCustomerDTO = null;
		Customer deregisterCustomer = customerService.deregisterCustomer(emailAddress);
		if(deregisterCustomer != null) {
			removedCustomerDTO = toDTO(deregisterCustomer);
		}
		return removedCustomerDTO;
	}
}
