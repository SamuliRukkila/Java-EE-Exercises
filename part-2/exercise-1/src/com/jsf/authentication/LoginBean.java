package com.jsf.authentication;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;


/**
 * @Named Papu, joka toimii yhteydessä JSF:n kanssa. Tämän pavun
 * avulla voimme välittää dataa Facelettiin. Uudempi @Named papu,
 * toimii erilailla siinä mielessä, että sen hallinta on serverin
 * vastuulla (tässä projektissa Payara); tämän takia emme tarvi
 * erillistä konfiguraatiotiedostoa = faces-config.xml. Papumme 
 * toimii siis välittäjänä facelettien välillä; vastaanottaa dataa eri
 * faceleilta sekä lähettää tarvitsiville faceteille dataa.
 * 
 * Beanilla pitää myös olla setterit & getterit niille attribuuteille mitä halutaan käyttää pavun
 * ulkopuolella (ei tarpeellista, mutta muuten melkeinpä hyödyttömiä).
 * 
 * Nimetylle pavulla on myös annettu @RequestScoped -attribuutti, mikä tarkoittaa, että papu on aina
 * näkyvissä aina sille tehtäessä pyyntö ja se lopettaa itsensä pyynnön jälkeen.
 * 
 * 
 * Miten autentikaatio sovelluksessa tapahtuu?
 * -------------------------------------------
 * 
 * Tällä pavulla on tarvittavat muuttujat sekä funktiot autentikoitumiseen.
 * Kun käyttäjä antaa ktunnuksen + salasanan ja painaa "submit" -nappia pääsivulla,
 * toteutetaan tämän luokan validateUserLogin() -funktio, joka katsoo, että 
 * kredentiaalit vastaavat valmiiksi kovakoodattuja tietoja. Jos ne vastaavat, palautetaan
 * stringi "success". Takaisin index.xhtml:ssä "submit" -nappulan kohdalla on action -attribuutti,
 * jonka avulla käyttäjä voidaan viedä haluamalle sivulle.  Samanlainen
 * tapahtuma tapahtuu myös jos tiedot ovat vääriä "failure" -> failure.xhtml.
 * 
 * @author samuli
 */

@Named(value = "loginBean") 
@RequestScoped
public class LoginBean {
  
  private String userName;
  private String password;
  
  /**
   * Get's username
   * 
   * @return username
   */
  public String getUserName() {
    return this.userName;
  }
  
  /**
   * Sets new username
   * 
   * @param userName New username
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }
  
  /**
   * Get's password
   * 
   * @return password
   */
  public String getPassword() {
    return this.password;
  }
  
  /**
   * Sets new password
   * 
   * @param password New password
   */
  public void setPassword(String password) {
    this.password = password;
  }
  
  /**
   * Validates user's credentials
   * 
   * Function which will validate user's credentials comparing them
   * to hard-coded strings seen in function. If both username and password
   * are correct, a string called "success" will be returned; otherwise "failure".
   * 
   * Returned value will be used to redirect user to corresponding site =>
   * "success" -> success.xtml | "failure" -> failure.xhtml 
   * 
   */
  public String validateUserLogin() {
    String navResult = "";
    System.out.println("Entered username is = " + userName + 
        ", password is = " + password);
    if (userName.equalsIgnoreCase("123") && password.equals("123")) {
      navResult = "success";
    } else {
      navResult = "failure";
    }
    return navResult;
  }
}