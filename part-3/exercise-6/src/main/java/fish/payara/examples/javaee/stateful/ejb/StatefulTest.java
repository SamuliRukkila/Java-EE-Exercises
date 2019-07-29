package fish.payara.examples.javaee.stateful.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;

/**
 * 2 & 3) @EJB-komponentti, jossa on yhdistetty @SessionScoped-
 * sekä @Stateful -annotaatio.
 * 
 * @SessionScoped -annotaatiota käytetään yleensä tavallisissa 
 * beaneissa, jossa sessio toimii HTTP-session avulla, kun taas
 * @Stateful -annotaatiota enemmän @EJB-komponentin kanssa. Jos nämä
 * kaksi annotaatiota yhdistetään, aktiivisesta HTTP-sessiosta tulee
 * @EJB-client, joka ylläpitää instansseja.
 */

@Stateful
@SessionScoped
public class StatefulTest {

	public StatefulTest() {
	}

	private int i;
	
	/**
	 * 2) @PostConstruct -annotaatiota käytetään menetelmässä, joka on 
	 * suoritettava sen jälkeen kun riippuvuus-injektio on tehty. Eli tämä
	 * funktio suoritetaan ENNENKUIN luokka otetaan käyttöön.
	 * 
	 * Tässä funktiossa muuttujalle "i" annetaan arvo 0, jotta sitä voidaan
	 * plussata.
	 * 
	 * Koska kyseessä on session omaava @EJB-komponentti, tätä funktiota kutsutaan
	 * uudestaan vain silloin kun uusi sessio luodaan.
	 */
	@PostConstruct
	public void initialize() {
		i = 0;
	}

	public int getI() {
		return i++;
	}
}
