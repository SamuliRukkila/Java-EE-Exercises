package fish.payara.examples.javaee.facade.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fish.payara.examples.javaee.facade.ejb.BankServiceFacade;

@WebServlet("/FacadeTestServlet")
public class FacadeTestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String result;

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
