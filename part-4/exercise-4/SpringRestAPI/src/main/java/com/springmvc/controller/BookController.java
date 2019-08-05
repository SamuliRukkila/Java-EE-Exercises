package com.springmvc.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springmvc.exception.ResourceNotFoundException;
import com.springmvc.model.Book;
import com.springmvc.model.Bookgroup;
import com.springmvc.repository.BookRepository;
import com.springmvc.repository.BookgroupRepository;

/**
 * REST-Controller for endpoint "/api/book". Receives
 * HTTP-requests and process them with repositories and entities.
 * 
 * @author samuli
 */
@RestController
@RequestMapping("/api/book")
public class BookController {
  
  /**
   * @Autowired -annotation tells Spring to automatically
   * inject these classes into this class without configuration.
   */
  @Autowired
  private BookRepository bookRepo;
  
  @Autowired
  private BookgroupRepository bgRepo;
  
  
  /**
   * GET-request function which fetches all the books
   * and bookgroups they're in contact with.
   * 
   * @return All the found books
   */
  @GetMapping
  public List<Book> getBooks() {
    return bookRepo.findAll();
  }
  
  /**
   * GET-request function which finds one book with it's ID.
   * Uses BookRepository -interface for the SQL-query. Throws
   * exception if it cannot find any.
   * 
   * @param id - Book's ID
   * @return Book-object
   * @throws ResourceNotFoundException - If book cannot be found with the ID
   */
  @GetMapping("/{id}")
  public ResponseEntity<Book> getBookById(@PathVariable(value = "id") Integer id) 
      throws ResourceNotFoundException {
    
    Book book = bookRepo.findById(id).orElseThrow(() -> 
      new ResourceNotFoundException("Kirjaa ei löytynyt ID:llä: " + id));
    
    return ResponseEntity.ok().body(book);
  }
  
  
  /**
   * POST-request function which creates a new book into the sql-table.
   * Because all books need to be included in one bookgroup, function will
   * firstly find the bookgroup by the id user has given via parameter. 
   * 
   * If the bookgroup is found, we'll add that bookgroup to the book-object,
   * which then will be saved to the table
   * 
   * @param bookgroup_id - Bookgroup's ID which'll be included in the book
   * @param book - Book-object which contains all the necessary data
   * @return Saved book -object
   * @throws ResourceNotFoundException - If bookgroup cannot be found with provided ID
   */
  @PostMapping("/{id}")
  public Book createBook(@PathVariable (value = "id") Integer bookgroup_id, @Valid @RequestBody Book book) 
      throws ResourceNotFoundException {
    
    return bgRepo.findById(bookgroup_id).map(bookGroup -> {
      book.setBookgroup(bookGroup);
      return bookRepo.save(book);
    }).orElseThrow(() -> new ResourceNotFoundException(
        "Kirjaryhmää ei löytynyt ID:llä: " + bookgroup_id
    ));
  }
  
  
  /**
   * PUT-request function which updates existing book and the bookgroup 
   * it's linked with.
   * 
   * Function will first find the bookgroup-entity via provided ID. After
   * it has found it, function'll find the existing book via provided ID
   * and save new values into it. 
   * 
   * @param id - Book's ID which user wants to update
   * @param book - Book-object which contains new data for the book
   * @return Updated book as an object
   * @throws ResourceNotFoundException - Book/bookgroup cannot be found with provided ID
   */
  @PutMapping("/{id}")
  public Book updateBook(@PathVariable (value = "id") Integer id, @Valid @RequestBody Book book)
      throws ResourceNotFoundException {
    
    Bookgroup bg = bgRepo.findById(book.getBookgroup().getId())
        .orElseThrow(() -> new ResourceNotFoundException("Kirjaryhmää ei löytynyt ID:llä: " + book.getBookgroup().getId()));
    
    return bookRepo.findById(id).map(b -> {
      b.setAuthor(book.getAuthor());
      b.setTitle(book.getTitle());
      b.setBookgroup(bg);
      return bookRepo.save(b);
    }).orElseThrow(() -> new ResourceNotFoundException("Kirjaa ei löytynyt ID:llä: " + id));
  }
  
  
  /**
   * DELETE-request function which'll delete a book via provided ID. 
   * Using BookRepository -interface, it'll try to find the book with ID
   * and if it founds it, that book will be deleted. 
   * 
   * @param id - Book's ID which'll be deleted
   * @return Deleted book as an object
   * @throws ResourceNotFoundException - Book cannot be found with provided ID
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteBook(@PathVariable (value = "id") Integer id) 
      throws ResourceNotFoundException {
    
    return bookRepo.findById(id).map(b -> {
      bookRepo.delete(b);
      return ResponseEntity.ok().body(b);
    }).orElseThrow(() -> new ResourceNotFoundException("Kirjaa ei löytynyt ID:llä: " + id));
  }
  
}
