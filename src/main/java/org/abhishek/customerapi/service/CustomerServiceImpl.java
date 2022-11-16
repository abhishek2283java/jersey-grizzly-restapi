package org.abhishek.customerapi.service;

import java.util.List;

import javax.inject.Inject;

import org.abhishek.customerapi.dao.CustomerDAO;
import org.abhishek.customerapi.model.Customer;

public class CustomerServiceImpl implements CustomerService{
	
//	public CustomerService() {
//		System.out.println("Customer Service constructor was called");
//	}
	
	@Inject
	private CustomerDAO customerDAO;
	
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
     * @return Customer Customer Entity instance
     * @throws Nothing
     */
	@Override
	public Customer fetchCustomerByEmail(String emailAddress) {
		System.out.println("Customer Service Impl fetchCustomerByEmail() called for emailAddress '" + emailAddress + "'");
		Customer customer = customerDAO.fetchCustomerByEmail(emailAddress);
		return customer;
	}

	@Override
	public Customer createCustomer(Customer customer) throws Exception {
		System.out.println("Customer Service Impl createCustomer called");
		Customer savedCustomer = customerDAO.saveCustomer(customer);
		System.out.println("Customer Service Impl createCustomer returning createdCustomer: " + savedCustomer);
		return savedCustomer;
	}

	@Override
	public Customer updateCustomer(String emailAddress, String phoneNumber) {
		System.out.println("Customer Service Impl updateCustomer called");
		Customer updatedCustomer = customerDAO.updateCustomer(emailAddress, phoneNumber);
		System.out.println("Customer Service Impl updateCustomer returning createdCustomer: " + updatedCustomer);
		return updatedCustomer;
	}

	@Override
	public Customer registerCustomer(Customer customer) throws Exception {
		System.out.println("Customer Service Impl registerCustomer called");
		Customer customerFromDB = fetchCustomerByEmail(customer.getEmailAddress());
		if(customerFromDB == null) {
			customerFromDB = createCustomer(customer);
		} else {
			System.out.println("Customer with email '" + customer.getEmailAddress() + "' is already registered");
		}
		return customerFromDB;
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		System.out.println("Customer Service Impl updateCustomer called");
		Customer updatedCustomer = customerDAO.updateCustomer(customer);
		System.out.println("Customer Service Impl updateCustomer returning updateCustomer: " + updatedCustomer);
		return updatedCustomer;
	}

	@Override
	public Customer deregisterCustomer(String emailAddress) {
		System.out.println("Customer Service Impl deregisterCustomer called");
		Customer removeCustomer = customerDAO.removeCustomer(emailAddress);
		if(removeCustomer != null) {
			System.out.println("Customer Service Impl deregisterCustomer returning removedCustomer: " + removeCustomer);
		} else {
			System.out.println("Customer Service Impl deregisterCustomer returning null removedCustomer");
		}
		
		return removeCustomer;
	}

	@Override
	public List<Customer> fetchAllCustomersPaginated(int page) {
		System.out.println("Customer Service Impl fetchAllCustomersPaginated called");
		List<Customer> customers = customerDAO.fetchAllCustomersPaginated(page);
		System.out.println("Customer Service Impl fetchAllCustomersPaginated returning customers with size: " + customers.size());
		return customers;
	}

}
