/*
 Service which handles the connection to database and executes the SQL-queries.
 When called, it'll contact Entity-class (Customer.java) and then executes needed
 queries. After the execution it'll forwards needed information back to Customer-API.
*/

package jpa_rest_api;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


// This service is @Stateless so the instance of it will be destroyed after every call.
@Stateless
// This service is also @LocalBean, so it has no external interface -view
@LocalBean
@SuppressWarnings("unchecked")
public class CustomerService {
  
  // Service (bean) belongs to JPA-context
  @PersistenceContext
  EntityManager em;
  
  
  /**
   * Fetches all available customers from the database via @NamedQuery. If it does not
   * find any it'll return an empty Customer -list.
   * @return all the found customers
   */
  public List<Customer> getCustomers() {
    List<Customer> customers = this.em.createNamedQuery("Customer.findAll").getResultList();
    return customers;
  }
  
  
  /**
   * Get one customer by it's ID. It'll return the customer -object if it founds one.
   * If none is found it'll return a null -object.
   * @param id - Customer's ID
   * @return Customer -object
   */
  public Customer getCustomer(String id) {
    Customer customer = em.find(Customer.class, Integer.parseInt(id));
    return customer;
  }
  
  
  /**
   * Delete one customer by it's ID. It'll return a true if deletion is
   * successful. Boolean value false will be returned if user cannot be 
   * found by ID-parameter.
   * @param id - Customer's ID
   * @return boolean value telling if deletion was successful or not
   */
  public boolean deleteCustomer(String id) {
    try {
      em.remove(em.find(Customer.class, Integer.parseInt(id)));
      return true;
    } catch(Exception e) {
      System.out.println(e);
      return false;
    }
  }
  
  
  /**
   * Creates a new customer -row into the database. Needed values will come via parameter.
   * If the addition of the customer is successful, it'll return a true value, otherwise false.
   * @param c - Customer -object containing customer's information
   * @return NULL if user was created successfully; error-message if creation was unsuccessful
   */
  public String createCustomer(Customer c) {
    // If needed values weren't sent
    if (c.getEmail() == null || c.getNimi() == null || c.getOsoite() == null 
        || c.getPuhelin() == null || c.getSalasana() == null) {
      return "Et antanut kaikkia asiakkaan tietoja";
    }
    try {
      em.persist(c);
      // If creation was successful
      return null;
    }
    // If posted user already exists
    catch (EntityExistsException eee) {
      System.out.println(eee);
      return "Asiakas on jo olemassa";
    }
    // If values weren't given in right type
    catch (IllegalArgumentException iae) {
      System.out.println(iae);
      return "Et antanut asiakkaan tietoja oikeassa muodossa";
    }
    // General error
    catch (Exception e) {
      System.out.println(e);
      return "Virhe asiakasta luodessa";
    }
  }
  
}
