/*
 Normal Java-application which only works on console. Makes a connection
 to HelloEJBWebServices -SOAP-API.
*/
package com.ejb3.webservice;
 
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;
 
public class Main {
  
  public static void main(String[] args) throws ServiceException, RemoteException {
    
    // Locate needed service
    HelloWorldJAXWSServiceLocator l = new HelloWorldJAXWSServiceLocator();
    // Tries to find the port for the upper web-service
    HelloWorldJAXWS port = (HelloWorldJAXWS)l.getPort(HelloWorldJAXWS.class);
    // Call one of the public methods in the web-service's function
    String res = port.sayHello("Jonathan the Thirdy");
    // Print the return value of upper web-function
    System.out.println(res);

  }
}
    
