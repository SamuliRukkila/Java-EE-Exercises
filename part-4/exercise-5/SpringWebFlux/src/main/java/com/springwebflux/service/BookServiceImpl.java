package com.springwebflux.service;

import org.springframework.stereotype.Service;

import com.springwebflux.model.Book;
import com.springwebflux.repository.BookRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookServiceImpl implements BookService {

  private BookRepository bookRepo;
  
  public BookServiceImpl(BookRepository bookRepo) {
    this.bookRepo = bookRepo;
  }
  
  @Override
  public Mono<Book> findById(String id) {
    return bookRepo.findById(id);
  }

  @Override
  public Flux<Book> findAll() {
    return bookRepo.findAll();
  }

  @Override
  public Mono<Book> save(Book book) {
    return bookRepo.save(book);
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return bookRepo.deleteById(id);
  }
  
  
  
}
