package com.springwebflux.web;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * Web-paketissa olevat luokat ovat erilainen tapa 
 * hoitaa routing sekä handleri HTTP-kyselyille.
 * 
 * router() -metodi luo beanin, jonka tyyppi on
 * RouterFunction<ServerResponse>. Routerin funktiot
 * ovat vastuussa HTTP-reittien kääntämisestä handlerin
 * funktiolle (BookHandler.java).
 * 
 * Esimerkiksi ensimmäinen route: jos on tulossa GET-tyyppinen
 * pyyntö /fbooks -reittiin ja media hyväksyy tietotyypin
 * APPLICATION_JSON, kutsutaan BookHandler-luokan findAll()-
 * metodia.
 * 
 * @Configuration on Springin annotaatio, joka tunnistaa
 * luokan konfiguraatio-luokaksi, jonka metodit luovat
 * toisia Spring beaneja.
 * 
 */
@Configuration
public class BookRouter {
  
  /**
   * route()-funktio palauttaa RouterFunction -instanssin, joka mahdollistaa
   * muiden polkujen lisäämisen kutsumalla addRoute()-funktiota.
   * 
   * Yhteenvetona voidaan todeta, että BookRouter::route -menetelmä luo
   * RouterFunction-menetelmän, joka koostuu useista routerin metodeista.
   * Nämä määrittelevät olosuhteet milloin tiettyä handlerin funktiota 
   * pitäisi kutsua.
   * 
   * Merkitty @Bean-annotaatiolla, joka tarkoittaa, että se palauttaa 
   * Springin hallitseman beanin. Kun Spring vetoaa tähän metodiin,
   * se huomaa, että beantarvitsee BookHandler-instanssin.
   */
  @Bean
  public RouterFunction<ServerResponse> route(BookHandler handler) {
    return RouterFunctions
        /**
         * GET()-metodi on staattisesti importattu RequestPredicates-luokasta,
         * joka palauttaa RequestPredicate -instanssin.
         * 
         * Predikaatti on boolean-arvoinen funktio, jolla on testi()-menetelmä,
         * joka arvioi predikaatin ja palauttaa true/false -arvon, jos predikaatin
         * ehdot täyttyvät. RequestPedicate-luokka luo ServerRequestin määrittääkseen,
         * pitäisikö tämn reitin käsitellä pyyntö vai ei.
         * 
         * RequestPredicate-instanssi vertaa HTTP-verbiä ServerRequestissa 
         * HttpMethod.GET -menetelmään, sekä polkua määritettyyn URI-malliin. Sitten
         * ketjutamme accept(MediaType.APPLICATION_JSON) -menetelmän predikaattiin
         * käyttämällä and() -metodia, joka on standari predikaatti-funktio, joka luo
         * kaksi predikaattio käyttämällä AND-menetelmän boolean-logiikkaa.
         * 
         * Lopuksi handler::findAll -funktiota kutsutaan jos seuraavat tapahtumat ovat
         * totta: 1.) HTTP-verb on GET 2.) URI-polku on "/fbooks" 3.) HTTP:n "Accept" -header
         * on "application/json". 
         * 
         */
        .route(GET("/fbooks").and(accept(MediaType.APPLICATION_JSON)), handler::findAll)
        .andRoute(GET("/fbook/{id}").and(accept(MediaType.APPLICATION_STREAM_JSON)), handler::findById)
        .andRoute(POST("/fbook").and(accept(MediaType.APPLICATION_JSON)), handler::save)
        .andRoute(DELETE("/fbook/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::delete);
  }
  
}
