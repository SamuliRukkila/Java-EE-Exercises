package com.springwebflux.web;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.springwebflux.model.Book;
import com.springwebflux.service.BookService;

import reactor.core.publisher.Mono;

/**
 * Web-paketissa olevat luokat ovat erilainen tapa hoitaa 
 * routing sekä handleri HTTP-kyselyille. 
 * 
 * Handler-luokat vastaavat 
 * business-logiikan toteuttamisesta ja vastausten rakentamisesta.
 * Allaoleva luokka ei siis ole kontrolleri vaan tavallinen Springin
 * bean, joka kytketään BookRouter-luokkaan.
 * 
 * Kaikki luokan funktiot palauttavat arvon Mono<ServerResponse>.
 * Kun handleria rakennetaan on devaaja vastuussa vastaus-arvon
 * rakentamisesta, joka palautetaan kutsujalle. Kaikki funktiot
 * saavat ServerRequest -parametrin, mikä antaa oikeudet esim. 
 * HTTP-pyynnön parametreihin, luokka-muuttujiin ja SQL-kyselyn
 * parametreihin.
 * 
 * Jotta voimme rakentaa response bodyn, meidän pitää konstruktoida
 * se käyttämällä BodyBuilderia. ok() -metodi palauttaa BodyBuilderin,
 * jonka HTTP-tilakoodi on 200 (=HttpStatus.OK). BodyBuilder-rajapinta
 * määrittelee menetelmät sisällömn tyyppiin, sisällön pituuteen
 * sekä HTTP-headerin arvojen asettamiseksi.
 * 
 * @Component -annotaatio on yleinen merkintä, joka tunnistaa luokan
 * Springin hallitsemaksi beaniksi. Spring löytää tämän komponentin,
 * kun se tekee komponentti-skannuksen ja lisää sen sovellus-yhteyteen.
 */
@Component
public class BookHandler {

  private final BookService bService;
  
  public BookHandler(BookService bService) {
    this.bService = bService;
  }
  
  public Mono<ServerResponse> findById(ServerRequest req) {
    String id = req.pathVariable("id");
    return ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(bService.findById(id), Book.class);
  }
  
  public Mono<ServerResponse> findAll(ServerRequest req) {
    return ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(bService.findAll(), Book.class);
  }
  
  /**
   * save() -funktiossa voidaksemme deserialisoida bodyn
   * payloadin luokan instanssiksi, meidän pitää kutsua
   * ServerRequestin funktiota bodyToMono(). 
   * 
   * Tämä metodi palauttaa Mono<Book>, joka on tilaaja,
   * joka toimittaa Book-instanssin asynkronisesti kun
   * se on saatavilla. Kun Mono<Book> -muoto on kädessä,
   * rakennamme taas vastauksen käyttämällä ok()-metodia.
   * 
   * Palautusvaiheessa fromPublisher() -metodi palauttaa
   * BodyInserter-menetelmän, mitä body()-metodi odottaa.
   * 
   */
  public Mono<ServerResponse> save(ServerRequest req) {
    final Mono<Book> book = req.bodyToMono(Book.class);
    return ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(fromPublisher(book.flatMap(bService::save), Book.class));
  }
  
  public Mono<ServerResponse> delete(ServerRequest request) {
    String id = request.pathVariable("id");
    return ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(bService.deleteById(id), Void.class);
  }
}
