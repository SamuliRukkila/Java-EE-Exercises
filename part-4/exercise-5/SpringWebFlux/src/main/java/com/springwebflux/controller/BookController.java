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

@RestController
public class BookController {

  private BookService bService;
  
  public BookController(BookService bService) {
    this.bService = bService;
  }
  
  @GetMapping(value = "/book/{id}")
  public Mono<Book> getBookById(@PathVariable String id) {
    return bService.findById(id);
  }
  
  @GetMapping(value = "/books")
  public Flux<Book> getAllBooks() {
    return bService.findAll();
  }
  
  @PostMapping(value = "/book")
  public Mono<Book> createBook(@RequestBody Book book) {
    return bService.save(book);
  }
  
}
