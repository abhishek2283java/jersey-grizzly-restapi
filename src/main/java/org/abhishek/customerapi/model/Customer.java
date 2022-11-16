package org.abhishek.customerapi.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "customer")
public class Customer {
	@Id
	private String emailAddress;
	private String customerName;
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestampCreated = new Date((((new Date()).getTime())/1000)*1000);
	private boolean active = true;
	private String phoneNumber;
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Date getTimestampCreated() {
		return timestampCreated;
	}
	public void setTimestampCreated(Date timestampCreated) {
		this.timestampCreated = timestampCreated;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	@Override
	public String toString() {
		return "Customer [emailAddress=" + emailAddress + ", customerName=" + customerName + ", timestampCreated="
				+ timestampCreated + ", active=" + active + ", phoneNumber=" + phoneNumber + ", dateOfBirth="
				+ dateOfBirth + "]";
	}
	

}
