package classes;

/*
 * SQL.java
 * Yksinkertainen luokka tietokannan käsittelyyn
 * attribuutit ja metodit staattisia eli tätä kutsutaan luokan nimellä
 * Ei tarvitse (eikä voi) luoda oliota.
 *
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.mysql.jdbc.PreparedStatement;

public class SQL {

	private static Connection conn = null;
	

	// avaa yhteyden tietokantaan
	public static Connection openConnection() {
	  try {
	    Context ctx = new InitialContext();
	    DataSource ds = (DataSource) ctx.lookup("jdbc/sample");
	    conn = ds.getConnection();
	  } catch (Exception ex) {
	    System.out.println("Yhteyttä ei saatu: " + ex);
	  }
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