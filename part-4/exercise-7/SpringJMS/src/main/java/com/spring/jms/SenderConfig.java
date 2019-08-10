package com.spring.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

/**
 * JmsTemplaten sekä Senderin luonti tehdään tässä konfiguraatio-
 * luokassa. 
 * 
 * Jotta voimme käyttää Springin JmsTemplaattia, meidän on annettava
 * viitaus ConnectionFactory-kirjastoon, jota käytetään luomaan
 * yhteyksiä JMS-palveluntarjoajaan. JmsTemplaatti myös kapseloi
 * erilaisia konfiguraatioparametreja, joista monet ovat tiettyyn
 * tarkoitukseen tarkoitettua.
 * 
 * Luokka on merkitty @Configuration -annotaatiolla, joka
 * osoittaa, että Spring IoC-container voi käyttää luokkaa 
 * bean-määritysten lähteenä.
 * 
 * @Bean -annotaatio funktiossa osoittaa, että funktio tuottaa
 * beanin, jota hallitaan Springin omassa containerissa.
 * 
 */

@Configuration
public class SenderConfig {
  
  /**
   * Haemme broker-osoitteen (johon viestit lähetetään/vastaanotetaan) 
   * application.yml -tiedostosta, joka voidaan hakea @Value-annotaatiolla.
   */
  @Value("${activemq.broker-url}")
  private String brokerUrl;
  
  
  /**
   * Jotta voimme käyttää Springin Jms-templaattia meidän on annettava
   * viittaus ConnectionFactory-kirjastoon, jota käytetään luomaan
   * yhteyksiä JMS-palveluntarjoajaan.
   * 
   * URL:ksi määritellään brokerUrl -muuttuja, jonka tieto haetaan
   * application.yml -tiedostosta.
   * 
   * @return Aktiivisen ConnectionFactory-olion
   */
  @Bean
  public ActiveMQConnectionFactory senderActiveMQConnectionFactory() {
    ActiveMQConnectionFactory activeMQConnectionFactory =
        new ActiveMQConnectionFactory();
    activeMQConnectionFactory.setBrokerURL(brokerUrl);

    return activeMQConnectionFactory;
  }

  
  /**
   * Tässä käärimme ACtiveMQConnectionFactoryn Springin
   * CachinConnectionFactory -kirjastoon, jotta hyötyisimme
   * istuntojen, yhteyksien ja tuottajien välimuistista sekä
   * automaattisen yhteyden palautuksesta.
   * 
   * @return CachingConnectionFactory-olion, jonka sisällä 
   *  on ActiveMQConnectionFactory
   */
  @Bean
  public CachingConnectionFactory cachingConnectionFactory() {
    return new CachingConnectionFactory(
        senderActiveMQConnectionFactory());
  }

  
  /**
   * Funktio, mikä palauttaa aktiivisen Jms-templaatin. Templaatti
   * rakennetaan käyttäen kahta muuta funktiota tähän.
   * 
   * Sender -luokka kutsuu tätä (GET)-funktiota send()-funktiolla.
   * 
   * @return Jms-templaatin
   */
  @Bean
  public JmsTemplate jmsTemplate() {
    return new JmsTemplate(cachingConnectionFactory());
  }

  
  /**
   * Funktio, mikä palauttaa uuden Sender-luokasta tehdyn olion.
   * 
   * @return Uuden Sender-luokasta tehdyn olion.
   */
  @Bean
  public Sender sender() {
    return new Sender();
  }
}
