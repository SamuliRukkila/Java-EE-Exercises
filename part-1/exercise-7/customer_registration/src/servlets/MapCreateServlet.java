/*
 * MapCreateServlet on palveluservletti joka 
 * luo Map-tietorakenteen jossa on id,nimi -pareja 
 * poiston käyttöliittymää ja toteutusta varten
 */

package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import classes.SQL;

@WebServlet(name = "MapCreateServlet", urlPatterns = { "/MapCreateServlet" })
public class MapCreateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	Connection conn = null;

	String id = "";
	String nimi = "";

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

		// HashMap johon id:t ja nimet tulevat
		HashMap<String, String> idnimiMap = new HashMap<String, String>();

		try {
			// luodaan Statement-olio jonka avulla voidaan suorittaa sql-lause
			Statement stmt = conn.createStatement();

			// Suoritetaan sql, otetaan tulosjoukko kiinni.
			ResultSet rs = stmt.executeQuery("SELECT id, nimi FROM asiakkaat");

			// Käydään tulosjoukko läpi
			while (rs.next()) {

				id = rs.getString("id");
				nimi = rs.getString("nimi");

				idnimiMap.put(id, nimi);

			}
		} catch (SQLException e) {
			out.println("Tuli poikkeus!");
		}

		request.setAttribute("map", idnimiMap);// lähetetään pyynnössä map

		// RequestDispatcher -oliolla voidaan lähettää dataa jsp-sivulle
		RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher("/poistaAsiakas.jsp");
		rd.forward(request, response);

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

}
