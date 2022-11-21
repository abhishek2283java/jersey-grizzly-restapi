package org.abhishek.customerapi.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.abhishek.customerapi.exception.CustomerAPIGenericException;
import org.abhishek.customerapi.exception.DataNotFoundException;
import org.abhishek.customerapi.model.Customer;
import org.abhishek.customerapi.util.HibernateUtils;
import org.abhishek.customerapi.util.PhoneNumberGenerator;
import org.abhishek.customerapi.util.RandomDateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class CustomerDAO {
	
	private SessionFactory sessionFactory;
	
	public CustomerDAO() {
		System.out.println("CustomerDAO constructor was invoked");
		this.sessionFactory = HibernateUtils.getSessionFactory();
	}
	
	private static final int MAX_PAGINATED_RESULT_CNT = 10;

	public static void main(String[] args) throws Exception {
		CustomerDAO dao = new CustomerDAO();
//		Optional<Customer> fetchCustomerByEmail = dao.fetchCustomerByEmail("hhljk");
//		fetchCustomerByEmail.get();

//		Customer customer = new Customer();
//		customer.setEmailAddress("abc001@gmail.com");
//		customer.setCustomerName("Customer 1");
//		customer.setPhoneNumber("0046739182144");
//		customer.setDateOfBirth(new Date());
//		
//		Customer saveCustomer = dao.saveCustomer(customer);
//		System.out.println(saveCustomer);

//		Customer fetchCustomerByEmail = dao.fetchCustomerByEmail("abc008@gmail.com");
//		System.out.println("Received customer is: " + fetchCustomerByEmail);

//		String emailAddress = "abc001client@gmail.com";
//		Customer removeCustomer = dao.removeCustomer(emailAddress);
//		System.out.println("RemovedCustomer: " + removeCustomer);
		
		List<Customer> createdCustomers = dao.createMultipleCustomers(100); 
		createdCustomers.forEach(System.out::println);
	}

	private List<Customer> createMultipleCustomers(int i) throws Exception {
		List<Customer> customerObjects = createCustomerObjects(i);
		List<Customer> insertedCustomers = new ArrayList<>();
		for(Customer customer : customerObjects) {
			Customer saveCustomer = saveCustomer(customer);
			insertedCustomers.add(saveCustomer);
		}
		return insertedCustomers;
	}
	
	private List<Customer> createCustomerObjects(int numberOfCustObjects) throws ParseException {
		List<Customer> customerObjects = new ArrayList<>();
		RandomDateUtil dateUtil = new RandomDateUtil();
		DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
		PhoneNumberGenerator phoneGenerator = new PhoneNumberGenerator();
		for(int i = 1; i <= numberOfCustObjects; i++) {
			Customer customer = new Customer();
			customer.setCustomerName("Test Customer " + i);
			customer.setEmailAddress("test_" + i + "_@gmail.com");
			customer.setDateOfBirth(dtf.parse(dateUtil.getSingleRandomDateStringBetween(1981, 2000)));
			customer.setPhoneNumber("0046" + String.valueOf(phoneGenerator.generatePhoneNumberStartingWith(7)));
			customerObjects.add(customer);
		}
		return customerObjects;
	}

	@SuppressWarnings("unused")
	@Deprecated
	private List<Customer> getDefaultListOfCustomers() {
		Customer customer = new Customer();
		customer.setEmailAddress("abc001@gmail.com");
		customer.setCustomerName("Customer 2");
		customer.setPhoneNumber("0046739182144");

		List<Customer> customers = new ArrayList<>();
		customers.add(customer);

		return customers;
	}

	public Customer saveCustomer(Customer customer){
		System.out.println("Customer DAO saveCustomer called");
		Customer savedCustomer = null;
		SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			session.save(customer);
			session.getTransaction().commit();
			savedCustomer = session.get(Customer.class, customer.getEmailAddress());
		}catch(ExceptionInInitializerError e) {
			//This is an error and not exception from the static block of HibernateUtil class.
			System.out.println("Error duing initializing the SessionFactory " + e.getMessage());
			throw new CustomerAPIGenericException("Error while accessing database");
		}
		return savedCustomer;
	}
	
	/**
     * Fetches customer using emailAddress. If the customer exists, just return the customer data else return ??
     * 
     * @param String emailAddress
     * @return Customer Customer instance
     * @throws Nothing
     */
	public Optional<Customer> fetchCustomerByEmail(String emailAddress) {
		System.out.println("CustomerDAO fetchCustomerByEmail() was called for emailAddress: '" + emailAddress + "'");
		Customer customerFromDB = null;
		//try (Session session = HibernateUtils.getSessionFactory().openSession()) {
		try (Session session = sessionFactory.openSession()) {
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

			CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
			Root<Customer> root = criteriaQuery.from(Customer.class);
			criteriaQuery.select(root);

			criteriaQuery.where(criteriaBuilder.equal(root.get("emailAddress"), emailAddress));

			Query<Customer> query = session.createQuery(criteriaQuery);
			List<Customer> resultList = query.getResultList();
			if (resultList.size() == 1) {
				System.out.println("Customer DAO fetchCustomerByEmail found single result for emailAddress");
				customerFromDB = resultList.get(0);
			} else if (resultList.size() > 1) {
				System.out.println("Customer DAO fetchCustomerByEmail found multiple results for emailAddress");
				throw new RuntimeException("Multiple hits found for the emailAddress " + emailAddress);
			}
		}catch(ExceptionInInitializerError e) {
			//This is an error and not exception from the static block of HibernateUtil class.
			System.out.println("Error duing initializing the SessionFactory " + e.getMessage());
			throw new CustomerAPIGenericException("Error while accessing database");
		}
		return Optional.ofNullable(customerFromDB);
	}

