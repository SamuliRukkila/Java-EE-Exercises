/*
 * DispatcherServlet on palveluservletti joka
 * suorittaa palveluja sovellukselle. Tässä tapauksessa
 * palveluservletti ottaa yhteyden kantaan, suorittaa tietokantahaun ja tallentaa tuloksen
 * papu-olioon.
 *
 * Sessioon tallennetun papu-olion avulla välitetään kyselyn tulos käyttöliittymään
 * asiakkaat.jsp-sivulle. Näin saadaan aikaan paremmin MVC-mallia noudattava sovellus.
 *
 */

package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
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

@WebServlet(name = "DispatcherServlet", urlPatterns = { "/DispatcherServlet" })
public class DispatcherServlet extends HttpServlet {

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
    // yhteys kantaan tietokantaluokan avulla.
    try {
      conn = classes.SQL.openConnection();
    } catch (Exception e) {
      System.out.println("Kantaan ei saada yhteyttä " + e);
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

    PrintWriter out = response.getWriter();

    // Otetaan istunto haltuun
    HttpSession session = request.getSession(false);
    // Luodaan papu johon vastaus sijoitetaan
    CustomerBean papu = new CustomerBean();

    // Käsitellään lomake
    // Tehdään tietokantahaku ja tallennetaan tiedot papuun
    String em = request.getParameter("email");
    System.out.println(em);
    try {
      // luodaan Statement-olio jonka avulla voidaan suorittaa sql-lause
      Statement stmt = conn.createStatement();

      // Suoritetaan sql, otetaan tulosjoukko kiinni.
      ResultSet rs = stmt.executeQuery("SELECT * FROM asiakkaat WHERE email = '" + em + "'");

      if (rs.next()) {
        do {
          id = rs.getString("id");
          nimi = rs.getString("nimi");
          osoite = rs.getString("osoite");
          puhelin = rs.getString("puhelin");
          email = rs.getString("email");
          salasana = rs.getString("salasana");
        } while (rs.next());
      } else {
        id = null;
        nimi = null;
        osoite = null;
        puhelin = null;
        email = null;
        salasana = null;
      }

    } catch (Exception e) {
      out.println("Tuli poikkeus!");
    }

    // tiedot papuun
    papu.setId(id);
    papu.setNimi(nimi);
    papu.setOsoite(osoite);
    papu.setPuhelin(puhelin);
    papu.setEmail(email);
    papu.setSalasana(salasana);

    // papu sessioon
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
