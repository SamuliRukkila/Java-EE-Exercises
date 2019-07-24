/**
 * HelloWorldJAXWSService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ejb3.webservice;

public interface HelloWorldJAXWSService extends javax.xml.rpc.Service {
    public java.lang.String getHelloWorldJAXWSPortAddress();

    public com.ejb3.webservice.HelloWorldJAXWS getHelloWorldJAXWSPort() throws javax.xml.rpc.ServiceException;

    public com.ejb3.webservice.HelloWorldJAXWS getHelloWorldJAXWSPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
