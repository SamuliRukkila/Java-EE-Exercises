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

import fish.payara.examples.javaee.smoke.cdi.RollbackException;
import fish.payara.examples.javaee.smoke.cdi.TransactionalCDI;
import fish.payara.examples.javaee.smoke.ejb.SmokeJPABMBean;
import fish.payara.examples.javaee.smoke.ejb.SmokeJPABean;
import fish.payara.examples.javaee.smoke.ejb.data.Person;
import fish.payara.examples.javaee.smoke.ejb.data.SmokeEntity;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 1) Kyseinen sovellus toimii @WebServlet -annotaation alla. Eli
 * se on servletti, johon ei tuoda omia parametreja.
 * 
 * 2) Kyseinen servletti injektoi itseensä muutaman @EJB -komponentin
 * sekä yhden @ApplicationScoped -annotaatiolla varustetun beanin. 
 * 
 * Servletissä tehdään yllämainittujen beanien avulla erilaisia
 * tietokanta-kyselyitä käyttäen hyväksi luotuja JPA-luokkia. Esimerkki-
 * beaneissa käytetään tavallista ORM-objektointimuotoa sekä myös 
 * transaktionia käyttäen UserTransaction -kirjastoa sekä 
 * @Transactional -annotaatiota.
 * 
 * Jotta, servletti toimii oikein, pitää jokaisen kutsun palauttaa "true"
 * -arvon servletin omassa templaattissa, jossa se kutsuu beanien funktiota.
 * 
 * 
 * 4)
 * @Stateless - @EJB-komponentti, joka tuhoaa session joka kutsun jälkeen
 * @ApplicationScoped - Bean, joka on toiminnassa applikaation elinkaaren ajan
 * @PersistenceContext - Otetaan persistence.xml tiedostossa olevaan
 * -palvelimeen yhteys. Tämän mukana annetaan halutun yhteyden nimi, 
 * jotta yhteys saadaan muodostettua (esim. MySQL -palvelimeen).
 * @Resource -annotaatio kertoo, että tämä resurssi viitataan tähän palveluun. Servletti injektoi resurssin automaattisesti.
 * @EJB - Injektoidaan Enterprise JavaBean kyseiseen sovellukseen
 * @Inject - Injektoidaan tavallinen bean, arvo tmv. sovellukseen
 * @TransactionManagement - Määrittää, onko pavulla "kontti-hallittuja" vai
 * "papu-hallittuja" tapahtumia. 
 * @Transactional - Mahdollstaa JPA-kyselyiden automaattisen transaktionin ilman
 * esim. UserTransaction -kirjastoa. 
 * 
 * @Entity - Mahdollistaa JPA-luokassa ORM:in käytön SQL-kyselyille.
 * @OneToOne - Yksi-yhteen -suhde SQL-taulussa
 * @ManyToOne - Moni-yhteen -suhde SQL-taulussa
 * @OneToMany - Yksi-moneen -suhde SQL-taulussa
 */

/**
 *
 * @author Steve Millidge (Payara Foundation)
 */
@WebServlet(name = "SmokeTestJPA", urlPatterns = {"/SmokeTestJPA"})
public class SmokeTestJPA extends HttpServlet {
    
  private static final long serialVersionUID = 1L;
  
    /**
     * 2) Komponentti sisältää funktioita, jotka tekee SQL-kyselyitä kuten 
     * rivien poisto, etsintä, lisääminen jne. Kyseinen @EJB-komponentti 
     * on yhteydessä kahteen muuhun JPA-luokkaan. Tämä komponentti toimiikin 
     * siis välikätenä tämän servletin ja JPA-luokkien välillä.
     * 3) @EJB-komponentti. 
     */
    @EJB
    SmokeJPABean ejb;
    
    /**
     * 2) Komponentti sisältää funktiota, jotka tekee transaktionin avulla 
     * SQL-kyselyitä kuten rivien poisto, etsintä jne. Toisin siis kuin
     * ylempi @EJB-komponentti, tässä funktiot ensin aloittavat transaktionin,
     * tekevät tarvittavat muutokset ja lopuksi vasta committaa ne (tallentaa 
     * lopulliste) tauluun.
     * 3) @EJB-komponentti
     */
    @EJB
    SmokeJPABMBean bmejb;
    
