package com.springwebflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.springwebflux.model.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {}
