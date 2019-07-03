/*
 * DbBean on papu jossa tehdään tietokantaoperaatiot
 * Yleensä JSF-sovelluksissa käytetään kannan käsittelyyn 
 * JPA:ta, mutta tässä on homma tehty tavallisella JDBC:llä.
 * 
 * Tämä papu toimii yhdessä userBean -pavun kanssa. Niiden välillä
 * on riippuvuus ja tämä papu on liitetty @inject -annotaatiolla (CDI)
 * userBeaniin
 * 
 *Sovellus on laitettu toimimaan ilman tietokantaa mutta jos haluat 
 *testata tiedon hakua kannasta, tarvitset seuraavanlaisen taulun:
 * 

 CREATE TABLE `users` (
 `username` varchar(20) NOT NULL ,
 `password` varchar(20) NOT NULL ,
 `ID` int(11) NOT NULL auto_increment,
 PRIMARY KEY  (`ID`)
 ) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=0;

 * 
 */
package beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.Statement;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/* @Dependent (riippuvainen) tarkoittaa että beanilla ei ole varsinaista scopea, vaan
DbBean on käytössä silloin kun toinen bean tarvitsee sitä */
@Named(value = "dbBean")
@Dependent
public class DbBean implements Serializable {

  private static final long serialVersionUID = 1L;
  private String message;

  // alustetaan viesti tyhjäksi ettei tule sen puuttumisesta virheilmoitusta
  public DbBean() {
    this.message = "";
  }

  /**
   * Creates a connection to database called jeedb2 using Payara's Connection 
   * Pool. After the connection has been established, it'll be returned to 
   * function who called it.
   * 
   * @return conn - Connection to MySQL
   */
  public Connection Connect() {

    Connection conn;
    
    try {
      Context ctx = new InitialContext();
      DataSource ds = (DataSource) ctx.lookup("jdbc/mysql_2_3");
      conn = ds.getConnection();
    } catch (Exception ex) {
      System.out.println(ex);
      return null;
    }
      return conn;
  }

  /**
   * Generates a message telling if wanted username is being taken already
   * 
   * @param username which'll be checked
   * @return message telling if username is taken already
   */
  public String getMessage(String value) {

    try {
      Connection conn = Connect();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username='" + value + "';");
      
      int rivilkm = 0;
      while (rs.next()) {
        rivilkm++;
      }
      
      if (rivilkm == 1) {
        message = "On jo käytössä. Valitse toinen käyttäjätunnus";
      } else {
        message = "Käytettävissä";
      }
      return message;

    } catch (Exception ex) {
      System.out.println("Tuli poikkeus" + ex);
      return null;
    }
  }
  
  /**
   * Creates new user (=credentials)
   * 
   * After connecting to MySQL, function will execute a new query which'll
   * try to insert new credentials into the table. Message will generated too
   * telling if insertion was successful or not.
   * 
   * @param username New username
   * @param password New password
   * @return message telling if insertion was successful or not
   */
  public String createCredentials(String username, String password) {
    try {
      Connection conn = Connect();
      Statement stmt = conn.createStatement();
      stmt.execute("INSERT INTO `users` (`username`, `password`)"
          + "VALUES ('" + username + "','" + password + "');");
      return "Tunnukset luotu";
    } catch (Exception ex) {
      System.out.println(ex);
      return "Tunnareita ei voitu luoda" + ex;
    }
  }

}