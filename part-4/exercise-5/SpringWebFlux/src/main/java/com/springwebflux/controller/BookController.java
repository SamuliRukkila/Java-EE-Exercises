package com.springwebflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springwebflux.model.Book;
import com.springwebflux.service.BookService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * BookController on merkitty @RestController -annotaatiolla,
 * joka on enemmänkin soveltuvuus-merkintä.
 * 
 * Tämä merkintä sisältää @Controller -annotaaton, jota käytetään
 * tunnistamaan luokka, joka käsittelee verkkopyyntöjä (web requests)
 * ja @ResponseBody, joka osoittaa, että funktioiden paluuarvot
 * olisi sidottava web-bodyyn.
 */
@RestController
public class BookController {
  
  /**
   * Injektoidaan @Service-luokka BookService konstruktorin kautta
   */
  private BookService bService;
  
  public BookController(BookService bService) {
    this.bService = bService;
  }
  
  
  /**
   * GET-tyyppinen funktio, joka hakee kirjan ID:n perusteella.
   * 
   * @param id - Kirjan ID
   * @return Löydetty kirja Mono-muodossa
   */
  @GetMapping(value = "/book/{id}")
  public Mono<Book> getBookById(@PathVariable String id) {
    return bService.findById(id);
  }
  
  /**
   * GET-tyyppinen funktio, joka hakee kaikki MongoDB-kannassa
   * olevat kirjat.
   * 
   * @return Löydetyt kirjat Flux-muodossa
   */
  @GetMapping(value = "/books")
  public Flux<Book> getAllBooks() {
    return bService.findAll();
  }
  
  /**
   * POST-tyyppinen funktio, joka lisää uuden kirjan MongoDB-kantaan.
   * 
   * @Example: { "title": "Book 1", "author": "Author" }
   * 
   * @param book - Uusi kirja objektina
   * @return Tallennettu kirja Mono-muodossa
   */
  @PostMapping(value = "/book")
  public Mono<Book> createBook(@RequestBody Book book) {
    return bService.save(book);
  }
  
}
