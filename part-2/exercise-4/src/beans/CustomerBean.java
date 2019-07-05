package beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

// Named session-bean
@Named(value = "customerBean")
@SessionScoped
public class CustomerBean implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  // Inject database-bean to this bean
  @Inject private DbBean dbBean;
  
  private int id;
  private String nimi;
  private String osoite;
  private String puhelin;
  private String email;
  private String salasana;
  private String status;
  // Includes all the customers found from the table  
  private ArrayList<CustomerBean> customers;
  

  /**
   * Function which'll gather all the customers from 
   * database's table. External bean will be used for the
   * MySQL -queries. After the query is ready, it'll return 
   * all of the customers inside ArrayList. This list will be 
   * placed inside bean's variable - "customers".
   * 
   * @return customers - all of the customers found from table
   */
  public ArrayList<CustomerBean> getCustomers() {
    status = null;
    customers = dbBean.getCustomers();
    return customers;
  }
  
  
  /**
   * Function which'll add new customer to the table. Function will
   * call an external bean to handle the insertion SQL-query. After the 
   * query is done in another bean, it'll return a status-message which
   * will tell if query was successful or not. 
   * 
   * If query is successfull (status == "Lisätty), we'll empty all the inputs
   * in the facelet.
   * 
   * @return status - status-message describing if SQL-query was successfull or not
   */
  public String addCustomer() {
    status = dbBean.addCustomer(nimi, osoite, puhelin, email, salasana);
    if (status == "Lisätty") {
      nimi = "";
      osoite = "";
      puhelin = "";
      email = "";
      salasana = "";
    }
    return status;
  }
  
  
  // GETTERS AND SETTERS FOR ALL OF THE ATTRIBUTES
  
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getNimi() {
    return nimi;
  }
  public void setNimi(String nimi) {
    this.nimi = nimi;
  }
  public String getOsoite() {
    return osoite;
  }
  public void setOsoite(String osoite) {
    this.osoite = osoite;
  }
  public String getPuhelin() {
    return puhelin;
  }
  public void setPuhelin(String puhelin) {
    this.puhelin = puhelin;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getSalasana() {
    return salasana;
  }
  public void setSalasana(String salasana) {
    this.salasana = salasana;
  }
  
  
}
