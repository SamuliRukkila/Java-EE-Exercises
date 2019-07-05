
package beans;

import java.io.Serializable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


@Named(value = "dbBean")
@Dependent
public class DbBean implements Serializable {

  private static final long serialVersionUID = 1L;
  
  /**
   * Creates a connection to database called jeedb1 using Payara's Connection 
   * Pool. After the connection has been established, it'll be returned to 
   * function who called it.
   * 
   * @return conn - Connection to MySQL
   */
  public Connection connect() {
    
    Connection conn;
    
    try {
      Context ctx = new InitialContext();
      DataSource ds = (DataSource) ctx.lookup("jdbc/mysql_2_4");
      conn = ds.getConnection();
    } catch(Exception ex) {
      System.out.println(ex);
      return null;
    }
    return conn;
  }
  
  
  /**
   * Fetches all customers from the table using a SQL-query. These 
   * customers will be placed inside an ArrayList through a while -loop. This
   * ArrayList will be same typed as a CustomerBean -bean.
   * 
   * After all of the customers have been fetched they'll be returned to the
   * caller -bean.
   * 
   * @return customers - an ArrayList of the customers
   */
  public ArrayList<CustomerBean> getCustomers() {
    
    ResultSet rs = null;
    PreparedStatement pst = null;
    Connection conn = connect();
    
    String stm = "SELECT * FROM `asiakkaat`;";
    ArrayList<CustomerBean> customers = new ArrayList<CustomerBean>();
    
    try {
      pst = conn.prepareStatement(stm);
      pst.execute();
      rs = pst.getResultSet();
      
      while(rs.next()) {
        CustomerBean customer = new CustomerBean();
        customer.setId(rs.getInt(1));
        customer.setNimi(rs.getString(2));
        customer.setOsoite(rs.getString(3));
        customer.setPuhelin(rs.getString(4));
        customer.setEmail(rs.getString(5));
        customer.setSalasana(rs.getString(6));
        customers.add(customer);
      }
      conn.close();
    } catch(SQLException e) {
      e.printStackTrace();
    }
    return customers;
  }

  
  /**
   * Adds a new customer to table. Needed values will
   * come from parameters. They'll be placed inside a 
   * string which'll act as a query. After the query is 
   * ready, it'll be executed. If the execution succeeds,
   * an informative status-message will be returned to the 
   * calling bean ("Lisätty"). If the insert-query is unsuccessful,
   * a different status-message will be returned.
   * 
   * @param nimi - Customer's name
   * @param osoite - Customer's address
   * @param puhelin - Customer's phonenumber
   * @param email - Customer's email
   * @param salasana - Customer's password
   * 
   * @return Status message telling if SQL-query was successful or not
   */
  public String addCustomer(String nimi, String osoite, 
      String puhelin, String email, String salasana) {
    
    PreparedStatement pst = null;
    Connection conn = connect();
    String stm = "INSERT INTO `asiakkaat` "
        + "(`nimi`, `osoite`, `puhelin`, `email`, `salasana`) "
        + "VALUES ( '"+ nimi +"','" + osoite +"','" + puhelin 
        + "','" + email + "','" + salasana + "');";
    
    try {
      pst = conn.prepareStatement(stm);
      pst.executeUpdate();
      conn.close();
    } catch (SQLException e) {
      System.out.println(e);
      return "Virhe! Käyttäjää ei voitu lisätä";
    }
    return "Lisätty";
    
  }
  
  
}
