package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
 *
 * @author TuiTo
 */
@WebServlet(name = "ServletAsiakasBean", urlPatterns = { "/ServletAsiakasBean" })
public class ServletAsiakasBean extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	Connection conn = null;
	AsiakasBean papu = new AsiakasBean();
	
	 /*
   * init-metodi suoritetaan aina kun Servletti otetaan käyttöön. Se soveltuu
   * hyvin tietokantayhteyden avaamiseen
   */
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

  // destroy-metodi soveltuu hyvin tietokantayhteyden sulkemiseen.
  @Override
  public void destroy() {

    try {
      conn.close();
    } catch (SQLException se) {
      System.out.println("Poikkeus " + se);
    }
  }
	
	
	
	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
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
		
		PrintWriter out = response.getWriter();
		
		String id = request.getParameter("id");
		response.setContentType("text/html;charset=UTF-8");
		
		try {
		  Statement stmt = conn.createStatement();
		  ResultSet rs = stmt.executeQuery(
		      "SELECT * FROM asiakkaat where id=" + id
		  );
		  if (rs.next()) {
		    do {
		      papu.setId(rs.getString("id"));
		      papu.setNimi(rs.getString("nimi"));
		      papu.setOsoite(rs.getString("osoite"));
		      papu.setPuhelin(rs.getString("puhelin"));
		      papu.setEmail(rs.getString("email"));
		      papu.setSalasana(rs.getString("salasana"));
		    } while (rs.next());
		  } else {
        papu.setId(null);
        papu.setNimi(null);
        papu.setOsoite(null);
        papu.setPuhelin(null);
        papu.setEmail(null);
        papu.setSalasana(null);
		  }
		  
		  request.setAttribute("asiakas", papu);
		  RequestDispatcher rd = getServletConfig().getServletContext()
		      .getRequestDispatcher("/muokkaa.jsp");
		  rd.forward(request, response);
		  
		} catch (SQLException e) {
		  out.println(e);
		}
	}

	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
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
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}

}
