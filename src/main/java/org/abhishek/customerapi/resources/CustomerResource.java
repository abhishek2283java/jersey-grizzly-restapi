package org.abhishek.customerapi.resources;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.abhishek.customerapi.dto.CustomerDTO;
import org.abhishek.customerapi.exception.CustomerAPIInvalidInputException;
import org.abhishek.customerapi.exception.CustomerNotUpdatedException;
import org.abhishek.customerapi.exception.DataNotFoundException;
import org.abhishek.customerapi.model.Customer;
import org.abhishek.customerapi.service.CustomerService;
import org.abhishek.customerapi.util.CustomerMapper;
import org.hibernate.internal.build.AllowSysOut;

@Path("customer-api/v2/customer")
public class CustomerResource {

	// private CustomerServiceImpl customerService = new CustomerServiceImpl();

	@Inject
	private CustomerService customerService;
	
	public CustomerResource() {
		System.out.println("CustomerResource constructor invoked");
	}

	/**
	 * Registers a customer. If the customer exists, just return the customer data
	 * else create and return
	 * 
	 * @param CustomerDTO customerDTO
	 * @return CustomerDTO Customer instance converted to CustomerDTO
	 * @throws Exception
	 */
	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerCustomer(CustomerDTO customerDTO, @Context UriInfo uriInfo) throws Exception {
		System.out.println("Customer Resource registerCustomer called");
		Customer customer = toEntity(customerDTO);
		Customer createdCustomer = customerService.registerCustomer(customer);
		// Convert to DTO and return
		CustomerDTO createdCustomerDTO = toDTO(createdCustomer);
		System.out.println("Customer Resource registerCustomer will return the customer dto: " + createdCustomerDTO);
		//return Response.status(Status.CREATED).header("PoweredBy", "ABC-Tech").entity(createdCustomerDTO).build();
		
		//create the location of the created entity and return it in the response
		//URI createdUri = uriInfo.getAbsolutePathBuilder().path(createdCustomerDTO.getEmailAddress()).build();
		//http://localhost:8080/customer-api/v2/register/ab@test.com but this is not the location
		
		//URI createdUri = uriInfo.getBaseUriBuilder().path(CustomerResource.class).path(createdCustomer.getEmailAddress()).build();
		//http://localhost:8080/customer-api/v2/kirtishree@ema.com
		
		//System.out.println(uriInfo.getBaseUriBuilder().toString());
		URI createdUri = uriInfo.getBaseUriBuilder()
			.path(CustomerResource.class)
			.path(CustomerResource.class, "fetchCustomerByEmailReturnResponse")
			.queryParam("emailAddress", createdCustomerDTO.getEmailAddress()).build();
		
		System.out.println("createdUri:" + createdUri);
		
		
		//URI createdUri = uriInfo.getBaseUriBuilder().path(CustomerResource.class, "fetchCustomerByEmailReturnResponse")
		//	.queryParam("emailAddress", createdCustomerDTO.getEmailAddress()).build();
		//path(createdCustomer.getEmailAddress())
		
		return Response.created(createdUri).header("PoweredBy", "ABC-Tech").entity(createdCustomerDTO).build();
		
	}

