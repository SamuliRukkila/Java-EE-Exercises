package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ServletMuokkaaAsiakas", urlPatterns = { "/ServletMuokkaaAsiakas" })
public class ServletMuokkaaAsiakas extends HttpServlet {
  
	private static final long serialVersionUID = 1L;
  Connection conn = null;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    // yhteys kantaan tietokantaluokan avulla
    conn = classes.SQL.openConnection();
  }
  
  
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    
    try {
      // ei päästetä servlettiin muita kuin loggautuneita
      HttpSession session = request.getSession(false);

      String log = (String) session.getAttribute("login");
      if (!log.equals("ok")) {
        // ei-loggautuneet takaisin login-sivulle
        response.sendRedirect("login.html");
      }
    } catch (Exception e) {
      response.sendRedirect("login.html");
      return;
    }
    
    // Set request + response encodings to 
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;UTF-8");
    response.setCharacterEncoding("UTF-8");
    
    PrintWriter out = response.getWriter();
    
    // Put request-attributes into local variables
    String id = request.getParameter("id");
    String nimi = request.getParameter("nimi");
    String osoite = request.getParameter("osoite");
    String puhelin = request.getParameter("puhelin");
    String email = request.getParameter("email");
    String salasana = request.getParameter("salasana");
    
    // Verify that needed attributes aren't empty / null
    if (nimi == null || nimi.length() == 0 || 
      email == null || email.length() == 0 || 
      salasana == null || salasana.length() == 0) {
      out.println("Nimi, salasana & sähköposti eivät saa jäädä tyhjäksi");
    } else {
      // If values aren't empty, create an empty statement
      PreparedStatement pre_stmt = null;
      try {
        // Do a update-query to a specific customer, uniqued by ID.
        String sql = "UPDATE asiakkaat SET " +
                     "`nimi` = '"+ nimi +"'," +
                     "`osoite` = '"+ osoite +"'," +
                     "`puhelin` = '"+ puhelin +"'," +
                     "`email` = '"+ email +"'," +
                     "`salasana` = '"+ salasana +"' " +
                     "WHERE id=" + id + ";";
        
        // After we created the variable which holds the query, execute it
        pre_stmt = conn.prepareStatement(sql);
        pre_stmt.executeUpdate();
        // After the query, redirect user to AsiakkaatServlet which'll display all customers
        response.sendRedirect("AsiakkaatServlet");
        
      } catch (SQLException e) {
        out.println(e);
      }
    }
  } 
  
    
  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request  servlet request
   * @param response servlet response
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request  servlet request
   * @param response servlet response
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    processRequest(request, response);
  }

}
