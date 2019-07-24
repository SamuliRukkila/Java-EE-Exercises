package com.ejb3.webservice;
 
import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;
 
public class Main {
  
  public static void main(String[] args) throws ServiceException, RemoteException {
    
    HelloWorldJAXWSServiceLocator l = new HelloWorldJAXWSServiceLocator();
    HelloWorldJAXWS port = (HelloWorldJAXWS)l.getPort(HelloWorldJAXWS.class);
    String res = port.sayHello("Jonathan the Third");
    System.out.println(res);

  }
}
    
