/*
 * AsiakkaatServlet on palveluservletti joka
 * hakee kaikki asiakkaat kannasta ja laittaa 
 * ne AsiakasBean -tyyppisiin papuihin jotka 
 * kootaan ArrayListiin joka viedään asiakkaat.jsp 
 * -sivulle
 */

package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
// import javax.servlet.http.HttpSession;
import java.util.ArrayList;

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

@WebServlet(name = "AsiakkaatServlet", urlPatterns = { "/AsiakkaatServlet" })
public class AsiakkaatServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	Connection conn = null;

	String id = "";
	String nimi = "";
	String osoite = "";
	String puhelin = "";
	String email = "";
	String salasana = "";

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

		// Tehdään ArrayList johon voidaan sijoittaa CustomerBean-olioita
		ArrayList<AsiakasBean> CustomerList = new ArrayList<>();

		try {
			// luodaan Statement-olio jonka avulla voidaan suorittaa sql-lause
			Statement stmt = conn.createStatement();

			// Suoritetaan sql, otetaan tulosjoukko kiinni.
			ResultSet rs = stmt.executeQuery("SELECT * FROM asiakkaat");

			// Käydään tulosjoukko eli kaikki asiakkaat läpi
			while (rs.next()) {
				// luetaan tietueen tiedot
				id = rs.getString("id");
				nimi = rs.getString("nimi");
				osoite = rs.getString("osoite");
				puhelin = rs.getString("puhelin");
				email = rs.getString("email");
				salasana = rs.getString("salasana");

				// Luodaan papu johon asiakkaan tiedot sijoitetaan
				AsiakasBean papu = new AsiakasBean();
				// tiedot papuun
				papu.setId(id);
				papu.setNimi(nimi);
				papu.setOsoite(osoite);
				papu.setPuhelin(puhelin);
				papu.setEmail(email);
				papu.setSalasana(salasana);

				// lisätään jokainen asiakas papuna ArrayListiin
				CustomerList.add(papu);

			}
		} catch (SQLException e) {
			out.println("Tuli poikkeus!");
		}

		request.setAttribute("list", CustomerList);// lähetetään requestin mukana list

		// RequestDispatcher -oliolla voidaan lähettää data jsp-sivulle
		RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher("/asiakkaat.jsp");
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
