package fish.payara.examples.javaee.stateful.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fish.payara.examples.javaee.stateful.ejb.StatefulTest;

/**
 * 1) Kysessä on normaali @WebServlet -johon injektoidaan @EJB-komponentti,
 * StatefulTest.java. Tämä servlet pitää sessiota yllä.
 * 
 * 2) Tähän servletiin injektoidaan @EJB, jonka avulla nostetaan muuttujan
 * "i" -arvoa, joka sivunlatauksella. 
 * 
 * Ensimmäisellä kerralla kun @EJB injektoidaan tähän servlettiin, laitetaan
 * muuttujan "i" arvoksi 0 (jotta sen arvoa pystytään nostamaan ++ -operaattorilla).
 * Eli ensimmäinen lataus antaa muuttujalle arvoksi "0". Tämän jälkeen joka sivun-
 * latauksessa muuttujaa kutsutaan GET-funktiolla, jossa sen arvoa samalla nostetaan
 * yhdellä. Koska injektoitu @EJB pitää session yllä ( @SessionScoped + @Stateful ),
 * ei arvo mene takaisin nollaan vaan se nousee joka kerralla.
 * 
 * 4)
 * @WebServlet - Annotaatiota, jolla luodaan servletti. Samalla luodaan vastaava 
 *  URL-osoite (tässä tapauksessa /StatefulTestServlet).
 * @PostConstruct -annotaation omaava funktio suoritetaan sen jälkeen, kun riippuvuus-
 *  injektio on tehty, eli se funktio suoritetaan ennenkuin luokka otetaan käyttöön.
 * @SessionScoped - Yleensä tavallisissa beaneissa käytetty tapa, jolla voidaan pitää
 *  sessiota beanissa HTTP:n avulla
 * @Stateful - @EJB:n tapa pitää sessiota yllä, jossa se luo erillisen session-pavun
 *  asiakkaan kunkin pyynnön käsittelemiseksi.
 * @Inject - Tapa, jolla voidaan injektoida esim. tavallinen bean tai EJB -luokkaan
 */

@WebServlet("/StatefulTestServlet")
public class StatefulTestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Inject
	StatefulTest test;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		@SuppressWarnings("unused")
    HttpSession session = request.getSession(true);

		out.println("<html>");
		out.println("<head>");
		out.println("<title></title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<p>Page reload counter</p>");
		out.println("<h1>" + test.getI() + "</h1>");
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
