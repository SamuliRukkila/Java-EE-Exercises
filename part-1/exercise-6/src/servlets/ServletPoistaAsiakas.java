//Poistetaan asiakas id:n perusteella

package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

//import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Tuito
 */
@WebServlet(name = "ServletPoistaAsiakas", urlPatterns = { "/ServletPoistaAsiakas" })
public class ServletPoistaAsiakas extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Connection conn = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		conn = classes.SQL.openConnection();
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
			throws ServletException, IOException, SQLException {

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

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		String id = request.getParameter("id");

		// Tarkistetaan, että id on olemassa
		if (id != null) {

			// Esitellään PreparedStatement-olio, johon asetetaan myöhemmin lisäyskomento
			PreparedStatement pre_stmt = null;

			// Kaikki tietokantatoiminnot suoritetaan try-lohkon sisällä
			try {
				// Muodostetaan tietokantakomento
				String sql = "DELETE FROM asiakkaat WHERE id='" + id + "'";

				// Alustetaan olio luodulla merkkijonolla.
				pre_stmt = conn.prepareStatement(sql);

				// ExecuteUpdate-komento suorittaa sql-komennon eli tässä tietueen poiston
				// tietokannasta
				pre_stmt.executeUpdate();

				// Ohjataan käyttäjä takaisin etusivulle
				response.sendRedirect("AsiakkaatServlet");
			} catch (SQLException e) {
				out.println("Poisto epäonnistui, poikkeus " + e);
			} // Finally-lohkossa suljetaan PreparedStatement-olio.
			finally {
				try {
					pre_stmt.close();
				} catch (SQLException e) {
					System.out.println(e);
				}
			}

		}

		out.close();
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
		try {
			processRequest(request, response);
		} catch (SQLException ex) {
			Logger.getLogger(ServletPoistaAsiakas.class.getName()).log(Level.SEVERE, null, ex);
		}
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
		try {
			processRequest(request, response);
		} catch (SQLException ex) {
			Logger.getLogger(ServletPoistaAsiakas.class.getName()).log(Level.SEVERE, null, ex);
		}
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
