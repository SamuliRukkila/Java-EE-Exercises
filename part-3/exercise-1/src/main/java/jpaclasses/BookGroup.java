package jpaclasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "bookgroup")
@NamedQueries({
  @NamedQuery(name = "BookGroup.findAll", query = "SELECT bg FROM BookGroup bg"),
  @NamedQuery(name = "BookGroup.findAndCount", query = "SELECT bg.name, COUNT(b) amount FROM BookGroup bg "
      + "LEFT OUTER JOIN bg.books b GROUP BY bg.name ORDER BY amount DESC")
})
public class BookGroup implements Serializable {

  private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "bookgroup_id")
  private Integer bookgroup_id;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 50)
  @Column(name = "name")
  private String name;
  
  @OneToMany(mappedBy = "bookgroup")
  private List<Book> books = new ArrayList<>();
  
  public List<Book> getBooks() {
    return books;
  }

  public void setBooks(List<Book> books) {
    this.books = books;
  }

  public BookGroup() {}
  
  public BookGroup(Integer id) {
    this.bookgroup_id = id;
  }
  
  public BookGroup(Integer id, String name) {
    this.bookgroup_id = id;
    this.name = name;
  }
  
  // Set & Get for ID
  public Integer getId() {
    return bookgroup_id;
  }
  public void setId(Integer id) {
    this.bookgroup_id = id;
  }
  
  // Set & Get for name
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
}
