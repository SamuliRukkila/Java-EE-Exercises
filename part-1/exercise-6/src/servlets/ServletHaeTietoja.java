package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import classes.AsiakasBean;
import classes.SQL;

/**
 * Servlet implementation class ServletHaeTietoja
 */
@WebServlet(name = "ServletHaeTietoja", urlPatterns = { "/ServletHaeTietoja" })
public class ServletHaeTietoja extends HttpServlet {
  
	private static final long serialVersionUID = 1L;
	Connection conn = null;
  
	String id = "";
	String nimi = "";
	String osoite = "";
	String puhelin = "";
	String email = "";
	String salasana = "";
	
	@Override
	public void init(ServletConfig config) throws ServletException {
	  // Kutsutaan perittävän luokan konstruktoria.
	  super.init(config);
	  try {
	    conn = SQL.openConnection();
	  } catch (Exception e) {
	    System.out.println("Poikkeus " + e);
	  }
	}
	
	@Override
	public void destroy() {
	  try {
	    conn.close();
	  } catch (SQLException se) {
	    System.out.println("Poikkeus " + se);
	  }
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	  throws ServletException, IOException {
	  
	  String word = (String) request.getParameter("hakusana");
	  String choice = (String) request.getParameter("hakuvalinta");
	  
	  try {
	    HttpSession session = request.getSession(false);
	    String log = (String) session.getAttribute("login");
	    if (!log.equals("ok")) {
	      response.sendRedirect("login.html");
	    }
	  } catch (Exception e) {
	    response.sendRedirect("login.html");
	    return;
	  }
	  
	  PrintWriter out = response.getWriter();
	  ArrayList<AsiakasBean> CustomerList = new ArrayList<>();
	  
	  try {
	    Statement stmt = conn.createStatement();
	    ResultSet rs = stmt.executeQuery(
	        "SELECT * FROM asiakkaat WHERE `" + choice + "` LIKE '%" + word + "%';");
	    
	    while (rs.next()) {
	      id = rs.getString("id");
	      nimi = rs.getString("nimi");
	      osoite = rs.getString("osoite");
	      puhelin = rs.getString("puhelin");
	      email = rs.getString("email");
	      salasana = rs.getString("salasana");
	      
	      AsiakasBean papu = new AsiakasBean();
	      
	      papu.setId(id);
	      papu.setNimi(nimi);
	      papu.setOsoite(osoite);
	      papu.setPuhelin(puhelin);
	      papu.setEmail(email);
	      papu.setSalasana(salasana);
	      
	      CustomerList.add(papu);
	    }
	  } catch (SQLException e) {
	    out.println(e);
	  }
	  
	  request.setAttribute("list", CustomerList);
	  request.setAttribute("hakusana", word);
	  request.setAttribute("hakuvalinta", choice);
	  
	  RequestDispatcher rd = getServletConfig().getServletContext()
	    .getRequestDispatcher("/haku.jsp");
	  rd.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
