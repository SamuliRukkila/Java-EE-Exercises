package jpaclasses;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// @Entity -annotation defines an entity in JPA. It'll tell JPA
// that this class is in contact with JPA-SQL -connections.
@Entity
// Which table this entity-class targets
@Table(name = "book")
// Ready-made queries for use
@NamedQueries({ 
    @NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b"),
		@NamedQuery(name = "Book.findById", query = "SELECT b FROM Book b WHERE b.book_id = :book_id"),
		@NamedQuery(name = "Book.findByTitle", query = "SELECT b FROM Book b WHERE b.title = :title"),
		@NamedQuery(name = "Book.findByAuthor", query = "SELECT b FROM Book b WHERE b.author = :author"),
    @NamedQuery(name = "Book.findByBookGroup", query = "SELECT b FROM Book b WHERE b.bookgroup = :id")
})
public class Book implements Serializable {
  
	private static final long serialVersionUID = 1L;
	
	
	/* COLUMNS */
	
	// Main column in table
	@Id
	// Auto-incremented value
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	// Column's name in MySQL-table
	@Column(name = "book_id")
	private Integer book_id;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "title")
	private String title;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "author")
	private String author;
	
	// Reference to another table - BookGroup
	@ManyToOne(optional = true)
	@JoinColumn(name = "bookgroup_id", referencedColumnName = "bookgroup_id")
	private BookGroup bookgroup;
	
	
	/* CONSTRUCTORS */
	
	public Book() {
	}

	public Book(Integer id) {
		this.book_id = id;
	}


  public Book(Integer id, String title, String author) {
		this.book_id = id;
		this.title = title;
		this.author = author;
	}
  
  
  /* SETTERS AND GETTERS */
  
	public Integer getId() {
		return book_id;
	}

	public void setId(Integer id) {
		this.book_id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public BookGroup getBookGroup() {
	  return bookgroup;
	}
	
	public void setBookGroup(BookGroup bg) {
	  this.bookgroup = bg;
	}

	@Override
	public String toString() {
		return "entityClasses.Book[ id=" + book_id + " ]";
	}

}
