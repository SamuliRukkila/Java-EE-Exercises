package com.ejb3.webservice;

public class HelloWorldJAXWSProxy implements com.ejb3.webservice.HelloWorldJAXWS {
  private String _endpoint = null;
  private com.ejb3.webservice.HelloWorldJAXWS helloWorldJAXWS = null;
  
  public HelloWorldJAXWSProxy() {
    _initHelloWorldJAXWSProxy();
  }
  
  public HelloWorldJAXWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initHelloWorldJAXWSProxy();
  }
  
  private void _initHelloWorldJAXWSProxy() {
    try {
      helloWorldJAXWS = (new com.ejb3.webservice.HelloWorldJAXWSServiceLocator()).getHelloWorldJAXWSPort();
      if (helloWorldJAXWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)helloWorldJAXWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)helloWorldJAXWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (helloWorldJAXWS != null)
      ((javax.xml.rpc.Stub)helloWorldJAXWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.ejb3.webservice.HelloWorldJAXWS getHelloWorldJAXWS() {
    if (helloWorldJAXWS == null)
      _initHelloWorldJAXWSProxy();
    return helloWorldJAXWS;
  }
  
  public java.lang.String sayHello(java.lang.String name) throws java.rmi.RemoteException{
    if (helloWorldJAXWS == null)
      _initHelloWorldJAXWSProxy();
    return helloWorldJAXWS.sayHello(name);
  }
  
  
}