    /**
     * 2) @ApplicatinScoped -bean, joka sisältää funktiota, jotka tekevät SQL-kyselyitä sekä
     * -komentoja käyttäen @Transactional -annotaatiota, jokaisessa funktiossa. 
     * Tämän takia ei transaktionin alkua sekä commit -funktiota tarvitse erikseen
     * kutsua yksinkertaistaen täten koodia.
     * 3) Ei ole @EJB-komponentti vaan @ApplicationScoped -tyypillä toimiva,
     * tavallinen bean.
     */
    @Inject
    TransactionalCDI cdi;


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SmokeTestJPA</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>JPA Smoke tests</h1>");
            out.println("<table>");
            out.println("<tr><td>Persist and Retrieve EJB: </td><td>" + testInsertandRetrieve() + "</td></tr>");
            out.println("<tr><td>Delete All EJB: </td><td>" + testDeleteAll() + "</td></tr>");
            out.println("<tr><td>Persist and Retrieve CDI: </td><td>" + testInsertandRetrieveCDI() + "</td></tr>");
            out.println("<tr><td>Delete All CDI: </td><td>" + testDeleteAllCDI() + "</td></tr>");
            out.println("<tr><td>Persist and Retrieve EJB  BMT: </td><td>" + testInsertandRetrieveBMT()+ "</td></tr>");
            out.println("<tr><td>Delete All EJB BMT: </td><td>" + testDeleteAllBMT()+ "</td></tr>");
             out.println("<tr><td>Test Relations: </td><td>" + testRelations()+ "</td></tr>");
            out.println("<tr><td>Test Rollback: </td><td>" + testRollback()+ "</td></tr>");
            out.println("<tr><td>Test Rollback BMT: </td><td>" + testRollbackBMT()+ "</td></tr>");
            out.println("<tr><td>Test Rollback CDI: </td><td>" + testRollbackCDI()+ "</td></tr>");
            out.println("</table>");            
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    /**
     * 2) Tässä komponentissa käytetään hyväksi @EJB-komponenttia - "ejb". 
     * Aluksi luodaan uusi SmokeEntity -JPA-luokan olio, jolla voidaan tehdä
     * SQL-kyselyitä käyttäen ORM:ia. Tähän olioon lisätään valmiiksi määriteltyä
     * dataa. Lopuksi katsotaan, löytyykö tämä juuri tallennettu rivi taulusta. 
     * Jos se löytyy, palautetaan "true" -arvo.
     */
    private boolean testInsertandRetrieve() {
        @SuppressWarnings("unused")
        boolean result = false;
        SmokeEntity se = ejb.insert();
        SmokeEntity se2 = ejb.retrieve(se.getId());
        return Objects.equals(se.getId(), se2.getId());
    }
    
    /**
     * 2) Tässä komponentissa käytetään hyväksi @EJB-komponenttia - "ejb".
     * bulkLoad() -funktio tallentaa 1000 uutta riviä tietokantaan. Tämän jälkeen
     * lasketaan, että 1000 arvoa ollaan tallennettu tietokantaan countAll() -funktiolla.
     * Lopuksi poistamme kaikki 1000 uutta riviä ja katsomme uudestaan countAll() 
     * -funktiolla, että kaikki rivit tosiaan poistettiin. Jos kaikki tämä on tehty
     * onnistuneesti palautetaan "true" -arvo.
     */
    private boolean testDeleteAll() {
        boolean result = false;
        ejb.bulkLoad(1000);
        result = ejb.countAll() >= 1000;
        ejb.deleteAll();
        result = result && (ejb.countAll() == 0);
        return result;
    }
    
    /**
     * 2) Käytetään @EJB-komponenttia - "bmejb". 
     * Luodaan uusi rivi käyttäen transaktionia. Tämän jälkeen koitetaan etsiä 
     * tämä juuri luotu rivi ID:n perusteella. Jos se löytyy, palauttaa funktio
     * arvon "true".
     */
    private boolean testInsertandRetrieveBMT() {
        @SuppressWarnings("unused")
        boolean result = false;
        SmokeEntity se = bmejb.insert();
        SmokeEntity se2 = bmejb.retrieve(se.getId());
        return Objects.equals(se.getId(), se2.getId());
    }
    
    /**
     * 2) Käytetään @EJB-komponenttia - "bmejb". 
     * Luodaan 1000 uutta riviä, ja katsotaan .count() -funktion
     * avulla, että rivejä on vähintään 1000. Tämän jälkeen
     * poistetaan kaikki rivit ja katsotaan, että rivejä on 
     * tasan 0. Jos kaikki komennot onnistuvat, palautetaan arvon "true".
     */
    private boolean testDeleteAllBMT() {
        boolean result = false;
        bmejb.bulkLoad(1000);
        result = bmejb.countAll() >= 1000;
        bmejb.deleteAll();
        result = result && (bmejb.countAll() == 0);
        return result;
    }
    
