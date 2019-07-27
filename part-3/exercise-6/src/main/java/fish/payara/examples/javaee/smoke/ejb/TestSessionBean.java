/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2016 Payara Foundation and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://github.com/payara/Payara/blob/master/LICENSE.txt
 * See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * The Payara Foundation designates this particular file as subject to the "Classpath"
 * exception as provided by the Payara Foundation in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package fish.payara.examples.javaee.smoke.ejb;

import fish.payara.examples.javaee.smoke.cdi.ApplicationScopedBean;
import fish.payara.examples.javaee.smoke.cdi.RequestScopedBean;
import fish.payara.examples.javaee.smoke.cdi.SessionScopedBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 3) @EJB -komponentti. 
 * Tässä @EJB -komponentissa on tarkoitus testata, että injektointi onnistuu
 * @WebServlet applikaatio <--> @EJB-komponentti <--> @Injected normaali bean
 * välillä.
 * 
 * 2) Toimii välikätenä applikaation (TestServlet)
 * sekä normaalien beanien (ApplicationScopedBean, RequestScopedBean jne.)
 * välillä. Tämä EJB on @Stateless -muodossa eli se ei tilaansa kun sen
 * palveluita käytetään vaan aloittaa "puhtaalta pöydältä".
 * 
 */

/**
 *
 * @author Steve Millidge (Payara Foundation)
 */
@Stateless
public class TestSessionBean {
    
    /**
     * 2) @ApplicationScoped -bean eli pysyy hengissä koko applikaation 
     * elinajan aikana. Injektoidaan normaalina beanina tähän @EJB:en.
     */
    @Inject
    ApplicationScopedBean appBean;
    
    /**
     * 2) @SessionScoped -bean eli pysyy hengissä session aikana. Injektoidaan
     * normaalina beanina tähän @EJB:en.
     */
    @Inject
    SessionScopedBean sessBean;
    
    /**
     * 2) @RequestScoped -bean eli pysyy hengissä yhden haun aikana, jonka 
     * jälkeen se tuhoutuu. Injektoidaan normaalinan beanina tähän @EJB:en.
     */
    @Inject
    RequestScopedBean reqbean;
    
    /**
     * Normaali funktio joka palauttaa true -valuen. Katsotaan, että @EJB 
     * toimii sitä kutsuvasta funktiosta (eli on injektoitu onnistuneesti).
     */
    public boolean doYouExist() {
        return true;
    }
    
    
    /**
     * 2) Alemmissa funktiossa on tarkoitus testata, että kyseinen @EJB saa
     * yhteyden siihen injektoiduihin funktioihin sekä palauttaa true -arvon,
     * tarkoittaen, että yhteys 
     * @WebServlet applikaatio <--> @EJB <--> @Inject normaali bean 
     * toimii.
     */
    
    /**
     * 2) @ApplicationScoped -beanin funktio, joka palauttaa true -valuen
     * tähän funktioon ja edelleen tätä funktiota kutsuvalle.
     */
    public boolean testAppInjection() {
        return appBean.doIExist();
    }
    
    /**
     * 2) @SessionScoped -beanin funktio, joka palauttaa true -valuen
     * tähän funktioon ja edelleen tätä funktiota kutsuvalle
     */
    public boolean testSInjection() {
        return sessBean.doIExist();
    }
    
    /**
     * 2) @SessionScoped -beanin funktio, joka palauttaa true -valuen
     * tähän funktioon ja edelleen tätä funktiota kutsuvalle
     */
    public boolean testRInjection() {
        return reqbean.doIExist();
    }


    
}
