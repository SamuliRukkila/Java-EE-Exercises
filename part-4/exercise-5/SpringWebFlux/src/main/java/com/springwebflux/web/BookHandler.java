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
