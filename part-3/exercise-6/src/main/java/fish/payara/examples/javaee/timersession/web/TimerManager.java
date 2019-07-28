/**
 * Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * https://github.com/javaee/tutorial-examples/LICENSE.txt
 */
package fish.payara.examples.javaee.timersession.web;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import fish.payara.examples.javaee.timersession.ejb.TimerSessionBean;

/**
 * 2) Tavallinen @SessionScoped -bean, joka nimetään timer-client.xhtml
 * -applikaatiota varten, jotta ne voivat keskustella keskenään - @Named.
 * 
 * Tämä bean toimii viewin sekä @EJB-komponentin - TimerSessionBean, kanssa.
 * Tähän beaniin injektoidaan @EJB-komponentti, joten tämä bean välittää ainoastaan
 * tietoa takaisin viewin ja komponentin välillä. 
 * 
 * 3) Ei ole @EJB-komponentti.
 */

/**
 *
 * @author ian
 */
@Named
@SessionScoped
public class TimerManager implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 3) Injektointi @EJB-komponentista. TimerSessionBean on
	 * @Singleton -session-bean.
	 */
	@EJB
	private TimerSessionBean timerSession;

	private String lastProgrammaticTimeout;
	private String lastAutomaticTimeout;
	
	/**
	 * 2) Kun timer-client.xhtml -applikaatio ottaa ensimmäisen kerran
	 * yhteyden tähän beaniin, luodaan alkuarvot luokan attribuuteille.
	 */
	/** Creates a new instance of TimerManager */
	public TimerManager() {
		this.lastProgrammaticTimeout = "never";
		this.lastAutomaticTimeout = "never";
	}

	/**
	 * @return the lastTimeout
	 */
	public String getLastProgrammaticTimeout() {
		lastProgrammaticTimeout = timerSession.getLastProgrammaticTimeout();
		return lastProgrammaticTimeout;
	}

	/**
	 * @param lastTimeout the lastTimeout to set
	 */
	public void setLastProgrammaticTimeout(String lastTimeout) {
		this.lastProgrammaticTimeout = lastTimeout;
	}
	
	/**
	 * 2) Kun käyttäjä painaa "Set Timer" -nappia, kutsutaan tätä funktiota, joka
	 * edelleen kutsuu timerSession @EJB-komponentin funktiota, joka luo viiveen
	 * eli ns. timeoutin.
	 */
	public void setTimer() {
		long timeoutDuration = 8000;
		timerSession.setTimer(timeoutDuration);
	}

	/**
	 * @return the lastAutomaticTimeout
	 */
	public String getLastAutomaticTimeout() {
		lastAutomaticTimeout = timerSession.getLastAutomaticTimeout();
		return lastAutomaticTimeout;
	}

	/**
	 * @param lastAutomaticTimeout the lastAutomaticTimeout to set
	 */
	public void setLastAutomaticTimeout(String lastAutomaticTimeout) {
		this.lastAutomaticTimeout = lastAutomaticTimeout;
	}

}
