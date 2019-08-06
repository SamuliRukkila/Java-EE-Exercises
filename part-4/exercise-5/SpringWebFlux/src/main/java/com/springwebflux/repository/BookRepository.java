package com.springwebflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.springwebflux.model.Book;

/**
 * BookRepository on Spring Data -rajapinta, eli voit itse määrittää 
 * rajapinnan ja Spring Data luo koodin, joka toteuttaa kyseisen 
 * rajapinnan.
 * 
 * Rajapintaa käytetään normaaleihin kyselyihin Book-dokumentille, kuten
 * findById(), findAll() jne.
 */
public interface BookRepository extends ReactiveMongoRepository<Book, String> {}
