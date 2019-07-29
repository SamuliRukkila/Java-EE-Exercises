package fish.payara.examples.javaee.facade.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fish.payara.examples.javaee.facade.ejb.BankServiceFacade;

/**
 * 1) Kysessä on normaali @WebServlet -johon injektoidaan @EJB-komponentti,
 *  BankServiceFacade. Tämä servletti ei pidä sessiota yllä.
 * 
 * 2) Tässä servletissa feikataan lainan hakemus -prosessia käyttäen @EJB 
 * -komponentteja hyväkseen. Kun applikaatio (=servletti) avataan, ajetaan
 * BankServiceFacade -beanin funktio, joka tarkistaa onko asiakas kykenevä
 * lainaan. EJB:en on injektoitu 3 muuta EJB:ta, jotka tekevät omat funktiot
 * asiakkaan ID:n hakemiseen sekä tietojen ja lainan määrän tarkistamiseen.
 * Nämä kaikki tehtävät ovat feikattuja sillä määrin, että 3 määrättyä EJB:ta
 * palauttavat aina "true" -arvon tarkistamatta mitään. 
 * 
 * 4)
 * @WebServlet - Annotaatiota, jolla luodaan servletti. Samalla luodaan vastaava 
 *  URL-osoite (tässä tapauksessa /FacadeTestServlet).
 * @Stateless - EJB-komponentti, joka tuhoaa session joka kutsun jälkeen
 * @Inject - Tapa, jolla voidaan injektoida esim. tavallinen bean tai EJB -luokkaan
 * 
 */

@WebServlet("/FacadeTestServlet")
public class FacadeTestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String result;
	
	/**
	 * 2 & 3) Injektoitu @EJB -komponentti, joka tekee lainan tarkistustehtävät
	 */
	@EJB
	BankServiceFacade bsf;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		boolean canHaveLoan = this.bsf.getLoan(123456, 1000.00);

		if (canHaveLoan) {
			result = "Yes";
		} else {
			result = "No";
		}

		out.println("<html>");
		out.println("<head>");
		out.println("<title></title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<p>Can customer have loan?</p>");
		out.println("<h1>" + result + "</h1>");
		out.println("</body>");
		out.println("</html>");
		out.close();
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
