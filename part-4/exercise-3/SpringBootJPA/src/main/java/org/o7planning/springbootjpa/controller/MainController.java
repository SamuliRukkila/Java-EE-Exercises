package org.o7planning.springbootjpa.controller;

import java.util.List;

import org.o7planning.springbootjpa.dao.BankAccountDAO;
import org.o7planning.springbootjpa.exception.BankTransactionException;
import org.o7planning.springbootjpa.form.SendMoneyForm;
import org.o7planning.springbootjpa.model.BankAccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Kontrolleri, joka on yhteydessä DAO-luokan kanssa tietokantakyselyissä.
 * Käyttää hyväksi Springin UI Model -objektia, jolla voi helposti tulostaa
 * informaatiota Thymeleaf -templaateissa.
 * 
 * @Controller -annotaatio kertoo Springille, että kyseinen luokka toimii
 * kontrollerina tässä Spring MVC -sovelluksessa.
 */
@Controller
public class MainController {
  
  /**
   * Automaattinen injektointi (ja wirettäminen) tähän
   * luokkaan ilman XML-konfiguraatiota.
   */
  @Autowired
  private BankAccountDAO bad;
  
  /**
   * Kun käyttäjä saapuu root -etusivulle, kutsutaan
   * tätä GET-funktiota, joka käyttää hyväksi DAO-luokkaa, joka on 
   * yhteydessä JPA-luokkaan (=tietokantaan).
   * 
   * Kun DAO-luokka on etsinyt kaikki avoinna olevat pankkitilit, 
   * palautetaan ne tähän funktioon, joka lisää ne Springin UI Model 
   * -objektiin.
   * @param model - Springin UI Model, johon tallennetaan löydetyt pankkitilit
   * @return tekstinpätkän, jonka avulla Thymeleaf ohjaa käyttäjän oikeaan 
   *  templaattiin (/accountsPage)
   */
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String showBankAccounts(Model model) {
    List<BankAccountInfo> list = bad.listBankAccountInfo();
    model.addAttribute("accountInfos", list);
    return "accountsPage";
  }
  
  /**
   * Kun käyttäjä klikkaa navigaatiopalkissa "Send money" -nappia, 
   * kutsutaan tätä GET-funktiota, joka tekee aluksi uuden SendMoneyForm 
   * -objektin staattisesta tiedosta, joita käytetään templaattina sivulta
   * löytyvästä lomakkeesta. 
   * 
   * Tämä uusi objekti lisätään Springin UI Model -objektiin, jotta se voidaan
   * näyttää helposti Thymeleaf -templaatissa.
   * @param model - Springin UI Model, joon tallennetaan uusi objekti
   * @return tekstinpätkän, jonka avulla Thymeleaf ohjaa käyttäjän oikeaan 
   *  templaattiin (/sendMoneyPage)
   */
  @RequestMapping(value = "/sendMoney", method = RequestMethod.GET)
  public String viewSendMoneyPage(Model model) {
    SendMoneyForm form = new SendMoneyForm(1L, 2L, 700d);
    model.addAttribute("sendMoneyForm", form);
    return "sendMoneyPage";
  }
  
  /**
   * Kun käyttäjä on valinnut /sendMoneyPage -templaatissa tilit ja rahan määrän
   * mitä vaihdetaan päästään tähän POST-funktioon, mikä osaksi vie tiedon eteenpäin,
   * että rahanvaihdos aloitetaan.
   * 
   * Koska kyseessä oleva rahan lisäys sekä rahan poisto toiselta pitää onnistua molemmin
   * puolin käytämme DAO-funktiossa @Transactional -annotaatio ja se hoidetaan siinä luokassa.
   * 
   * Käyttäen tietojen säilyttämisessä SendMoneyForm -beania, viemme tarvittavat tiedot 
   * sendMoney() -funktiolle, joka hoitaa sen. Jos rahan siirtäminen ja poistaminen epäonnistuu
   * jossain vaiheessa, ja funktio pyyhkii tekevät muutokset, nappaa tämä funktio sen vastaan
   * ja lähettää takaisin virheviestin miksi rahanvaihdos epäonnistui.
   * 
   * @param model - Springin UI Model, johon tallennetaan löydetyt pankkitilit
   * @param smf - bean, jossa säilytämme rahanvaihdon ajaksi tarvittavia tietoja
   * @return tekstinpätkän, joka määrittää minkä templaatin Thymeleaf seuraavaksi lataa
   *  (/sendMoneyPage tai sivun uudelleenlataus)
   */
  @RequestMapping(value = "/sendMoney", method = RequestMethod.POST)
  public String processSendMoney(Model model, SendMoneyForm smf) {
    System.out.println("Send money: " + smf.getAmount());
    
    try {
      bad.sendMoney(smf.getFromAccountId(), smf.getToAccountId(), smf.getAmount());
    } catch (BankTransactionException e) {
      model.addAttribute("errorMessage", "Error: " + e.getMessage());
      return "/sendMoneyPage";
    }
    return "redirect:/";
  }
  
}
