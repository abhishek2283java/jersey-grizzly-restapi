package org.abhishek.customerapi.service;

import java.util.List;

import org.abhishek.customerapi.model.Customer;

public interface CustomerService {
	
	List<Customer> fetchAllCustomers();
	Customer fetchCustomerByEmail(String emailAddress);
	Customer createCustomer(Customer customer) throws Exception;
	Customer updateCustomer(String emailAddress, String phoneNumber);
	Customer registerCustomer(Customer customer) throws Exception;
	Customer updateCustomer(Customer customer);
	Customer deregisterCustomer(String emailAddress);
	List<Customer> fetchAllCustomersPaginated(int page);

}
