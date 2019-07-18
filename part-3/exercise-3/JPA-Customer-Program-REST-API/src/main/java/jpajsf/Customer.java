/*
 Entity-class created from the customer(asiakkaat) -table. It represents the
 struct of that table. This class will contain needed information from the table
 in order to do ORM -queries (without writing SQL-queries).
*/

package jpajsf;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;


@Entity
@Table(name = "asiakkaat")
@NamedQueries({
  @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c")
})
public class Customer implements Serializable {

  private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  private Integer id;
  
  @Basic(optional = true)
  @Size(min = 1, max = 50)
  @Column(name = "nimi", nullable = true)
  private String nimi;
  
  @Basic(optional = true)
  @Size(min = 1, max = 100)
  @Column(name = "osoite", nullable = true)
  private String osoite;
  
  @Basic(optional = true)
  @Size(min = 1, max = 15)
  @Column(name = "puhelin", nullable = true)
  private String puhelin;
  
  @Basic(optional = true)
  @Size(min = 1, max = 50)
  @Column(name = "email", nullable = true)
  private String email;
  
  @Basic(optional = true)
  @Size(min = 1, max = 15)
  @Column(name = "salasana", nullable = true)
  private String salasana;

  
  /* GETTERS AND SETTERS FOR VARIABLES */
  
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNimi() {
    return nimi;
  }

  public void setNimi(String nimi) {
    this.nimi = nimi;
  }

  public String getOsoite() {
    return osoite;
  }

  public void setOsoite(String osoite) {
    this.osoite = osoite;
  }

  public String getPuhelin() {
    return puhelin;
  }

  public void setPuhelin(String puhelin) {
    this.puhelin = puhelin;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSalasana() {
    return salasana;
  }

  public void setSalasana(String salasana) {
    this.salasana = salasana;
  }

}
