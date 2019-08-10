package com.spring.jms;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tässä testitiedostossa luodaan perustason SpringJmsApplicationTest
 * -testiluokka varmistaakseme, että pystymme lähettämään ja
 * vastaanottamaan viestin ActiveMQ:lle & ActiveMQ:lta.
 * 
 * @Test -annotaatio kertoo JUnitille (Java testikirjasto), että julkinen
 * void menetelmä, johon se on liitetty, voidaan suorittaa testitapauksena.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringJmsApplicationTests {

  @Autowired
  private Sender sender;

  @Autowired
  private Receiver receiver;
  
  /**
   * testReceive() -funktio sisältää yksikkötestauksen,
   * joka käyttää Sender-luokkaa lähettääkseen viestin
   * "helloworld.q" -jonoon ActiveMQ -viestinvälittäjällä.
   * 
   * Käytämme sen jälkeen vastaanottamiseen CountDownLatch-
   * kirjastoa, joka varmistaa viestin vastaanottamisen.
   */
  @Test
  public void testReceive() throws Exception {
    sender.send("Hello Spring JMS ActiveMQ!");

    receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    assertThat(receiver.getLatch().getCount()).isEqualTo(0);
  }
}