	/**
	 * Registers a customer. If the customer exists, just return the customer data
	 * else create and return
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
		// Convert to DTO and return
		CustomerDTO createdCustomerDTO = toDTO(createdCustomer);
		System.out.println("Customer Resource registerCustomer will return the customer dto: " + createdCustomerDTO);
		return createdCustomerDTO;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Deprecated
	public List<CustomerDTO> fetchAllCustomers() {
		System.out.println("Customer Resource called");
		List<Customer> customers = customerService.fetchAllCustomers();
		System.out.println("Customer Resource returning cusomers with size: " + customers.size());
		// System.out.println("Customer: " + customers.get(0));

		// Convert to DTO and return
		List<CustomerDTO> list = customers.stream().map(cust -> toDTO(cust)).collect(Collectors.toList());
		return list;
	}

	@Path("/allCustomers")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CustomerDTO> fetchAllCustomersWithPagination(@QueryParam("page") int page) {
		// Not start and year will be defaulted to 0 if you don't pass these query
		// parameters while invoking the endpoint.
		// We will make use of this fact to fetch the customers
		System.out.println("Customer Resource fetchAllCustomersWithPagination called with page: " + page);
		List<Customer> customers = customerService.fetchAllCustomersPaginated(page);

		// convert the list to list of customerDTO and return the response
		List<CustomerDTO> list = customers.stream().map(cust -> toDTO(cust)).collect(Collectors.toList());
		return list;
	}
	
	@Path("dateOfBirthGreaterThan/{dateOfBirth}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CustomerDTO> fetchCustomersByDateOfBirth(@PathParam("dateOfBirth") String dateOfBirth) {
		System.out.println("Customer Resource fetchCustomersByDateOfBirth called with dob: " + dateOfBirth);
		List<CustomerDTO> list = new ArrayList<>();
		try {
			//Convert the String dob to Date object and if it cannot be parsed then show message/exception in response
			System.out.println("Parsing input date '" + dateOfBirth + "'");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date dateOfBirthAsDate = sdf.parse(dateOfBirth);
			
			List<Customer> customers = customerService.fetchCustomersByDateOfBirth(dateOfBirthAsDate);
			customers.stream().findAny()
			.orElseThrow(() -> {
				System.out.println("Resource method received no customers matching the date of birth: '" + dateOfBirth + "'");
				return new DataNotFoundException("No customer found matching date of birth: " + dateOfBirth);});
			
			// convert the list to list of customerDTO and return the response
			//list = customers.stream().map(cust -> toDTO(cust)).collect(Collectors.toList());
			list = customers.stream().map(this::toDTO).collect(Collectors.toList());	//using method reference
		} catch(ParseException e) {
			System.out.println("Exception in parsing the input dateOfBirth: '" + dateOfBirth + "' with error: " + e.getMessage());
			throw new CustomerAPIInvalidInputException("Unable to parse date of birth: '" + dateOfBirth + "'. Valid format is YYYY-MM-DD");
		}
		
		return list;
	}

	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CustomerDTO createCustomer(CustomerDTO customerDTO) throws Exception {
		System.out.println("Customer Resource createCustomer called");
		Customer customer = toEntity(customerDTO);
		Customer createdCustomer = customerService.createCustomer(customer);
		// Convert to DTO and return
		CustomerDTO createdCustomerDTO = toDTO(createdCustomer);
		System.out.println("Customer Resource createCustomer will return the customer dto: " + createdCustomerDTO);
		return createdCustomerDTO;
	}

	// utility method to convert the customer entity to customer dto
	private CustomerDTO toDTO(Customer customer) {
		System.out.println("Converting entity to dto in CustomerResource");
		CustomerDTO dto = CustomerMapper.INSTANCE.toDto(customer);
		System.out.println("Converted dto instance is: " + dto);
		return dto;
	}

	// utility method to convert the customer entity to customer dto
	private Customer toEntity(CustomerDTO customerDTO) {
		// System.out.println("Converting dto to entity in CustomerResource");
		Customer customer = CustomerMapper.INSTANCE.toEntity(customerDTO);
		System.out.println("Converted entity instance is: " + customer);
		return customer;
	}

	/**
	 * Searches a customer using emailAddress. If the customer exists, just return
	 * the customer data else I don't know now
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
		System.out
				.println("CustomerResource fetchCustomerByEmail() was called with query param '" + emailAddress + "'");
		Customer customer = customerService.fetchCustomerByEmail(emailAddress).get();
		if (customer != null) {
			customerDTO = toDTO(customer);
		} else {
			System.out.println("Received null customer in result of CustomerResource fetchCustomerByEmail()");
		}
		return customerDTO;
	}

	/**
	 * Searches a customer using emailAddress. If the customer exists, just return
	 * the customer data else I don't know now
	 * Re-factored using Java 8 Optional
	 * 
	 * @param String emailAddress
	 * @return CustomerDTO Customer instance converted to CustomerDTO
	 * @throws Nothing
	 */
	@GET
	@Path("/getByEmail")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchCustomerByEmailReturnResponse(@QueryParam("emailAddress") String emailAddress) {
		System.out.println("CustomerResource fetchCustomerByEmail() was called with query param '" + emailAddress + "'");
		Optional<Customer> optionalCustomer = customerService.fetchCustomerByEmail(emailAddress);
		CustomerDTO customerDTO = optionalCustomer
						.map(cust -> toDTO(cust))
						.orElseThrow(() -> {
								System.out.println("Received null customer in method fetchCustomerByEmailReturnResponse() of CustomerResource");
								return new DataNotFoundException("Customer with email " + emailAddress + " not found");
							});
		return Response.status(Status.OK).header("PoweredBy", "ABC-Tech").entity(customerDTO).build();
	}

	/**
	 * Updates a customer using. If the customer exists, just return the updated
	 * customer data else I don't know now Working fine from Client code and Postman
	 * 
	 * @param CustomerDTO customerDTO
	 * @return CustomerDTO Customer instance converted to CustomerDTO
	 * @throws Nothing
	 */
	@PUT
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCustomer(CustomerDTO customerDTO) {
		System.out.println("Customer Resource updateCustomer called");
		Customer customer = toEntity(customerDTO);
		//Customer updatedCustomer = customerService.updateCustomer(customer);
		
		CustomerDTO orElseThrow = customerService.updateCustomer(customer)
		.map(cust -> toDTO(cust))
		.orElseThrow(() -> {
				System.out.println("Customer could not be updated due to null was received from Service layer updateCustomer method");
				return new CustomerNotUpdatedException("Customer with email " + customerDTO.getEmailAddress() + " could not be updated");
			});
		
		//return orElseThrow;
		return Response.status(Status.OK).header("PoweredBy", "ABC-Tech").entity(orElseThrow).build();
	}

	/**
	 * De-register customer using path parameter. If the customer exists, remove the
	 * customer from DB, if it does not exist then I don't know Working fine from
	 * Client code and Postman
	 * 
	 * @param PathParam emailAddress
	 * @return Nothing
	 * @throws Nothing
	 */
	@DELETE
	@Path("/deregister/{emailAddress}")
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerDTO deregisterCustomer(@PathParam("emailAddress") String emailAddress) {
		System.out.println("Customer Resource deregisterCustomer called");
		CustomerDTO removedCustomerDTO = customerService.deregisterCustomer(emailAddress)
		.map(c -> {
			CustomerDTO cust = toDTO(c);
			System.out.println("Customer removed is: " + cust);
			return cust;
			})
		.orElseThrow(() -> {
			System.out.println("Customer could not be updated due to null was received from Service layer updateCustomer method");
			return new CustomerNotUpdatedException("Customer with email " + emailAddress + " could not be deleted");
		});
		return removedCustomerDTO;
	}
}
