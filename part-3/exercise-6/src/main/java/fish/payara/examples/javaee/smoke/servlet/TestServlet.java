/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2016 Payara Foundation and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://github.com/payara/Payara/blob/master/LICENSE.txt
 * See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * The Payara Foundation designates this particular file as subject to the "Classpath"
 * exception as provided by the Payara Foundation in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package fish.payara.examples.javaee.smoke.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

import fish.payara.examples.javaee.smoke.cdi.ApplicationScopedBean;
import fish.payara.examples.javaee.smoke.cdi.EJBInjectionTester;
import fish.payara.examples.javaee.smoke.cdi.POJO;
import fish.payara.examples.javaee.smoke.cdi.RequestScopedBean;
import fish.payara.examples.javaee.smoke.cdi.SessionScopedBean;
import fish.payara.examples.javaee.smoke.ejb.TestSessionBean;
import fish.payara.examples.javaee.smoke.ejb.TestSingletonBean;

/**
 * 1) Kyseisen sovellus toimii servletissa. Servletti on määritelty 
 * @WebServlet -määritelmällä, kuten myös servletin URL (urlPatterns).
 * Servletin lähtyä päälle se myös luo muutaman parametrin (initParams),
 * joita tullaan käyttämään servletin sisällä. Määritelly parametrit 
 * määritellään nimineen ja arvoineen tämän jälkeen @WebInitParam -määritelmässä
 * (parametreja ei siis itse tarvitse tuoda tähän servlettiin). 
 * 
 * 2) Sovellus tekee erilaisia injektioita tavallisiin- sekä enteprise -javabeaneihin. 
 * Servletissä kokeillaan myös eventin tekoa ja sen vuorovaikutusta beanien kanssa,
 * sekä executor-roolin, transaktionin, MySQL -palvelun injektoimista.
 * Mukana on myöskin parametrien sekä session -attribuuttien testailua. Servletin 
 * templaatissa näiden beanien jne. funktioita sekä "olemassaoloja" kutsutaan, jotta
 * nähdään, että jokainen tarvittava palvelu on injektoitu onnistuneesti.
 * 
 * Jotta sovellus toimisi normaalisti, pitäisi jokainen kutsu templaatissa
 * tulostaa "true" arvon. Injektiot määritellään tarkemmin itse koodissa.
 * 
 * 4)
 * @EJB - Injektoidaan Enterprise JavaBean kyseiseen sovellukseen
 * @Inject - Injektoidaan tavallinen bean, arvo tmv. sovellukseen
 * @WebServlet - Kertoo, että kyseessä on web-servlet
 * @WebInitParam - Luodaan "tyhjästä" parameterja sovellukselle luontivaiheessa
 * @Resource -annotaatio kertoo, että tämä resurssi viitataan tähän palveluun. Servletti injektoi resurssin automaattisesti.
 * @Override - Yliajaa isä-luokan (tässä tilanteessa HttpServlet) funktion
 * @Observes - Jos kyseinen annotaatio on funktion parameterissa, se kertoo, että kyseinen parameteri lukee event -arvon
 *  vaihtuvia arvoja reaaliajassa ja ajaa funktion uudestaan aina kun arvo vaihtuu.
 * @TrueInterceptor - Rajapinta, mikä muuttaa arvon ennen lähetystä esim. "false"=> "true".
 * 
 * @RequestScoped - Esim. bean, jonka tiedot on tallessa vain yhden pyynnön aikana. Tuhotaan pyynnön jälkeen.
 * @SessionScoped - Esim. bean, jonka tiedot pysyvät tallessa yhden session aikana. Tuhotaan kun sessio loppuu.
 * @ApplicationScoped - Esim. bean, jonka tiedot pysyvät tallessa koko applikaation elinkierron aikana.
 */

/**
 *
 * @author Steve Millidge (Payara Foundation)
 */
@WebServlet(name = "TestServlet", urlPatterns = { "/TestServlet" }, initParams = {
		@WebInitParam(name = "Init1", value = "Test") })
