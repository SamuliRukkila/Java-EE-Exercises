package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LaskePalkkaServletC
 */
@WebServlet(description = "Counts payments and returns them back to JSP.", 
	urlPatterns = { "/LaskePalkkaServletC" })
public class LaskePalkkaServletC extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void processRequest(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
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
			request.setAttribute("error", "Virhe. Täytitkö kaikki kohdat?");
		} catch (Exception e) {
			request.setAttribute("error", "Virhe palkkaa laskiessa");
		}
		
		// If convert was successful
		if (success) {
			double salary = salaryByHour * hours;
			double grossSalary = salary / (100 / (100 - tax));
			// Set salary-variables to request attributes which'll be returned back
			request.setAttribute("salary", salary);
			request.setAttribute("grossSalary", grossSalary);
		}
		// RequestDispacther -object -> can be used to send data back to JSP
		RequestDispatcher rd = getServletConfig().getServletContext()
				.getRequestDispatcher("/palkkalomake-C.jsp");
		rd.forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
