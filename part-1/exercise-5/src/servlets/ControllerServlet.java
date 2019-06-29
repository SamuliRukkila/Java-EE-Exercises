/*
 * ControllerServlet ohjailee viestejä käyttöliittymältä
 * palveluja suorittaville tiedostoille (palveluservleteille)
 *
 */

package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import classes.CustomerBean;

@WebServlet(name = "ControllerServlet", urlPatterns = { "/ControllerServlet" })
public class ControllerServlet extends HttpServlet {

  CustomerBean papu = new CustomerBean();
  private static final long serialVersionUID = 1L;

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // luodaan uusi sessio jos tullaan sivulle ekan kerran
    HttpSession session = request.getSession(true);
    session.setAttribute("avain", "arvo");// Turha homma, mutta tehdään jotta ei tule huomautusta
    System.out.println(request.getParameter("modify"));
    
    // According to parameters, user's submit will be forwarded to corresponding servlet
    RequestDispatcher rd = request.getParameter("updateValues") != null ? 
      request.getRequestDispatcher("/UpdateCustomerServlet") :
      request.getRequestDispatcher("/DispatcherServlet");
    rd.include(request, response);

    // Kontrolli palaa tähän kun DispatcherServlet on tehnyt työnsä.
    if (request.getParameter("submit") != null || request.getParameter("updateValues") != null) {
      request.getRequestDispatcher("result.jsp").forward(request, response);
    } else {
      request.getRequestDispatcher("modify.jsp").forward(request, response);
    }

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
