package jpa_rest_api;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Customer-API which includes REST-API. It'll use JSON as a type to pass data.
 * Request-types (Get, Post, Delete) will be created using JAX-RS annotation.
 * @path (/customersapi/customer/)
 * @author samuli
 */
@ApplicationPath("customersapi")
@Path("customer")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerAPI extends Application {
  
  // Inject Customer-Service to REST-API
  @EJB private CustomerService cs;
  
  
  /**
   * Checks that user's given ID is a number.
   * @example [1] => OK | [foo] => ERORR
   * @param param - Parameter which'll be checked
   * @returns true if user parameter is valid; false if not
   */
  public static boolean checkParameter(String param) {
    try {
      Integer.parseInt(param);
      return true;
    } catch(NumberFormatException nfe) {
      return false;
    }
  }
  
  
  /**
   * HTTP GET-method to fetch all the available customers. These
   * customers will be fetched from injected Customer-service. These
   * values will then be returned back to the caller. 404 message will
   * be returned if no customers were found.
   * @returns Customers in a object-list; string message if none were found
   */
  @GET
  public Response getCustomers() {
    List<Customer> customers = cs.getCustomers();
    // If no customers were found
    if (customers.size() == 0) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity("Asiakkaita ei löytynyt").build();
    }
    GenericEntity<List<Customer>> list = new GenericEntity<List<Customer>>(customers){};
    return Response.ok(list).build();
  }
  
  
  /**
   * HTTP GET-method to fetch one customer by it's ID. Customer will
   * be fetched from injected Customer-service. If user is found, it'll
   * returned back to this function. Otherwise an error-message will be
   * returned.
   * @param id - Customer's ID
   * @returns Customer as an object; error-message if user weren't found
   */
  @GET
  @Path("{id:}")
  public Response getCustomer(@PathParam("id") String id) {
    // Check that parameter is valid
    if (checkParameter(id) == false) {
      return Response.status(400)
          .entity("Et antanut numeroa: [" + id + "]")
          .build();
    }
    Customer customer = cs.getCustomer((String) id);
    if (customer == null) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity("Asiakasta ei löytynyt ID:llä: " + id)
          .build();
    }
    GenericEntity<Customer> cus = new GenericEntity<Customer>(customer){};
    return Response.ok(cus).build();
  }
  
  
  /**
   * HTTP DELETE-method to delete one customer by it's ID. Customer will be 
   * deleted in injected Customer-service. If user weren't found or there 
   * were error deleting the customer, a proper error-message will be sent
   * instead.
   * @param id - Customer's ID
   * @returns string-message telling if deletion was successful/unsuccessful
   */
  @DELETE
  @Path("{id:}")
  public Response deleteCustomer(@PathParam("id") String id) {
    // Validate parameter
    if (checkParameter(id) == false) {
      return Response.status(400)
          .entity("Et antanut numeroa: [" + id + "]")
          .build();
    }
    boolean deleted = cs.deleteCustomer(id);
    if (deleted == false) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity("Asiakasta ei löytynyt ID:llä: " + id)
          .build();
    }
    return Response.status(Response.Status.OK)
        .entity("Asiakas ID:llä: [" + id + "] poistettiin")
        .build();
  }
  
  
  /**
   * HTTP POST-method to create a new customer. The creation will be done
   * in injected Customer-service. Created customer -object will be returned
   * if creation is successful. If creation is unsuccessful, an error-message
   * will be returned instead.
   * @param c - Customer -object
   * @returns Customer -object if created successfully; error-message if
   *    failed to create the customer
   */
  @POST
  public Response createCustomer(Customer c) {
    String error = cs.createCustomer(c);
    if (error != null) {
      return Response.status(500).entity(error).build();
    }
    return Response.status(Response.Status.OK)
        .entity(c).build();
  }
  
}
