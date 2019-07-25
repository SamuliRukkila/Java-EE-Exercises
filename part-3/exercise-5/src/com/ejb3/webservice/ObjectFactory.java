
package com.ejb3.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ejb3.webservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CreateBook_QNAME = new QName("http://webservice.ejb3.com/", "createBook");
    private final static QName _SayHello_QNAME = new QName("http://webservice.ejb3.com/", "sayHello");
    private final static QName _GetBooks_QNAME = new QName("http://webservice.ejb3.com/", "getBooks");
    private final static QName _SayHelloResponse_QNAME = new QName("http://webservice.ejb3.com/", "sayHelloResponse");
    private final static QName _CreateBookResponse_QNAME = new QName("http://webservice.ejb3.com/", "createBookResponse");
    private final static QName _GetBooksResponse_QNAME = new QName("http://webservice.ejb3.com/", "getBooksResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ejb3.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreateBook }
     * 
     */
    public CreateBook createCreateBook() {
        return new CreateBook();
    }

    /**
     * Create an instance of {@link GetBooks }
     * 
     */
    public GetBooks createGetBooks() {
        return new GetBooks();
    }

    /**
     * Create an instance of {@link SayHello }
     * 
     */
    public SayHello createSayHello() {
        return new SayHello();
    }

    /**
     * Create an instance of {@link SayHelloResponse }
     * 
     */
    public SayHelloResponse createSayHelloResponse() {
        return new SayHelloResponse();
    }

    /**
     * Create an instance of {@link CreateBookResponse }
     * 
     */
    public CreateBookResponse createCreateBookResponse() {
        return new CreateBookResponse();
    }

    /**
     * Create an instance of {@link GetBooksResponse }
     * 
     */
    public GetBooksResponse createGetBooksResponse() {
        return new GetBooksResponse();
    }

    /**
     * Create an instance of {@link Book }
     * 
     */
    public Book createBook() {
        return new Book();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateBook }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.ejb3.com/", name = "createBook")
    public JAXBElement<CreateBook> createCreateBook(CreateBook value) {
        return new JAXBElement<CreateBook>(_CreateBook_QNAME, CreateBook.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHello }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.ejb3.com/", name = "sayHello")
    public JAXBElement<SayHello> createSayHello(SayHello value) {
        return new JAXBElement<SayHello>(_SayHello_QNAME, SayHello.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBooks }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.ejb3.com/", name = "getBooks")
    public JAXBElement<GetBooks> createGetBooks(GetBooks value) {
        return new JAXBElement<GetBooks>(_GetBooks_QNAME, GetBooks.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHelloResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.ejb3.com/", name = "sayHelloResponse")
    public JAXBElement<SayHelloResponse> createSayHelloResponse(SayHelloResponse value) {
        return new JAXBElement<SayHelloResponse>(_SayHelloResponse_QNAME, SayHelloResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateBookResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.ejb3.com/", name = "createBookResponse")
    public JAXBElement<CreateBookResponse> createCreateBookResponse(CreateBookResponse value) {
        return new JAXBElement<CreateBookResponse>(_CreateBookResponse_QNAME, CreateBookResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBooksResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.ejb3.com/", name = "getBooksResponse")
    public JAXBElement<GetBooksResponse> createGetBooksResponse(GetBooksResponse value) {
        return new JAXBElement<GetBooksResponse>(_GetBooksResponse_QNAME, GetBooksResponse.class, null, value);
    }

}
