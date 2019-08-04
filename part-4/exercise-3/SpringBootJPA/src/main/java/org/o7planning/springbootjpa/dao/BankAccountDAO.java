package org.o7planning.springbootjpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.o7planning.springbootjpa.entity.BankAccount;
import org.o7planning.springbootjpa.exception.BankTransactionException;
import org.o7planning.springbootjpa.model.BankAccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * DAO (Data Access Object) -luokat vastaavat 
 * CRUD -toimintojen tarjoamisesta tietokanta-taulukoihin.
 * 
 * Springin @Repository -annotaatiota käytetään osoittamaan,
 * että luokka tarjoaa mekanismin objektien varastointiin,
 * hakuun, päivitykseen ja poistamiseen. Ne ovat erikoistuneita
 * @Component -annotaatioita.
 */
@Repository
public class BankAccountDAO {
  
  /**
   * EntityManager -rajapinta hallitsee entiteeti-ilmentymien 
   * elinkaarta. JPA:ssa EntityManager -rajapintaa käytetään
   * sovellusten hallitsemiseen ja etsimän entiteettejä 
   * relaatiotietokannasta.
   * 
   * @Autowired -annotaatiota sinun ei tarvitse tehdä
   * bean-wiringiä XML-tiedostossa tmv vaan se hoitaa
   * ja löytää missä luokka tarvitsee injektoinnin ja 
   * hoitaa sen sinun puolestasi.
   */
  @Autowired
  private EntityManager em;
  
  public BankAccountDAO() {}
  
  /**
   * Hakee yhden pankkitilin ID:n perusteella.
   * @param id - Pankkitilin ID
   * @return Palautuneen pankkitilin
   */
  public BankAccount findById(Long id) {
    return this.em.find(BankAccount.class, id);
  }
  
  /**
   * Hakee kaikki löydetyt pankkitilit tietokannasta. 
   * Palautuneet rivit laitetaan väliaikaisesti BankAccountInfo 
   * -beaniin, ennenkuin kuin tulokset palautetaan funktiota 
   * kutsuvalle funktiolle (tässä tapauksessa @Controller).
   * 
   * @return Kaikki pankkitilit (List -muodossa)
   */
  @SuppressWarnings("unchecked")
  public List<BankAccountInfo> listBankAccountInfo() {
    String sql = "SELECT NEW " + BankAccountInfo.class.getName()
        + "(e.id,e.fullName,e.balance)"
        + "from " + BankAccount.class.getName() + " e";
    Query query = em.createQuery(sql, BankAccountInfo.class);
    return query.getResultList();
  }
  
  /**
   * Funktio, missä lisätään tai poistetaan (jos amount -parametri on miinusluku)
   * pankkitililtä rahaa. Pankkitili etsitään parametrin ID:n avulla. Jos rahan
   * miinustamisen aikana raha menee miinuksen puolelle, heitetään Exception, jonka
   * kutsuva @Transactional -funktio hoitaa ja pyyhkii tehdyt muutokset. 
   * 
   * Tämäkin on @Transcational -annotaation omaava funktio, mitä kutsutaan
   * toisesta @Transactional -funktiosta. Parametrin arvo Propagation.MANDATORY
   * kertoo, että metodi pitää ajaa transactionin sisällä (jos ei ajeta tulee virhe).
   * 
   * @param id - Pankkitilin ID, jolle/jolta lisätään/poistetaan rahaa
   * @param amount - Määrä mikä lisätään/poistetaan
   * @throws BankTransactionException - Jos rahansiirron aikana tulee virhe
   */
  @Transactional(propagation = Propagation.MANDATORY)
  public void addAmount(Long id, double amount) throws BankTransactionException {
    BankAccount acc = this.findById(id);
    if (acc == null) {
      throw new BankTransactionException("Account not found " + id);
    }
    double newBalance = acc.getBalance() + amount;
    if (acc.getBalance() + amount < 0) {
      throw new BankTransactionException(
          "The money in the account " + id + " is not enough (" 
               + acc.getBalance() + ")");
    }
    acc.setBalance(newBalance);
  }
  
  /**
   * Funktiossa koitetaan siirtää rahaa tililtä toiselle käyttäen toista
   * funktiota itse siirtoon. Jos rahan siirto epäonnistuu, ja toinen osapuolista
   * saa/menettää rahaa ilman, että toinen saa/menettää vastaavaa määrää 
   * käytämme Springin @Transactional -annotaatiota tähän tarkoitukseen. Joten
   * jos toinen kutsuttavista funktiosta epäonnistuu, palautuu se takaisin 
   * aikasempaan tilaan ja pyyhkii rahanvaihdon pois.
   * 
   * Kun luokka julistaa @Transactional -annotaation itsessään tai funktiossa,
   * Spring luo välityspalvelimen (proxyn), joka toteuttaa saman rajapinnan 
   * kuin luokka. Proxy tarjoaa tavan Springille injektoida käyttäytymismallit
   * ennen, jälkeen tai sen metodi-kutsun aikana. Joten kun @Transactional 
   * -annotaatiota kutsutaan koodista, Springin TransactionInterceptor -funktiota
   * kutsutaan ensin proxyn objektista, joka aloittaa transaktionin koodin, ja 
   * lopuksi kutsuu itse luokan (beanin) funktiota. Kun kutsuminen päättyy,
   * TransactionInterceptor committaa tai ajaa takaisin tapahtuman.
   * 
   *  - propagation -parametrissa kohdennamme, että joka transaktion -tapahtumassa
   *      Spring luo uuden TransactionInterceptor -proxyn tapahtumalle, eikä käytä
   *      vanhoja proxyjä hyväkseen
   *  - rollBackFor -parametri kertoo mihin virhe-luokkaan palaudutaan, jos transaktionin
   *      aikana tulee virhe eikä sitä voida committaa
   * 
   * @param fromAccountId - Pankkitilin ID, jolta koitetaan ottaa rahaa
   * @param toAccountId - Pankkitilin ID, jolle koitetaan antaa rahaa
   * @param amount - Määrä, mikä koitetaan siirtää
   * @throws BankTransactionException - Jos rahasumman vaihdon aikana tulee virhe
   */
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
  public void sendMoney(Long fromAccountId, Long toAccountId, double amount) throws BankTransactionException {
    addAmount(toAccountId, amount);
    addAmount(fromAccountId, -amount);
  }
}
