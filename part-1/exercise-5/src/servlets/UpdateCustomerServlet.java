package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import classes.CustomerBean;

@WebServlet(name="UpdateCustomerServlet", urlPatterns = { "/UpdateCustomerServlet" })

public class UpdateCustomerServlet extends HttpServlet {
  
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
    super.init(config);
    try {
      // Open connection to MySql via external class, opened connection will be
      // assigned to a variable
      conn = classes.SQL.openConnection();
    } catch (Exception e) {
      System.out.println("Kantaan ei saada yhteyttä " + e);
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
    
    // Change request encoding to UTF-8
    request.setCharacterEncoding("UTF-8");
    
    PrintWriter out = response.getWriter();
    HttpSession session = request.getSession(false);
    CustomerBean papu = new CustomerBean();
    
    // Get request's parameters into local variables
    id = request.getParameter("id");
    nimi = request.getParameter("nimi");
    osoite = request.getParameter("osoite");
    puhelin = request.getParameter("puhelin");
    email = request.getParameter("email");
    salasana = request.getParameter("salasana");
    
    // Try to update old row with new values uniqued by ID
    try {
      Statement stmt = conn.createStatement();
      stmt.execute(
        "UPDATE asiakkaat SET " +
        "`nimi` = '"+ nimi +"'," +
        "`osoite` = '"+ osoite +"'," +
        "`puhelin` = '"+ puhelin +"'," +
        "`email` = '"+ email +"'," +
        "`salasana` = '"+ salasana +"' " +
        "WHERE id=" + id + ";"
      );
      // If updation is successful, assing new values to bean
      papu.setId(id);
      papu.setNimi(nimi);
      papu.setOsoite(osoite);
      papu.setPuhelin(puhelin);
      papu.setEmail(email);
      papu.setSalasana(salasana);
    } catch (Exception e) {
      out.println(e);
    }
    
    // Put new attribute "papu" to session
    session.setAttribute("papu", papu);
  }
  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

}
