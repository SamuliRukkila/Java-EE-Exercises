package com.tutorial.servlet.example;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetSetExampleServlet
 */
@WebServlet(description = "This is an example servlet which'll be used for tutorial explaining GET and SET.", urlPatterns = { "/getset" })
public class GetSetExampleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetSetExampleServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("mySecretMessage", "I'm a request geek!");
		request.getSession().setAttribute("mySecretMessage", "I'm a session geek!");
    request.getServletContext().setAttribute("mySecretMessage", "I'm a context geek!");
    request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}
