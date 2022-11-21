package org.abhishek.customerapi.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.abhishek.customerapi.model.Customer;

public interface CustomerService {
	
	List<Customer> fetchAllCustomers();
	
	Optional<Customer> fetchCustomerByEmail(String emailAddress);
	
	Customer createCustomer(Customer customer) throws Exception;
	
	Customer updateCustomer(String emailAddress, String phoneNumber);
	
	Customer registerCustomer(Customer customer) throws Exception;
	
	Optional<Customer> updateCustomer(Customer customer);
	
	Optional<Customer> deregisterCustomer(String emailAddress);
	
	List<Customer> fetchAllCustomersPaginated(int page);

	List<Customer> fetchCustomersByDateOfBirth(Date dateOfBirthAsDate);

}
