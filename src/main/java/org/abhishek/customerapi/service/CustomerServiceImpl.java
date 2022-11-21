package org.abhishek.customerapi.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.abhishek.customerapi.dao.CustomerDAO;
import org.abhishek.customerapi.model.Customer;

public class CustomerServiceImpl implements CustomerService{
	
//	public CustomerService() {
//		System.out.println("Customer Service constructor was called");
//	}
	
	@Inject
	private CustomerDAO customerDAO;
	
	public CustomerServiceImpl() {
		System.out.println("CustomerServiceImpl constructor invoked");
	}
	
	@Override
	public List<Customer> fetchAllCustomers() {
		System.out.println("CustomerServiceImpl fetchAllCustomers() called");
		List<Customer> customers = customerDAO.fetchAll();
		System.out.println("CustomerServiceImpl fetchAllCustomers() received customers list with size: " + customers.size());
		return customers;
	}
	
	/**
     * Queries a customer using emailAddress. If the customer exists, just return the customer data else return null
     * 
     * @param String emailAddress
     * @return Optional<Customer> Customer Entity instance
     * @throws Nothing
     */
	@Override
	public Optional<Customer> fetchCustomerByEmail(String emailAddress) {
		System.out.println("Customer Service Impl fetchCustomerByEmail() called for emailAddress '" + emailAddress + "'");
		Optional<Customer> optionalCustomer = customerDAO.fetchCustomerByEmail(emailAddress);
		optionalCustomer.ifPresent((c) -> System.out.println("Customer Service Impl fetchCustomerByEmail found customer: " + c));
		return optionalCustomer;
	}

	@Override
	public Customer createCustomer(Customer customer){
		System.out.println("Customer Service Impl createCustomer called");
		Customer savedCustomer = customerDAO.saveCustomer(customer);
		System.out.println("Customer Service Impl createCustomer returning createdCustomer: " + savedCustomer);
		return savedCustomer;
	}

	@Override
	@Deprecated
	public Customer updateCustomer(String emailAddress, String phoneNumber) {
		System.out.println("Customer Service Impl updateCustomer called");
		Customer updatedCustomer = customerDAO.updateCustomer(emailAddress, phoneNumber);
		System.out.println("Customer Service Impl updateCustomer returning createdCustomer: " + updatedCustomer);
		return updatedCustomer;
	}

	@Override
	public Customer registerCustomer(Customer customer) {
		System.out.println("Customer Service Impl registerCustomer called");
		//Customer customerFromDB = fetchCustomerByEmail(customer.getEmailAddress()).get();	
		Customer createdCustomer = fetchCustomerByEmail(customer.getEmailAddress())
				.orElseGet(() -> createCustomer(customer));
		System.out.println("CustomerServiceImpl registerCustomer returning customer: " + createdCustomer);
		return createdCustomer;
	}

	@Override
	public Optional<Customer> updateCustomer(Customer customer) {
		System.out.println("Customer Service Impl updateCustomer called");
		Customer updatedCustomer = customerDAO.updateCustomer(customer).orElseGet(() -> null);
		System.out.println("Customer Service Impl updateCustomer returning updateCustomer: " + updatedCustomer);
		//return Optional.empty();
		return Optional.ofNullable(updatedCustomer);
	}

	@Override
	public Optional<Customer> deregisterCustomer(String emailAddress) {
		System.out.println("Customer Service Impl deregisterCustomer called");
		return customerDAO.removeCustomer(emailAddress);
	}

	@Override
	public List<Customer> fetchAllCustomersPaginated(int page) {
		System.out.println("Customer Service Impl fetchAllCustomersPaginated called");
		List<Customer> customers = customerDAO.fetchAllCustomersPaginated(page);
		System.out.println("Customer Service Impl fetchAllCustomersPaginated returning customers with size: " + customers.size());
		return customers;
	}

	@Override
	public List<Customer> fetchCustomersByDateOfBirth(Date dateOfBirthAsDate) {
		System.out.println("Customer Service Impl fetchCustomersByDateOfBirth called");
		List<Customer> customers = customerDAO.fetchCustomersByDateOfBirth(dateOfBirthAsDate);
		System.out.println("Customer Service Impl fetchCustomersByDateOfBirth returning customers with size: " + customers.size());
		return customers;
	}

}
