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

import fish.payara.examples.javaee.smoke.ejb.data.SmokeEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * 3) @EJB-komponentti. Komponentti on @Stateless, joten se hävittää
 * tehdyt muutokset/tiedot jokaisen requestin jälkeen. Komponentissa on myös
 * määritelty, että ko. @EJB-komponentilla on "papu-hallitut" transaktionit, 
 * "kontti-hallinta" transaktionin sijaan (@TransactionManagement).
 */

/**
 * An EJB using Bean Managed Transactions
 *
 * @author Steve Millidge (Payara Foundation)
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class SmokeJPABMBean {
    
    /**
     * 2) UserTransaction -rajapinta kertoo metodeista, jotka sallivat applikaation 
     * suorittaa ja ylläpitää transaktio -rajauksia.
     */
    @Resource
    UserTransaction ut;
    
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
     * 2) Funktio, joka lisää parametrissa annetun määrän (howMany) rivejä tauluun.
     * Tässä SQL-kyselihin käytetään transaktionia, eli muutokset tulevat voimaan 
     * lopullisesti vasta, kun ut.commit() -funktiota kutsutaan.
     */
    public boolean bulkLoad(int howMany) {
        boolean result = true;
        try {
            ut.begin();
            for (int i = 0; i < howMany; i++) {
                SmokeEntity entity = new SmokeEntity();
                entity.setData("Entity Data " + i);
                entity.setSomeNumber((long) i);
                entityManager.persist(entity);
            }
            ut.commit();

        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            result = false;
            Logger.getLogger(SmokeJPABMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /**
     * 2) Funktio, joka lisää uuden rivin tauluun käyttäen transaktionia. Muutokset 
     * tulevat voimaan lopullisesti vasta, kun ut.commit() -funktiota kutsutaan.
     */
    public SmokeEntity insert() {
        SmokeEntity entity = new SmokeEntity();
        try {
            ut.begin();

            entity.setData("Inserted Entity");
            entity.setSomeNumber(System.currentTimeMillis());
            entityManager.persist(entity);
            ut.commit();

        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(SmokeJPABMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entity;
    }
    
    /**
     * 2) Haetaan taulusta riviä ID:n perusteella
     */
    public SmokeEntity retrieve(Long id) {
        return entityManager.find(SmokeEntity.class, id);
    }
    
    /**
     * 2) Funktio, joka laskee kaikkien rivien määrän SmokeEntity -taulusta. 
     * SQL-kysely lähetetään vasta kun ut.commit() -funktiota kutsutaan.
     */
    public long countAll() {
        long result = -1;
        try {
            ut.begin();
            Query q = entityManager.createQuery("SELECT COUNT(se) from SmokeEntity se");
            result = (Long) q.getSingleResult();
            ut.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(SmokeJPABMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * 2) Funktio, joka poistaa kaikki rivit SQL-taulusta. SQL-kysely 
     * lähetetään vasta kun ut.commit() -funktiota kutsutaan.
     */
    public int deleteAll() {
        long result = -1;
        try {
            ut.begin();
            Query q = entityManager.createQuery("DELETE from SmokeEntity se");
            result = q.executeUpdate();
            ut.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(SmokeJPABMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (int) result;
    }
    
    /**
     * 2) Funktio, joka luo aluksi uuden rivin tauluun, mutta tallenna sitä vielä. Ennenkuin 
     * riviä tallennetaan, kutsutaan ut.rollback() -mikä tuhoaa kyseessä olevan transaktionin
     * kokonaan.
     */
    public SmokeEntity rollBack() {
        SmokeEntity p = new SmokeEntity();
        try {

            ut.begin();
            p.setData("Test Data");
            entityManager.persist(p);
            ut.rollback();

        } catch (NotSupportedException | SystemException ex) {
            Logger.getLogger(SmokeJPABMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

}
