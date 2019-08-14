package classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mysql.jdbc.PreparedStatement;

public class SQL {

	private static Connection conn = null;
	

	// Connect to Payara's Connection Pool
	public static Connection openConnection() {
	  try {
	    // Create new Context-object
	    Context ctx = new InitialContext();
	    // Search for datasource with ctx-object
	    DataSource ds = (DataSource) ctx.lookup("jdbc/sample");
	    // After datasource is found, place connection-object into conn-variable
	    conn = ds.getConnection();
	  } catch (Exception ex) {
	    System.out.println("Yhteyttä ei saatu: " + ex);
	  }
	  // Return conn -variable back to the caller
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
			return (PreparedStatement) conn.prepareStatement(sql);
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
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}
