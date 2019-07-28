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

import fish.payara.examples.javaee.smoke.ejb.data.Person;
import fish.payara.examples.javaee.smoke.ejb.data.SmokeEntity;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * 3) @EJB-komponentti. Ei säilytä tilaansa kyselyn jälkeen (@Stateless).
 * 
 */

/**
 *
 * @author Steve Millidge (Payara Foundation)
 */
@Stateless
public class SmokeJPABean {
  
    /**
     * 2) EntityManagerilla pystymme tekemään erilaisia 
     * tietokantatoimintoja tiettyyn tietokantaan. 
     * @PersistenceContext -annotaatiolla otetaan persistence.xml 
     * -tiedostossa olevaan yhteyteen MySQL -palvelimeen. Tämän 
     * mukana annetaan halutun yhteyden nimi, jotta yhteys saadaan
     * muodostettua.
     */
    @PersistenceContext(name = "SmokeTestPU")
    private EntityManager entityManager;
    
    /**
     * 2) EJBContext -rajapinta antaa instanssi-mahdollisuuden ko. @EJB
     * -komponentissa oleviin käskyihin, dataan jne.
     */
    @Resource
    private EJBContext context;
    
    /**
     * 2) Tässä otetaan yhteys JPA-luokkaan SmokeEntity. Parametri kertoo (howMany)
     * kuinka monta kertaa tehdään uusi olio tästä luokasta, johon tallennetaan 
     * esimerkkiviesti. Nämä pusketaan tietokantaan .persist -komennon avulla. 
     * Palauttaa lopuksi "true" -arvon, jos tallennuksen aikana ei käynyt mitään.
     */
    public boolean bulkLoad(int howMany) {
        boolean result = true;
        for (int i = 0; i < howMany; i++) {
            SmokeEntity entity = new SmokeEntity();
            entity.setData("Entity Data " + i);
            entity.setSomeNumber((long)i);
            entityManager.persist(entity);
        }
        return result;
    }
    
    
    /**
     * 2) Tässä funktiossa tehdään uusi JPA-olio, johon lisätään esimerkkidataa 
     * sekä esimerkkiaika. Lopuksi ne tallennetaan tietokantaan. Tallenettu 
     * JPA-olio palautetaan takaisin.
     */
    public SmokeEntity insert() {
        SmokeEntity entity = new SmokeEntity();
        entity.setData("Inserted Entity");
        entity.setSomeNumber(System.currentTimeMillis());
        entityManager.persist(entity);
        return entity;
    }
    
    /**
     * 2) Tässä funktiossa etsitään tietty rivi taulusta ID:n avulla.
     * Palautetaan löydetty olio.
     */
    public SmokeEntity retrieve(Long id)  {
        return entityManager.find(SmokeEntity.class, id);
    }
    
    /**
     * 2) Tässä funktiossa lasketaan kaikki rivit mitä löydetään ko. taulusta. 
     * Palautetaan luku Long -tyyppisenä.
     */
    public long countAll() {
        Query q = entityManager.createQuery("SELECT COUNT(se) from SmokeEntity se");
        return (Long)q.getSingleResult();
    }
    
    /**
     * 2) Tässä funktiossa poistetaan KAIKKI rivit taulusta SmokeEntity. Palauttaa
     * queryn tuloksen.
     */
    public int deleteAll() {
        Query query = entityManager.createQuery("DELETE from SmokeEntity se");
        return query.executeUpdate();
    }
    
    /**
     * 2) Tässä funktiossa etsitään Person-JPA -luokasta tietty rivi ID:n perusteella.
     * Palautetaan löydetty olio (rivi).
     */
    public Person retrieveFamily(Long ID) {
        return entityManager.find(Person.class, ID);
    }
    
    /**
     * 2) Person -luokassa on yhteydet feikkitauluihin: spouse, parent & children.
     */
    public Person createFamily() {
        /**
         * 2) Luodaan aluksi uusi ihminen - "Fred", ja tallennetaan se tietokantaan.
         */
        Person fred = new Person();
        fred.setFirstName("Fred");
        fred.setLastName("Bloggs");
        entityManager.persist(fred);
        
        /**
         * 2) Seuraavaksi luodaan uusi ihminen - "Freda". Yhteyksien avulla Fredan 
         * puolisoksi annetaan ylempänä luotu "Fred". Tallennetaan se tietokantaan.
         */
        Person freda = new Person();
        freda.setFirstName("Freda");
        freda.setLastName("Bloggs");
        freda.setSpouse(fred);
        fred.setSpouse(freda);
        entityManager.persist(freda);
        
        /**
         * 2) Lopuksi luodaan ihminen - "Freddy". Yhteyksien avulla Freddyn vanhemmaksi 
         * annetaan Fred ja toisinpäin. Tallennetaan se tietokantaan.
         */
        Person freddy = new Person();
        freddy.setFirstName("Freddy");
        freddy.setLastName("Bloggs");
        freddy.setParent(fred);
        fred.addChild(freddy);
        entityManager.persist(freddy);
        
        /**
         * 2) Lopuksi palautetaan ensimmäisenä JPA-luokassa tehty "Fred".
         */
        return fred;
    }
    
    /**
     * 2) Tässä funktiossa kokeillaan aluski luoda uusi JPA-olio ja tallentaa se esimerkkidatalla.
     * Tämän jälkeen käytetään context -oliota, jolla vyörytetään juuri tallennettu rivi takaisin.
     * Jos kaikki onnistuu, palautetaan tallenettu olio.
     */
    public SmokeEntity rollBack() {
        SmokeEntity p = new SmokeEntity();
        p.setData("Test Data");
        entityManager.persist(p);
        context.setRollbackOnly();
        return p;
    }
    
    /**
     * 2) Tämä funktio poistaa kaikki rivit Person-JPA -luokasta. Palauttaa
     * queryn tuloksen.
     */
    public int deleteAllPeople() {
        Query query = entityManager.createQuery("DELETE from Person p");
        return query.executeUpdate();    
    }
    
}
