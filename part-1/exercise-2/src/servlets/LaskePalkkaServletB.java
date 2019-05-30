package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LaskePalkkaServletB
 */
@WebServlet(description = "Servlet which'll count gross pay and take-home pay via user inputs. "
		+ "After the calculation, these values will be printed to user inside servlet.", urlPatterns = { "/LaskePalkkaServletB" })
public class LaskePalkkaServletB extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// Make sure the character encoding is UTF-8
		response.setContentType("text/html;charset=UTF-8");
		// Remove cache
		response.setHeader("pragma", "no-cache");
		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>Palkanlaskenta</title></head>");
		
		// Boolean value representing if convert was successful
		boolean success = false;
		
		// Get all user input's values and assign them to local variables
		String hoursReq = request.getParameter("hours");
		String salaryByHourReq = request.getParameter("salaryByHour");
		String taxReq = request.getParameter("tax");
		
		double hours = 0.0;
		double salaryByHour = 0.0;
		double tax = 0.0;
		
		// Try to convert all string-variables to decimal numbers
		try {
			hours = Double.parseDouble(hoursReq);
			salaryByHour = Double.parseDouble(salaryByHourReq);
			tax = Double.parseDouble(taxReq);
			success = true;
		} catch (NullPointerException npe) {
			out.println("Kaikkia lukuja ei voitu hakea. Jätitkö kohdan tyhjäksi?");
		} catch (Exception e) {
			out.println("Virhe palkkaa laskiessa");
		}
		
		// If convert was successful
		if (success) {
			double salary = salaryByHour * hours;
			out.println("<h3>Bruttopalkka:</h3>");
			out.println(salary + " €");
			out.println("<h3>Nettopalkka:</h3>");
			out.println(salary / (100 / (100 - tax)) + " €");
		}
		
		out.println("</body></html>");
		out.println("<p><a href='laskepalkka-B.html'>Laske uudestaan</a></p>");
		out.close();
	}

}
