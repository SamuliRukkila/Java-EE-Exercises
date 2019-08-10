package com.spring.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;


/**
 * Spring beanien luominen ja konfigurointi viestien
 * vastaanottamiseen on ryhmitelty tähän luokkaan.
 *
 * @EnableJms -annotaatio lisätään tähän luokkaan, koska
 * Receiver-luokassa oleva @JmsListener -annotaatio tarvitsee
 * sitä sen toimimiseen.
 * 
 * @Bean -annotaatio funktiossa osoittaa, että funktio tuottaa
 * beanin, jota hallitaan Springin omassa containerissa.
 * 
 * Luokka on merkitty @Configuration -annotaatiolla, joka
 * osoittaa, että Spring IoC-container voi käyttää luokkaa 
 * bean-määritysten lähteenä.
 */

@Configuration
@EnableJms
public class ReceiverConfig {
  
  /**
   * Haemme broker-osoitteen (johon viestit lähetetään/vastaanotetaan) 
   * application.yml -tiedostosta, joka voidaan hakea @Value-annotaatiolla.
   */
  @Value("${activemq.broker-url}")
  private String brokerUrl;
  
  
  /**
   * Kun olemme muodostaneet yhteyden ActiveMQ:een, 
   * ActiveMQConnectionFactory -olio luodaan ja välitetään 
   * DefaultJMSListenerContainerFactory-konstruktorissa.
   * 
   * @JmsListener -annotaatio Sender-luokassa odottaa
   * jmsListenerContainerFactory() -funktiota joka on luotu
   * tässä.
   */
  @Bean
  public ActiveMQConnectionFactory receiverActiveMQConnectionFactory() {
    ActiveMQConnectionFactory activeMQConnectionFactory =
        new ActiveMQConnectionFactory();
    activeMQConnectionFactory.setBrokerURL(brokerUrl);

    return activeMQConnectionFactory;
  }
  @Bean
  public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
    DefaultJmsListenerContainerFactory factory =
        new DefaultJmsListenerContainerFactory();
    factory
        .setConnectionFactory(receiverActiveMQConnectionFactory());

    return factory;
  }

  
  /**
   * Funktio, mikä palauttaa uuden Receiver-luokasta tehdyn olion.
   * 
   * @return Uuden Receiver-luokasta tehdyn olion.
   */
  @Bean
  public Receiver receiver() {
    return new Receiver();
  }
}