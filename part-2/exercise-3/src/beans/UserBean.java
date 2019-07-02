/*
 * Tähän Beaniin on injektoitu DbBean.
 * DbBeanista saadaan viesti sen perusteella
 * onko käyttäjän antama username jo kannassa vai ei.
 *
 */
package beans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "userBean")
@SessionScoped
public class UserBean implements Serializable {

  private static final long serialVersionUID = 1L;

  public UserBean() {
  }

  private String username;
  private String password;
  private String ajaxmessage;
  // CDI -injektio tapahtuu tässä:
  @Inject
  private DbBean dbBean;

  public String getAjaxmessage() {
    // UserBeaniin käyttöliittymästä tullut username
    // annetaan DbBeanin getMessage -metodille joka palauttaa viestin
    ajaxmessage = dbBean.getMessage(username);

    return ajaxmessage;
  }

  public void setAjaxmessage(String ajaxmessage) {
    this.ajaxmessage = ajaxmessage;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

}