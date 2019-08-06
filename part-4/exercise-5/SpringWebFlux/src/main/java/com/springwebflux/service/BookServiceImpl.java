package com.springwebflux.service;

import org.springframework.stereotype.Service;

import com.springwebflux.model.Book;
import com.springwebflux.repository.BookRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @Service -annotaation omaava luokka edustaa sovelluslogiikan
 * toteutusta ja funktiota. Se voidaan määritellä operaationa,
 * joka yksin modelissa ilman kapseloitua tilaa.
 *
 * Tässä esimerkissä sovelluslogiikka vain delegoidaan taustalla
 * olevaan repositoryyn.
 * 
 * (Jos joutuisit suorittamaan monimutkaisempaa logiikkaa kyselyihin
 * tai pysyviin objekteihin, tehtäisiin se tässä.)
 * 
 * Service-luokka yliajaa palvelun BookService funktiot ja toteuttaa
 * ne kutsumalla BookRepository -rajapinnan funktiota, jotka ovat 
 * yhteydessä MongoDB-kantaan.
 */
@Service
public class BookServiceImpl implements BookService {

  private BookRepository bookRepo;
  
  public BookServiceImpl(BookRepository bookRepo) {
    this.bookRepo = bookRepo;
  }
  
  /**
   * Funktio, joka hakee yhden kirjan ID:n perusteella
   * @return Löydetyn kirjan Mono-muodossa
   */
  @Override
  public Mono<Book> findById(String id) {
    return bookRepo.findById(id);
  }
  
  /**
   * Funktio, joka hakee kaikki MongoDB-kannassa olevat
   * kirjat.
   * @return Kaikki löydetyt kirjat Flux-muodossa
   */
  @Override
  public Flux<Book> findAll() {
    return bookRepo.findAll();
  }
  
  /**
   * Funktio, joka tallentaa uuden kirjan MongoDB-kantaan.
   * @return Tallennetun kirjan Mono-muodossa
   */
  @Override
  public Mono<Book> save(Book book) {
    return bookRepo.save(book);
  }
  
  /**
   * Funktio, joka poistaa kirjan MongoDB-kannasta ID:n avulla.
   * @return Tyhjän Mono-muotoisen tyypin (delete ei palauta mitään)
   */
  @Override
  public Mono<Void> deleteById(String id) {
    return bookRepo.deleteById(id);
  }
  
  
  
}
