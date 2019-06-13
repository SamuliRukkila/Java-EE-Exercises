package classes;

/*
 * SQL.java
 * Yksinkertainen luokka tietokannan käsittelyyn
 * attribuutit ja metodit staattisia eli tätä kutsutaan luokan nimellä
 * Ei tarvitse (eikä voi) luoda oliota.
 *
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.*;

public class SQL {

  private static Connection conn = null;

  // Kommenteissa esitetty tietokannan tunnareiden haku ulkoisesta tiedostosta.
  // Tunnarit voitaisiin lukea myös Servletin kontekstimuuttujista
  // tiedosto josta tietokannan tunnarit luetaan
  private static String source = "/opt/tomcat/creds.txt";

  // Metodi jolla luetaan webbiin näkymättömästä tiedostosta rivit muuttujiin
  
  public static String readLineFile(String source, int line) throws Exception {
    FileReader in; 
    LineNumberReader lnr; 
    String s ="";
    
    in = new FileReader(source);
    lnr = new LineNumberReader(in);
    
    for(int i=0; i<line;i++) s=lnr.readLine();
    in.close(); lnr.close(); return s; 
  }

  // avaa yhteyden tietokantaan
  public static Connection openConnection() throws Exception {
    try {
      Class.forName("com.mysql.jdbc.Driver");

    } catch (ClassNotFoundException cnfe) {
      System.out.println(cnfe);
    }
    conn = null;

    String url = readLineFile(source, 1); 
    String tunnari = readLineFile(source,2); 
    String salasana = readLineFile(source, 3);

    try {
      /*
       * Otetaan yhteys. Normaalisti rootin tunnareita ei saa tietenkään käyttää
       * Tunnareita ei saisi myöskään kovakoodata koodiin, vaan ne pitäisi hakea
       * ulkopuolisesta tiedostosta kts. kommentoitu koodi ylempänä
       */
      conn = DriverManager.getConnection(url, tunnari, salasana);
    } catch (SQLException e) {
      System.out.println(e);
    }
    // palautetaan avattu yhteys
    return conn;
  }

  // sulkee yhteyden
  public static void closeConnection(Connection conn) throws Exception {
    conn.close();
  }

  // luo yhteydesta Statement-olion
  public static Statement createStmt(Connection conn) {
    try {
      return conn.createStatement();
    } catch (SQLException e) {
      System.out.println(e);
    }
    return null;
  }

  // luo yhteydesta PreparedStatement olion tietyn SQL-lauseen mukaisesti
  public static PreparedStatement createPreStmt(Connection conn, String sql) {
    try {
      return conn.prepareStatement(sql);
    } catch (SQLException e) {
      System.out.println(e);
    }
    return null;
  }

  // hakee tietokannasta tietoa Statementin ja SQL-lauseen avulla
  public static ResultSet getRS(Statement stmt, String query) {
    try {
      return stmt.executeQuery(query);
    } catch (SQLException e) {
      System.out.println(e);
    }
    return null;
  }

  // tekee paivityksen tietokantaan Statementin ja SQL-lauseen avulla
  public static void updateDB(Statement stmt, String query) {
    try {
      System.out.println(query);
      stmt.executeUpdate(query);
    } catch (SQLException e) {
      System.out.println(e);
    }
  }
}
