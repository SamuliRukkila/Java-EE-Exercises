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

  private String username;
  private String password;
  private String ajaxmessage;
  private String notice;
  
  public UserBean() {
    this.ajaxmessage = "";
    this.notice = "";
  }

  // Injection to DBBean - meaning we can use the functions and 
  // variables in DbBean.
  @Inject
  private DbBean dbBean;
  
  /**
   * Sends credentials (username + password) forward to DBBean which'll
   * insert those values into table. If the insertion is successful ->
   * username, password + ajaxmessage will be emptied for another insertion.
   * 
   * If the insertion fails, a error message will be printed for user.
   * @return notice - message which tell if adding new user was successful or not
   */
  public String sendCredentials() {
    notice = dbBean.createCredentials(username, password);
    if (notice == "Tunnukset luotu") {
      username = "";
      password = "";
      ajaxmessage = "";
    }
    return notice;
  }
  
  /**
   * Using DbBean, this function will determine if wanted username is taken already.
   * @return ajaxmessage - telling if username is taken already
   */
  public String getAjaxmessage() {
    if (username != null) {
      if (!username.isEmpty()) {
        notice = "";
        ajaxmessage = dbBean.getMessage(username);
      }
    } else {
      ajaxmessage = "";
    }
    return ajaxmessage;
  }

  /**
   * Set new ajaxmessage
   * @param ajaxmessage New ajaxmessage
   */
  public void setAjaxmessage(String ajaxmessage) {
    this.ajaxmessage = ajaxmessage;
  }
  
  /**
   * Get notice -message
   * @return notice
   */
  public String getNotice() {
    return this.notice;
  }
  /**
   * Sets new notice -message
   * @param new notice message
   */
  public void setNotice(String notice) {
    this.notice = notice;
  }
  
  /**
   * Get password -message
   * @return password
   */
  public String getPassword() {
    return password;
  }
  /**
   * Sets new password
   * @param new password
   */
  public void setPassword(String password) {
    this.password = password;
  }
  
  /**
   * Get username
   * @return username
   */
  public String getUsername() {
    return username;
  }
  /**
   * Sets new username
   * @param new username
   */
  public void setUsername(String username) {
    this.username = username;
  }

}