/**
 * Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * https://github.com/javaee/tutorial-examples/LICENSE.txt
 */
package fish.payara.examples.javaee.timersession.ejb;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;


/**
 * 2) Kyseessä on ns. timer-bean, joka käynnistyy heti kun sitä käyttävä applikaatio
 * käynnistyy - @Startup. @Singleton -annotaation ansiosta tästä beanista ei luoda
 * instanssia jokaiselle sitä käyttävällä käyttäjälle, vaan siitä sama instanssi jaetaan
 * jokaisen tätä beania käyttävän kanssa
 * 
 * 3) @EJB-komponentti
 */
@Singleton
@Startup
public class TimerSessionBean {
  
  /**
   * 3) @EJB-komponentti. Otetaan käyttöön suoraan tässä beanissa, jotta sen funktiota voidaan
   * käyttää hyväksi.
   */
	@Resource
	TimerService timerService;

	private Date lastProgrammaticTimeout;
	private Date lastAutomaticTimeout;

	private static final Logger logger = Logger.getLogger("timersession.ejb.TimerSessionBean");
	
	/**
	 * 2) Kun käyttäjä painaa "Set Timer" -nappia, tulee siitä tieto sekä millisekuntimäärä tänne
	 * beanin välityksellä. Käyttäen hyväksi timerService -oliota, luodaan uusi timeri, joka 
	 * laukaisee kaikki @Timeout -annotaation omaavat funktiot. @Timeout -funktiot eivät saa ottaa
	 * kuin Timer -olion parametrin vastaan, eivätkä ne saa heittää omia virheitä.
	 */
	public void setTimer(long intervalDuration) {
		logger.log(Level.INFO, "Setting a programmatic timeout for {0} milliseconds from now.", intervalDuration);
		@SuppressWarnings("unused")
    Timer timer = timerService.createTimer(intervalDuration, "Created new programmatic timer");
	}
	
	/**
	 * 2) Tämä laukaistaan kun timerService.createTimer() -funktiossa annettu timer loppuu. 
	 * Lisää uuden Date -tyyppisen arvon luokan attribuuttiin.
	 */
	@Timeout
	public void programmaticTimeout(Timer timer) {
		this.setLastProgrammaticTimeout(new Date());
		logger.info("Programmatic timeout occurred.");
	}
	
	/**
	 * 2) @Schedule -annotaation omaavat funktio ajetaan parametreissa annetun ohjeitten mukaan.
	 * (Jos aikaa ei olla annettu se laukaistaan 24h välein yöllä). Tässä esimerkissa
	 * kyseinen funktio ajetaan joka minuutin välein; se tulostaa viestin automaattisesta
	 * timeoutista konsoliin sekä lisää uuden Date -tyyppisen arvon luokan attribuuttiin.
	 */
	@Schedule(minute = "*/1", hour = "*", persistent = false)
	public void automaticTimeout() {
		this.setLastAutomaticTimeout(new Date());
		logger.info("Automatic timeout occurred");
	}
	
	
	
	/**
	 * 2) GETTERIT JA SETTERI
	 */
	
	/**
	 * @return the lastTimeout
	 */
	public String getLastProgrammaticTimeout() {
		if (lastProgrammaticTimeout != null) {
			return lastProgrammaticTimeout.toString();
		} else {
			return "never";
		}
	}

	/**
	 * @param lastTimeout the lastTimeout to set
	 */
	public void setLastProgrammaticTimeout(Date lastTimeout) {
		this.lastProgrammaticTimeout = lastTimeout;
	}

	/**
	 * @return the lastAutomaticTimeout
	 */
	public String getLastAutomaticTimeout() {
		if (lastAutomaticTimeout != null) {
			return lastAutomaticTimeout.toString();
		} else {
			return "never";
		}
	}

	/**
	 * @param lastAutomaticTimeout the lastAutomaticTimeout to set
	 */
	public void setLastAutomaticTimeout(Date lastAutomaticTimeout) {
		this.lastAutomaticTimeout = lastAutomaticTimeout;
	}
}