public class TestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private boolean initPass1 = false;
	private boolean initPass2 = false;
	
	/**
	 * 3) @EJB -komponentti. Toimii välikätenä tämän servletin sekä @EJB:n omien
	 * injektoitujen, tavallisten beanien välillä välittääkseen tietoa.
	 */
	@EJB
	TestSessionBean bean;
	
	/**
	 * 3) @EJB -komponentti. Toimii tämän servletin sekä omien funktioiden kanssa.
	 */
	@EJB
	TestSingletonBean singleton;
	
	/**
	 * 3) Tavallinen @RequestScoped -injektoitu bean. Säilyy vain pyynnön aikana, kunnes
	 * sen jälkeen sen sessio tuhotaan heti.
	 * Ei ole @EJB-komponentti
	 */
	@Inject
	RequestScopedBean requestBean;
	
	/**
	 * 3) Tavallinen @SessionSCoped -injektoitu bean. Säilyttää yhden session ajaksi arvonsa. Kun
	 * sessio loppuu, tuhotaan se.
	 * Ei ole @EJB-komponentti
	 */
	@Inject
	SessionScopedBean sessionBean;
	
	 /**
   * 3) Tavallinen @ApplicationScoped -injektoitu bean. Säilyttää arvonsa koko applikaation 
   * elinkaaren ajaksi, tuhoutuu vasta kun applikaatio suljetaan/tuhotaan kokonaan.
   * Ei ole @EJB-komponentti
   */
	@Inject
	ApplicationScopedBean appbean;
	
	/**
	 * 3) Tavallinen @RequestScoped -injektoitu bean. Kyseinen bean ottaa yhteyttä @EJB -beaniin.
	 * Ei ole @EJB-komponentti. 
	 */
	@Inject
	EJBInjectionTester ejbCDIBean;
	
	/**
	 * 2) Injektoidaan tavallinen boolean -arvo servlettiin. Injektoitu boolean -arvo 
	 * on normaalisti "true" -arvoinen.
	 * 3) Ei @EJB-komponentti
	 */
	@Inject
	Boolean value;
	
	/**
	 * 3) Tavallinen bean. Ei olla annettu scope -arvoa, joten on automaattisesti @RequestScoped
	 * -arvoinen.
	 * Ei @EJB-komponentti.
	 */
	@Inject
	POJO pojo;
	
	/**
	 * 2) Luodaan injektoitu CDI-event, jota voidaan käyttää kaikissa injektoiduissa beaneissa hyväksi.
	 * Jotta sitä voidaan käyttää funktiossa hyväksi parametrina, tarvitaan @Observes -annotaatio.
	 * 3) Ei @EJB-komponentti
	 */
	@Inject
	Event<Integer> countEvent;
	
	/**
	 * 2) Palvelu, joka tarjoaa menetelmiä suorittaa jonkinlaisia tehtäviä Java EE -ympäristössä. Kuvaa
	 * usein applikaation käyttäytymismalleja enemmän kuin itse logiikkaa. Tämän palvelun avulla on
	 * myös mahdollista suorittaa transaktioneita.
	 * 3) Ei @EJB-komponentti
	 */
	@Resource
	ManagedExecutorService executor;
	
	/**
	 * 2) Käyttöliittymä, joka määrittelee menetelmät, joiden avulla sovellus voi hallita tapahtumarajoja-
	 * 3) Ei @EJB-komponentti
	 */
	@Resource
	UserTransaction transaction;
	
	/**
	 * 2) Injektoidaan MySQL -palvelu tähän servlettiin. Samanniminen resurssi etsitään persistence.xml
	 * -tiedostosta. 
	 * 3) Ei @EJB-komponentti
	 * 4) @Resource -annotaatio kertoo, että tämä resurssi viitataan tähän palveluun. Servletti injektoi
   * resurssin automaattisesti.
	 */
	@Resource(name = "java:comp/DefaultDataSource")
	private javax.sql.DataSource dsc;
	
	/**
	 * 2) Tämä funktio ajetaan aina automaattisesti kun servletti avataan. Katsotaan,
	 * että parametrissa tulleet arvot vat samoja kuin mitä ollaan staattisesti ilmoitettu.
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		initPass1 = "Test".equals(config.getInitParameter("Init1"));
		initPass2 = "Test2".equals(config.getServletContext().getInitParameter("Init2"));
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
		response.setContentType("text/html;charset=UTF-8");
		
		/**
		 * 2) Servletin omaan sessioon laitetaan RequestCount -niminen lukumäärä, jota
		 * käytetään osissa injektoiduissa beaneissa. Joka sivunlatauksen jälkeen katsotaan,
		 * onko kyseinen sessio-attribuutti jo voimassa, jos ei ole laitetaan se nollaksi.
		 */
		HttpSession session = request.getSession(true);
		Integer requestCount = (Integer) session.getAttribute("RequestCount");
		if (requestCount == null) {
			requestCount = 0;
		}
		
		/**
		 * 2) Katsotaan, onko servletin oman sessio-attribuutin arvo sama kuin
		 * @ApplicationScoped -tyyppisessä tavallisessa beanissa (pitäisi palauttaa
		 * arvon "true").
		 */
		boolean appBeanSameCount = (requestCount == appbean.getStoredValue());
		
		/**
		 * 2) Katsotaan onko sessio-attribuutin lukumäärä sama kuin mitä 
		 * saamme injektoidusta @EJB-komponentista (TestSingletonBean).
		 */
		boolean singletonSameCount = (requestCount == singleton.getStoredValue());
		
		
		/**
		 * 2) Päivitetään event -attribuutin arvoksi servletin sessio-attribuutin arvo. 
		 * appbean -oliossa on funktio, joka vastaanottaa event -arvoja @Observes -annotaatiolla.
		 * Katsotaan lopuksi muuttujassa, että eventin arvon nousu sekä appbean -olion "eventValue" 
		 * -attribuutin arvot ovat nousseet kummatkin yhtä aikaa.
		 */
		countEvent.fire(requestCount);
		boolean eventCountTest = appbean.getEventValue() == requestCount;
		
		/**
		 * 2) Katsotaan eventin kanssa onko sessio-attribuutin lkm sama kuin mitä
		 * saamme injektoidusta @EJB-komponentista (TestSingletonBean).
		 */
		boolean ejbEventtest = singleton.getEventValue() == requestCount;
		
		/**
		 * 2) Lisätään servletin sessio-attribuutin arvoksi +1, ja pohjustetaan se uudelleen
		 * servlettiin. Samalla päivitetää myös appbean -beanin arvoa.
		 */
		requestCount = requestCount + 1;
		session.setAttribute("RequestCount", requestCount);
		appbean.setStoredValue(requestCount);
		
		/**
		 * 2) Tallennetaan @EJB -komponenttiin session-attribuutin lkm-arvo 
		 * (TestSingletonBean).
		 */
		singleton.setStoredValue(requestCount);
		
		
		try (PrintWriter out = response.getWriter()) {
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Basic Injection Tests</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Basic Injection Tests</h1>");
			out.println("<table>");
			
			/**
			 * 2) Katsotaan oliko parametreissa tulleet arvot samoja kuin mitä ollaan 
			 * staattisesti init -funktiossa ilmoitettu, joka ajetaan aina kun servlet avataan.
			 */
			out.println("<tr><td>Init Parameter 1: </td><td>" + initPass1 + "</td></tr>");
			out.println("<tr><td>Init Parameter 2: </td><td>" + initPass2 + "</td></tr>");
			
			/**
			 * 2) Katsotaan onko requestissa tulleet arvot samoja kuin alla mainitut staattiset tekstit.
			 */
			out.println("<tr><td>Filter Attribute: </td><td>" + "Test".equals(request.getAttribute("Filter1"))
					+ "</td></tr>");
			out.println(
					"<tr><td>Filter Init: </td><td>" + "Test".equals(request.getAttribute("Filter2")) + "</td></tr>");
			out.println("<tr><td>Filter CDI AS Injection </td><td>" + request.getAttribute("FilterAppInject")
					+ "</td></tr>");
			out.println(
					"<tr><td>Filter CDI SS Injection </td><td>" + request.getAttribute("FilterSInject") + "</td></tr>");
			out.println(
					"<tr><td>Filter CDI RS Injection </td><td>" + request.getAttribute("FilterRInject") + "</td></tr>");
			out.println(
					"<tr><td>Filter EJB Injection </td><td>" + request.getAttribute("FilterEJBInject") + "</td></tr>");
			
			/**
			 * 2) Kokeillaan saadaanko @EJB-komponenttiin yhteys eli kutsutaan komponentin yhtä funktiota. Jos palauttaa
			 * "true", yhteys on muodostettu.
			 */
			out.println("<tr><td>Servlet EJB Injection: </td><td>" + bean.doYouExist() + "</td></tr>");
			
			/**
			 * 2) Kokeillaan saadaanko yhteys @RequestScoped -tyyppiseen, tavalliseen injektoituun beaniin
			 */
			out.println("<tr><td>Servlet CDI RS Injection: </td><td>" + requestBean.doIExist() + "</td></tr>");
			
			/**
			 * 2) Kokeillaan saadaanko yhteys @RequestScoped -tyyppiseen, tavalliseen
			 * beaniin.
			 */
			out.println("<tr><td>Servlet POJO Injection: </td><td>" + pojo.doIExist() + "</td></tr>");
			
			/**
			 * 2) Katsotaan onko arvo muuttunut requestBean -olion muuttujassa funktiolla, joka lopuksi muuttaa 
			 * storedValue -arvon falseksi ennen palautusta. Koska ko. bean on @RequestScoped -tyyppinen, pitäisi
			 * luokan sisällä tehdyt muutokset tuhoutua, joten tämän funktion pitäisi aina palauttaa "true" -arvo.
			 */
			out.println("<tr><td>Servlet CDI RS Stored Value Reset: </td><td>" + requestBean.isStoredValueReset()
					+ "</td></tr>");
			
			/**
			 * 2) Katsotaan onko yhteys @SessionScoped -tyyppiseen, injektoituun beaniin tehty (palauttaa "true")
			 */
			out.println("<tr><td>Servlet CDI SS Injection: </td><td>" + sessionBean.doIExist() + "</td></tr>");
			
			/**
			 * 2) Joka kerta kun servletti ladataan uudestaan, pitäisi vanhan lkm-arvo olla vielä tallessa session
			 * takia. Joka sivunlatauksen jälkeen arvoa nostetaan yhdellä. Tutkitaan onko lkm sama kuin servletin 
			 * oman requestCount -attribuutin kanssa. Lkm tulostetaan.
			 */
			out.println("<tr><td>Servlet CDI SS Stored Value Count: </td><td>"
					+ requestCount.equals(sessionBean.incrementValue()) + "</td><td> Value is " + sessionBean.getValue()
					+ "</td></tr>");
			
			/**
			 * 2) Katsotaan, että servletin sessio-attribuutin arvo sekä @ApplicationScoped -beanin muuttujan arvot
			 * ovat samoja. Tulostetaan kyseinen arvo.
			 */
			out.println("<tr><td>Servlet CDI AS Stored Value Count: </td><td>" + appBeanSameCount
					+ "</td><td> Value is " + appbean.getStoredValue() + "</td></tr>");
			
			/**
			 * 2) Bean -olio on injektoitu @EJB-komponentti, joka toimii välikätenä kolmen normaalin (scopet 
			 * @RequestScoped, @SessionScoped & @ApplicationScoped) beanin välinä. Tässä kutsutaan @EJB-komponentissa
			 * olevia funktiota, jotka edelleen ovat suoraan beaneista. Jos kaikki 3 tulostavat "true" -arvon, yhteys
			 * on oikein muodostettu servlettien sekä beanien välillä.
			 */
			out.println("<tr><td>EJB CDI AS Injection: </td><td>" + bean.testAppInjection() + "</td></tr>");
			out.println("<tr><td>EJB CDI SS Injection: </td><td>" + bean.testSInjection() + "</td></tr>");
			out.println("<tr><td>EJB CDI RS Injection: </td><td>" + bean.testRInjection() + "</td></tr>");
			
			/**
			 * 2) Katsotaan onko tavallinen, injektoitu bean saannut yhdistettyä @EJB-komponentin kanssa. 
			 * Palauttaa "true" -arvon jos yhdistyminen on onnistunut.
			 */
			out.println("<tr><td>CDI EJB Injection: </td><td>" + ejbCDIBean.doesEJBExist() + "</td></tr>");
	     
			/**
       * 2) appBean -olio on injektoitu @EBJ-komponentti. singleton -luokassa funktio normaalisti palauttaisi
       * "false" -arvon, mutta se on bindattu interfacen kanssa, joka muuttaa sen "true" -arvoksi ennen palautusta.
       */
			out.println("<tr><td>CDI Interceptor: </td><td>" + appbean.returnFalse() + "</td></tr>");
			
			/**
			 * 2) Tulostetaan @Inject -tyyppinen boolean -arvo. Muuttujalle ei olla annettu arvoa, joten
			 * se pohjustuu automaattisesti "true" -arvoon.
			 */
			out.println("<tr><td>CDI Produces Boolean: </td><td>" + value + "</td></tr>");
			
			/**
			 * 2) Katsotaan oliko eventin arvo sekä @ApplicationScoped -beanin arvot samat.
			 */
			out.println("<tr><td>CDI Eventing: </td><td>" + eventCountTest + "</td></tr>");
			/**
			 * 2) Katsotaan oliko eventin arvo sekä @EJB-komponentin arvot samat.
			 */
			out.println("<tr><td>CDI Eventing in EJB: </td><td>" + ejbEventtest + "</td></tr>");
			
			/**
			 * 2) singleton -olio on injektoitu @EJB-komponentti. Katsotaan, että sessio-attribuutin sekä
			 * @EJB-komponentista löytyvän lukumäärän arvot ovat pysyneet samana (tulostetaan samalla
			 * lukumäärä). 
			 */
			out.println("<tr><td>Servlet Singleton EJB Stored Value Count: </td><td>" + singletonSameCount
					+ "</td><td> Value is " + singleton.getStoredValue() + "</td></tr>");
			/**
			 * 2) singleton -olio on injektoitu @EBJ-komponentti. singleton -luokassa funktio normaalisti palauttaisi
			 * "false" -arvon, mutta se on bindattu interfacen kanssa, joka muuttaa sen "true" -arvoksi ennen palautusta.
			 */
			out.println("<tr><td>Singleton EJB CDI Interceptor: </td><td>" + singleton.returnFalse() + "</td></tr>");
			
			/**
			 * 2) Katsotaan, että @Resource -annotiaatiolla tuotu executor -rooli on onnistuneesti injektoitu servlettiin.
			 */
			out.println("<tr><td>Executor Injection: </td><td>" + (executor != null) + "</td></tr>");
			
			/**
			 * 2) Katsotaan, että @Resource -annotiaatiolla tuotu transaktioni -käyttöliittymä on onnistuneesti
			 * injektoitu servlettiin.
			 */
			out.println("<tr><td>UserTransaction Injection: </td><td>" + (transaction != null) + "</td></tr>");
	     /**
       * 2) Katsotaan, että @Resource -annotiaatiolla tuotu MySQL -palvelu on onnistuneesti
       * injektoitu servlettiin.
       */
			out.println("<tr><td>Default DS Injection: </td><td>" + (dsc != null) + "</td></tr>");
			
			out.println("</table>");
			out.println("</body>");
			out.println("</html>");
		}
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
