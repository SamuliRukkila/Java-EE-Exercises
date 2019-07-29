/**
 * Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * https://github.com/javaee/tutorial-examples/LICENSE.txt
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fish.payara.examples.javaee.interceptor.ejb;

import java.util.logging.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * 2 & 3) @EJB-komponentti, jonka ainoa tarkoitus on keskeyttää HelloBean -
 * luokassa oleva setName() -funktio, jossa tallennetaan käyttäjän nimi
 * luokan attribuuttiin. Edelläolevaan funktioon ollaan annettu annotaatio
 * @Interceptors, jossa otetaan yhteys tähän luokkaan. Tästä luokasta
 * etsitään @AroundInvoke -annotaatio (vain yksi tälläinen annotaatio per 
 * luokka) ja suoritetaan sen sisällä oleva koodi ja palautetaan se 
 * ennenkuin se tekemät muutokset palautetaan takaisin.
 */

/**
 *
 * @author ian
 */
public class HelloInterceptor {
	protected String greeting;
	private static final Logger logger = Logger.getLogger("interceptor.ejb.HelloInterceptor");

	public HelloInterceptor() {
	}
	
	/**
	 * 2) Tämä suoritetaan @Interceptors -annotaation avulla toisessa funktiossa.
	 * Näitä annotaatioita saa vain olla 1 per luokka. 
	 * 
	 * Luokka ottaa vastaan InvocationContext -olion joka kertoo tarvittavia 
	 * tietoja keskeytetystä funktiosta. Olion parametreista otetaan nimi
	 * vastaan ja muutetaan se CAPS LOCK:sta pieniin kirjaimiin. Lopuksi 
	 * muokattu nimi -parametri pistetään takaisin olion parametriin ja kutsutaan
	 * ctx:n funktiota - proceed(), joka jatkaa seuraavaan keskeyttäjään 
	 * (interceptor) tai jos niitä ei ole, palaa takaisin funktioon muokatuilla
	 * arvoilla.
	 */
	@AroundInvoke
	public Object modifyGreeting(InvocationContext ctx) throws Exception {
		Object[] parameters = ctx.getParameters();
		String param = (String) parameters[0];
		param = param.toLowerCase();
		parameters[0] = param;
		ctx.setParameters(parameters);
		try {
			return ctx.proceed();
		} catch (Exception e) {
			logger.warning("Error calling ctx.proceed in modifyGreeting()");
			return null;
		}
	}

}
