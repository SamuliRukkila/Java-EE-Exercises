/*
 Other files in this dynamic web project were created via wsimport -utility-command
 called client stubs. Via these stubs we can create SOAP-API calls straight to 
 the SOAP-server.
*/

package com.ejb3.webservice;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// http://localhost:8080/WebClient-SOAP-API/CustomerServlet
@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  public CustomerServlet() {
    super();
  }
  
  /**
   * Prints all the customers from fake-database in SOAP-API (Exercise 4.4). This GET-function will use ready-made
   * client stubs to connect to the server. In this function we can simply create objects out of those class-stubs and
   * then create function-calls straight to the API.
   * 
   * In this example we'll print all the available customers into a table.
   * 
   * @Author SaRu
   */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  
	  // Set response encoding to UTF-8
	  response.setCharacterEncoding("UTF-8");
	  PrintWriter out = response.getWriter();
	  
	  // Create new object from the service-client
	  HelloWorldJAXWSService service = new HelloWorldJAXWSService();
	  // Get new port from the @WebService, with this port we can 
	  // make SOAP-API calls.
    HelloWorldJAXWS port = service.getHelloWorldJAXWSPort();
    // Create a function from the port to fetch all the books and 
    // put them into a local list-variable.
    List<Book> books = port.getBooks();
    
    // Print all the available books via for-loop into a table.
    out.println("<html>");
      out.println("<head>");
        out.println("<meta charset='UTF-8'>");
      out.println("</head>");
      out.println("<body>");
        out.println("<table>");
          out.println("<tr>");
            out.println("<th>ID</th><th>Kirjoittaja</th><th>Teos</th>");
          out.println("</tr>");
          for (Book b : books) {
            out.println("<tr>");
            out.println("<td>" + (b.getId() != null ? b.getId() : "--") + "</td>");
            out.println("<td>" + (b.getAuthor() != null ? b.getAuthor() : "--") + "</td>");
            out.println("<td>" + (b.getTitle() != null ? b.getTitle() : "--") + "</td>");
            out.println("</tr>");
          }
        out.println("</table>");
      out.println("</body>");
    out.println("</html>");
	}


}
