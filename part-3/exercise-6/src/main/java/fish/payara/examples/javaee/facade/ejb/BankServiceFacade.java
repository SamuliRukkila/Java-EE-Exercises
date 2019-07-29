package fish.payara.examples.javaee.facade.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 2) @EJB -komponentti, jossa katsotaan, saako asiakas
 * lainaa. Tämä EJB on @Stateless, eli se ei pidä pyyntöjen
 * välillä sessiota. EJB injektoi itseensä kolme muuta EJB:ta,
 * jotka tarkistavat, saako käyttäjä lainaa (feikattu siltä osin
 * että jokaisen EJB:n funktio, jota kutsutaan, palauttavat "true"
 * -arvon kaikissa tilanteissa).
 * 
 */

@Stateless
public class BankServiceFacade {
  
  /**
   * 2 & 3) @Stateless @EJB -komponentti. Ei pidä sessiota yllä itsessään.
   */
	@Inject
	CustomerService customerService;
	
	/**
	 * 2 & 3) @Stateless @EJB -komponentteja. Eivät pidä sessiota yllä itsessään. 
	 * Sisältää feikattuja funktioita, joissa kaikki palautusarvot ovat automaattisesti 
	 * "true" -arvoja.
	 */
	@Inject
	LoanService loanService;
	@Inject
	AccountService accountService;
	
	/**
	 * 2) Funktio, missä katsotaan onko asiakas kykenevä lainaan. Tässä
	 * funktiossa käytetään hyväksi 3 eri injektoitua EJB:ta, jotka 
	 * palauttavat (tässä tilanteessa) "true" -arvon kaikille tarkistuksille
	 * riippumatta ID:stä tai lainarahamäärästä.
	 */
	public boolean getLoan(int sessionId, double amount) {
		boolean result = false;
		long id = customerService.getCustomer(sessionId);

		if (customerService.checkId(id)) {
			if (loanService.checkCreditRating(id, amount)) {
				if (accountService.getLoan(amount)) {
					result = accountService.setCustomerBalance(id, amount);
				}
			}
		}
		return result;
	}
}
