/*
 This is a @Named bean which acts as a controller between view (index.xhtml) and
 service (CustomerService.java). On it's own it doesn't have much logic.
 This forwards user inputs from the view to controller which'll execute needed
 queries. After the respond has arrived, those values will then be forwarded back to
 view via controller.
*/

package jpajsf;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named(value = "customerController")
@RequestScoped
public class CustomerController implements Serializable {

  private static final long serialVersionUID = 1L;
  
  // Inject service into this controller (bean)
  @EJB CustomerService cs;
  // Status message for adding new customers
  private String status;
  // Class variable -object which'll contain customer(s)
  private Customer customer;
  
  /**
   * Forwards an execution to fetch all the available customers to
   * CustomerService. Receives those customers from the service 
   * and returns them to the view.
   * @return all customers
   */
  public List<Customer> getCustomers() {
    List<Customer> customers = this.cs.getCustomers();
    return customers;
  }
  
  /**
   * Forwards an execution to create a new customer to CustomerService.
   * Receives the success of the query by boolean value. A status message
   * will be sent to view according to the succession of the query.
   * @return status-message telling if new customer was added
   */
  public String createCustomer() {
    if (this.cs.createCustomer(customer)) {
      customer = new Customer();
      status = "Lisätty";
    } else {
      status = "Käyttäjää ei voitu lisätä";
    }
    return status;
  }
  
  
  public CustomerController() {
    this.customer = new Customer();
  }
  
  public void setCustomer(Customer c) {
    this.customer = c;
  }
  
  public Customer getCustomer() {
    return customer;
  }
  
  public String getStatus() {
    return status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  
}
