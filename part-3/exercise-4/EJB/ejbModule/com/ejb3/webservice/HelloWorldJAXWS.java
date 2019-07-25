/*
 Main file of the EJB-project - Session Bean class. We'll
 expose this EJB-class to a web service via annotation @WebService to create a SOAP-API.
 All the public methods in this web-service will be declared in the class. It'll
 be using JAX-WS services so the data will be transferred via XML. 
*/

package com.ejb3.webservice;
 

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
 
/**
 * Web-service (EJB) class (Session Bean Class). Will be used to get SOAP (XML) -
 * requests from the client. It's @Stateless so it's state won't be sessioned.
 * Class is also @LocalBean, meaning it has no interface -view
 * 
 * @author samuli
 */
@WebService
@Stateless
@LocalBean
public class HelloWorldJAXWS {
  
  // Inject book-service to this web-service using @EJB
  @EJB private BookService bs;
  
  public HelloWorldJAXWS() {}

  
  /**
   * Web-method which'll return a Hello -message back to the user
   * with it's name.
   * @param name - Name which'll be added to the message
   * @return The hello -message
   */
  @WebMethod
  public String sayHello(@WebParam(name = "name") String name) {
    return "Hello " + name + "!";
  }
  
  
  /**
   * Web-method which'll return all the available books from the 
   * fake-database. It'll use BookService's function to fetch all the
   * books. Those books will then be returned to the caller.
   * @return All the found books
   */
  @WebMethod
  public List<Book> getBooks() {
    List<Book> books = bs.getAllBooks();
    return books;
  }
  
  
  /**
   * Web-method which'll add a new book to the list of books. New book
   * will arrive via @WebParam. It'll then use BookService's function to 
   * save the new book to a fake-database. Added book will then be returned.
   * @param book
   * @return The new added book
   */
  @WebMethod
  public Book createBook(@WebParam(name = "book") Book book) {
    Book savedBook = bs.saveBook(book);
    return savedBook;
  }
}