    /**
     * 2) Käytetään @ApplicationScoped -beania - "cdi".
     * Luodaan uusi rivi SQL-tauluun, ja sen jälkeen etsitään tämä
     * rivi ID:n avulla. Jos rivi luodaan, ja löydetään jälkeenpäin, 
     * palautetaan "true" -arvo. "cdi" -objekti käyttää hyväkseen 
     * @Transactional -annotaatiota.
     */
    private boolean testInsertandRetrieveCDI() {
        @SuppressWarnings("unused")
        boolean result = false;
        SmokeEntity se = cdi.insert();
        SmokeEntity se2 = cdi.retrieve(se.getId());
        return Objects.equals(se.getId(), se2.getId());
    }
    
    /**
     * 2) Käytetään @ApplicationScoped -beania - "cdi".
     * Luodaan 1000 uutta riviä, lasketaan, että rivejä on väh. 1000.
     * Lopuksi poistetaan kaikki rivit ja katsotaan uudestaan, että
     * rivejä on tasan 0. Jos kaikki SQL-kyselyt onnistuvat palautetaan
     * "true" -arvo. "cdi" -objekti käyttää hyväkseen @Transactional -annotaatiota.
     */
    private boolean testDeleteAllCDI() {
        boolean result = false;
        cdi.bulkLoad(1000);
        result = cdi.countAll() >= 1000;
        cdi.deleteAll();
        result = result && (cdi.countAll() == 0);
        return result;
    }
    
    /**
     * 2) Tässä komponentissa käytetään hyväksi @EJB-komponenttia - "ejb".
     * Person -JPA-luokka sisältää relaatioita muihin "feikkitauluihin". 
     * Tässä funktiossa testataan, että relaatiot toimivat eri JPA-luokkien välillä.
     * Jos yhteydet toimivat keskenään, palautetaan "true" -arvo.
     * 
     */
    private boolean testRelations() {
        boolean result = false;
        Person fred = ejb.createFamily();
        fred = ejb.retrieveFamily(fred.getId());
        result = fred != null;
        result = result && (fred.getSpouse() != null);
        result = result && (fred.getSpouse().getSpouse().getId().equals(fred.getId()));
        result = result && (fred.getChildren().size() > 0);
        return result;
    }
    
    /**
     * 2) Tässä komponentissa käytetään hyväksi @EJB-komponenttia - "ejb".
     * Aluksi poistetaan kaikki mahdolliset rivit taulusta. Tämän jälkeen ajetaan
     * rollback() -funktio mikä lisää 1:n uuden rivin ja sitten käyttäen rollbackkia,
     * ajaa sen pois. Lopuksi katsotaan, että taulussa olevien rivien määrä on vielä 0.
     * Jos rivejä ei ole yhtään, palautetaan "true" -arvo.
     */
    private boolean testRollback() {
        boolean result = false;
        ejb.deleteAll();
        ejb.rollBack();
        result = ejb.countAll() == 0;
        return result;
    }
    
    /**
     * 2) Käytetään @EJB-komponenttia - "bmejb". Kaikissa kyselyissä
     * käytetään transaktionia.
     * Aluksi poistetaan kaikki mahdollisit rivit taulusta. Tämän
     * jälkeen meinataan lisätä 1 uusi rivi, mutta kutsutaankin @EJB-
     * komponentin rollback() -funktiota mikä lopettaa transaktionin
     * ja pyyhkii kaikki sen mukana tulleet muutokset pois, tarkoittaen
     * että rivejä ei pitäisi löytyä yhtään. Jos rivejä ei lopussa löydy yhtään
     * palautetaan arvo "true".
     */
    private boolean testRollbackBMT() {
        boolean result = false;
        bmejb.deleteAll();
        bmejb.rollBack();
        result = bmejb.countAll() == 0;
        return result;
    }
    
    /**
     * 2) Käytetään @ApplicationScoped -beania - "cdi".
     * Aloitetaan luomaan uutta riviä SQL-tauluun. Ennekuin riviä
     * tallennetaan transaktionissa, heitetään uusi virhe (RollbackException),
     * joka lopettaa tallennuksen eikä rivi tallennu tauluun. 
     * "cdi" -objekti käyttää hyväkseen @Transactional -annotaatiota.
     */
    private boolean testRollbackCDI() {
        boolean result = false;
        cdi.deleteAll();
        try {
            cdi.rollBack();
        } catch (RollbackException ex) {
        }
        result = cdi.countAll() == 0;
        return result;
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
