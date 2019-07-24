/**
 * HelloWorldJAXWSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ejb3.webservice;

public class HelloWorldJAXWSServiceLocator extends org.apache.axis.client.Service implements com.ejb3.webservice.HelloWorldJAXWSService {

    public HelloWorldJAXWSServiceLocator() {
    }


    public HelloWorldJAXWSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public HelloWorldJAXWSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for HelloWorldJAXWSPort
    private java.lang.String HelloWorldJAXWSPort_address = "http://samuli:8080/HelloWorldJAXWSService/HelloWorldJAXWS";

    public java.lang.String getHelloWorldJAXWSPortAddress() {
        return HelloWorldJAXWSPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String HelloWorldJAXWSPortWSDDServiceName = "HelloWorldJAXWSPort";

    public java.lang.String getHelloWorldJAXWSPortWSDDServiceName() {
        return HelloWorldJAXWSPortWSDDServiceName;
    }

    public void setHelloWorldJAXWSPortWSDDServiceName(java.lang.String name) {
        HelloWorldJAXWSPortWSDDServiceName = name;
    }

    public com.ejb3.webservice.HelloWorldJAXWS getHelloWorldJAXWSPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(HelloWorldJAXWSPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getHelloWorldJAXWSPort(endpoint);
    }

    public com.ejb3.webservice.HelloWorldJAXWS getHelloWorldJAXWSPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.ejb3.webservice.HelloWorldJAXWSPortBindingStub _stub = new com.ejb3.webservice.HelloWorldJAXWSPortBindingStub(portAddress, this);
            _stub.setPortName(getHelloWorldJAXWSPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setHelloWorldJAXWSPortEndpointAddress(java.lang.String address) {
        HelloWorldJAXWSPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.ejb3.webservice.HelloWorldJAXWS.class.isAssignableFrom(serviceEndpointInterface)) {
                com.ejb3.webservice.HelloWorldJAXWSPortBindingStub _stub = new com.ejb3.webservice.HelloWorldJAXWSPortBindingStub(new java.net.URL(HelloWorldJAXWSPort_address), this);
                _stub.setPortName(getHelloWorldJAXWSPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("HelloWorldJAXWSPort".equals(inputPortName)) {
            return getHelloWorldJAXWSPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webservice.ejb3.com/", "HelloWorldJAXWSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webservice.ejb3.com/", "HelloWorldJAXWSPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("HelloWorldJAXWSPort".equals(portName)) {
            setHelloWorldJAXWSPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