//	public Customer fetchCustomerByEmail(String emailAddress) {
//		List<Customer> customers = getDefaultListOfCustomers();
//		//return customers.stream().filter(c -> c.getEmailAddress(emailAddress)).findAny();
//		return customers.get(0);
//	}

	public List<Customer> fetchAll() {
		List<Customer> allCustomers = new ArrayList<>();
		// SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
		try (Session session = HibernateUtils.getSessionFactory().openSession()) {
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
			Root<Customer> root = criteriaQuery.from(Customer.class);
			criteriaQuery.select(root);
			Query<Customer> createQuery = session.createQuery(criteriaQuery);
			allCustomers = createQuery.list();
		}
		return allCustomers;
	}

	// update phone number
	@Deprecated
	public Customer updateCustomer(String emailAddress, String phoneNumber) {
		System.out.println("Customer DAO updateCustomer called with email and phone");
		Customer updatedCustomer = null;
		try (Session session = HibernateUtils.getSessionFactory().openSession()) {
			Customer customerFromDb = session.get(Customer.class, emailAddress);
			customerFromDb.setPhoneNumber(phoneNumber);
			session.beginTransaction();
			session.save(customerFromDb);
			session.getTransaction().commit();
			updatedCustomer = session.get(Customer.class, emailAddress);
		}
		return updatedCustomer;
	}

	public Optional<Customer> updateCustomer(Customer customer) {
		System.out.println("Customer DAO updateCustomer called with customer instance");
		Customer updatedCustomer = null;
		try (Session session = HibernateUtils.getSessionFactory().openSession()) {
			session.beginTransaction();
			Customer customerFromDb = fetchCustomerByEmail(customer.getEmailAddress()).orElseThrow(() -> 
				{ System.out.println("Customer DAO updateCustomer could not find customer for email: '" + customer.getEmailAddress() + "'");
					return new DataNotFoundException("Customer with email " + customer.getEmailAddress() + " not found");
				});
			
			customerFromDb.setDateOfBirth(customer.getDateOfBirth() != null ? customer.getDateOfBirth() : customerFromDb.getDateOfBirth());
			customerFromDb.setPhoneNumber(customer.getPhoneNumber() != null ? customer.getPhoneNumber() : customerFromDb.getPhoneNumber());
			// System.out.println("Customer to be updated is: " + customer);
			session.update(customerFromDb);
			session.getTransaction().commit();
			System.out.println("Re-querying the updated customer using emailAddress from dao method");
			updatedCustomer = session.get(Customer.class, customer.getEmailAddress());
		}catch(ExceptionInInitializerError e) {
			//This is an error and not exception from the static block of HibernateUtil class.
			System.out.println("Error duing initializing the SessionFactory " + e.getMessage());
			throw new CustomerAPIGenericException("Error while accessing database");
		} 
//		catch(HibernateException e) {
//			System.out.println("Exception in DAO updateCustomer: " + e.getMessage());
//		}
//		if (updatedCustomer == null) {
//			System.out.println("Customer could not be updated by DAO method updateCustomer(), hence what was passed in will be sent back");
//			updatedCustomer = customer;
//		} else {
//			System.out.println("Customer was updated by DAO method. Sending back the updated customer instance");
//		}
		return Optional.ofNullable(updatedCustomer);
	}

	public Optional<Customer> removeCustomer(String emailAddress) {
		System.out.println("Customer DAO removeCustomer called with emailAddress: '" + emailAddress + "'");
		Customer customerFromDb = null;
//		Customer customerFromDb = new Customer();
//		customerFromDb.setEmailAddress(emailAddress);
		try (Session session = HibernateUtils.getSessionFactory().openSession()) {
			session.beginTransaction();
			customerFromDb = session.load(Customer.class, emailAddress);
			session.delete(customerFromDb);
			session.getTransaction().commit();
		} catch (EntityNotFoundException e) {
			System.out.println("Exception in DAO class remove customer: " + e.getMessage());
			throw new DataNotFoundException("Customer with email " + emailAddress + " not found");
		}
		System.out.println("About to return from DAO");
		return Optional.ofNullable(customerFromDb);
	}

	public List<Customer> fetchAllCustomersPaginated(int page) {
		List<Customer> resultList = null;
		try (Session session = HibernateUtils.getSessionFactory().openSession()) {
			// 1. Get criteria builder
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

			CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class); // we expect Customer class instance in result
			
			Root<Customer> root = criteriaQuery.from(Customer.class);
			criteriaQuery.select(root);

			int startRowIndex = 0;
			if(page > 0) {
				startRowIndex = ((page-1) * MAX_PAGINATED_RESULT_CNT);
			} 
			System.out.println("startRowIndex: " + startRowIndex);
			resultList = session.createQuery(criteriaQuery)
								.setFirstResult(startRowIndex)
								.setMaxResults(MAX_PAGINATED_RESULT_CNT)
								.getResultList();
		}
		return resultList;
	}

	public List<Customer> fetchCustomersByDateOfBirth(Date dateOfBirthAsDate) {
		System.out.println("Customer DAO fetchCustomersByDateOfBirth called with dateOfBirth: '" + dateOfBirthAsDate + "'");
		//List<Customer> resultList = null;
		try (Session session = HibernateUtils.getSessionFactory().openSession()) {
			// 1. Get criteria builder
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

			CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class); // we expect Customer class instance in result
						
			Root<Customer> root = criteriaQuery.from(Customer.class);
			criteriaQuery.select(root);
			criteriaQuery.where(criteriaBuilder.greaterThan(root.get("dateOfBirth"), dateOfBirthAsDate));
			List<Customer> resultList = session.createQuery(criteriaQuery).getResultList();
			return resultList;
		} catch(ExceptionInInitializerError e) {
			System.out.println("Error happened in getting session/session factory. Report error " + e.getMessage());
			throw new CustomerAPIGenericException("Error while accessing database. Report error.");
		}
	}
}
