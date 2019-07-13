/*
 Service which handles the connection to database and executes the SQL-queries.
 When called, it'll contact Entity-class (Customer.java) and then executes needed
 queries. After the execution it'll forwards needed information back to controller.
*/

package jpajsf;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
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
   * Fetches all available customers from the database via @NamedQuery. If it doesn's 
   * find any it'll return an empty Customer -list.
   * @return all the found customers
   */
  public List<Customer> getCustomers() {
    List<Customer> customers = this.em.createNamedQuery("Customer.findAll").getResultList();
    return customers;
  }
  
  /**
   * Creates a new customer -row into the database. Needed values will come via parameter.
   * If the addition of the customer is successful, it'll return a true value, otherwise false.
   * @param c - Customer -object containing customer's information
   * @return boolean value representing of query was successful/unsuccessful
   */
  public boolean createCustomer(Customer c) {
    try {
      this.em.persist(c);
      return true;
    } catch (Exception e) {
      System.out.println(e);
      return false;
    }
  }
  
}
