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

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.interceptor.Interceptors;

/**
 * 2 & 3) @EJB-komponentti, joka toimii viewin sekä Interceptor -luokan kanssa.
 * Muokkaa käyttäjän nimen CAPS LOCKISTA pieniin kirjaimiin. Toimii siis
 * tämän välissä. Tämä bean on myös varustettu @Named -annotaatiolla, jotta
 * tiedetään, että se keskustelee JSF:n faceletin (interceptor.xhtml) kanssa.
 */

/**
 *
 * @author ian
 */
@Stateless
@Named
public class HelloBean {

	protected String name;

	/**
	 * Get the value of name
	 *
	 * @return the value of name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 2) Tämä name -attribuutin SET-funktio sisältää @Interceptors -annotaation,
	 * eli ennenkuin funktion koodi ajetaan, viedään tullut parametri sekä muu
	 * tieto annotaation mukana siinä määritettyyn luokkaan (HelloInterceptor).
	 * Kun Interceptor -luokan @AroundInvoke -annotaation omaava funktio on 
	 * suoritettu, palautuu se takaisin tähän funktioon ja se suoritetaan 
	 * normaaliin tapaan loppuun.
	 */
	/**
	 * Set the value of name
	 *
	 * @param name new value of name
	 */
	@Interceptors(HelloInterceptor.class)
	public void setName(String name) {
		this.name = name;
	}

}
