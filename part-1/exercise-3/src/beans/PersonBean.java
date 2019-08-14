package beans;

import java.io.Serializable;

/**
 * @author samuli
 *
 */
public class PersonBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// Private variables for the Bean-class
	private String name;
	private String address;
	private String city;
	private String email;
	
	
	/* CONSTRUCTORS */
	
	public PersonBean() {}
	
	public PersonBean(String name, String address, String city, String email) {
    this.name = name;
    this.address = address;
    this.city = city;
    this.email = email;
  }
	
	
  // Getters for all the attributes
	// Returns wanted attribute by calling method name
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	public String getCity() {
		return city;
	}
	public String getEmail() {
		return email;
	}
	
	// Setters for all the attributes
	// Changes wanted attribute's value by method name
	public void setName(String pName) {
	  this.name = pName;
	}
	public void setAddress(String pAddress) {
	  this.address = pAddress;
	}
	public void setCity(String pCity) {
	  this.city = pCity;
	}
	public void setEmail(String pEmail) {
	  this.email = pEmail;
	}
}
