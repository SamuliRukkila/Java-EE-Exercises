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
import java.sql.DriverManager;
//import java.sql.ResultSet;
import java.sql.SQLException;

//import java.sql.Statement;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

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

  /*
   * Yhteydenotto kantaan vanhaan JDBC-tyyliin
   */
  public Connection Connect() {

    Connection conn;

    try {
      Class.forName("com.mysql.jdbc.Driver");

    } catch (ClassNotFoundException cnfe) {
      System.out.println(cnfe);
    }

    // tietokannan osoite ja tietokannan nimi
    String url = "jdbc:mysql://localhost/kannan_nimi";

    try {
      // kannan tunnari ja salasana
      conn = (Connection) DriverManager.getConnection(url, "root", "root");

      return conn;

    } catch (SQLException e) {
      System.out.println(e);
      return null;

    }

  }

  /*
   * Haetaan kannasta dataa sisääntulevan tiedon perusteella palauttaa String
   * message tai null, riippuen siitä onnistuuko kysely. Avaa importeissa ja alla
   * olevat kommentit ja luo taulu kantaan, niin sovellus toimii kannan kanssa.
   */
  public String getMessage(String value) {

    try {
      // Connection conn = Connect();

      // Tässä tietokantoperaatiot joilla haetaan dataa users-taulusta

      // Statement stmt = conn.createStatement();
      // ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username LIKE '%"
      // + value + "%'");

      // int rivilkm = 0;
      // while (rs.next()) {
      // rivilkm++;
      // }

      /*
       * Tämä esimerkki on laitettu toimimaan niin ettei tarvitse käyttää tietokantaa.
       * Kokeile syöttää käyttöliittymän username-kenttään "username" tai jokin muu
       * arvo
       */
      if (value.equals("username")) {
        // if (rivilkm == 1) {

        message = "On jo käytössä, valitse joku muu!";
      } else {

        message = "Käytettävissä!";
      }

      return message;

    } catch (Exception ex) {

      System.out.println("Tuli poikkeus" + ex);

      return null;
    }

  }

}