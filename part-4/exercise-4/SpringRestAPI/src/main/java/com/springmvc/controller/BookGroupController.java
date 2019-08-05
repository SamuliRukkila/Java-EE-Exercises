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
import com.springmvc.model.Bookgroup;
import com.springmvc.repository.BookRepository;
import com.springmvc.repository.BookgroupRepository;

/**
 * REST-Controller for endpoint "/api/bookgroup". Receives
 * HTTP-requests and process them with repositories and entities.
 * 
 * @author samuli
 */
@RestController
@RequestMapping("/api/bookgroup")
public class BookGroupController {
  
  /**
   * @Autowired -annotation tells Spring to automatically
   * inject these classes into this class without configuration.
   */
  @Autowired
  private BookgroupRepository bgRepo;
  
  @Autowired
  private BookRepository bookRepo;
  
  
  /**
   * GET-request function which fetches all the bookgroups.
   * 
   * @return All the found bookgroups as an array of objects
   */
  @GetMapping
  public List<Bookgroup> getBookgroups() {
    return bgRepo.findAll();
  }
  
  /**
   * GET-request function which finds one bookgroup with it's ID.
   * Uses BookgroupRepository -interface for the SQL-query. Throws
   * exception if it cannot find any.
   * 
   * @param id - Bookgroup's ID
   * @return Bookgroup -object
   * @throws ResourceNotFoundException - If bookgroup cannot be found with the provided ID
   */
  @GetMapping("/{id}")
  public ResponseEntity<Bookgroup> getBookgroupById(@PathVariable(value = "id") Integer id)
      throws ResourceNotFoundException {
    
    Bookgroup bg = bgRepo.findById(id).orElseThrow(() -> 
      new ResourceNotFoundException("Kirjaryhmää ei löytynyt ID:llä: " + id));
    
    return ResponseEntity.ok().body(bg);
  }
  
  
  /**
   * POST-request function which creates a new bookgroup into the sql-table.
   * 
   * @param bg - New bookgroup -object which'll be saved
   * @return Saved bookgroup as an object
   */
  @PostMapping
  public Bookgroup createBookgroup(@Valid @RequestBody Bookgroup bg) {
    return bgRepo.save(bg);
  }
  
  
  /**
   * PUT-request function which updates existing bookgroup. It'll
   * try to find the existing bookgroup-entity with BookgroupRepository 
   * -interface-function. If one is found, it'll overwrite old value 
   * with the new one.
   * 
   * @param id - Bookgroup's ID which user wants to update
   * @param bg - Bookgroup-object which contains new data for the bookgroup
   * @return Updated bookgroup as an object
   * @throws ResourceNotFoundException - Bookgroup cannot be found with provided ID
   */
  @PutMapping("/{id}")
  public Bookgroup updateBookgroup(@PathVariable (value = "id") Integer id, @Valid @RequestBody Bookgroup bg)
      throws ResourceNotFoundException {
    
    return bgRepo.findById(id).map(bookgroup -> {
      bookgroup.setName(bg.getName());
      return bgRepo.save(bookgroup);
    }).orElseThrow(() -> new ResourceNotFoundException(
        "Kirjaryhmää ei löytynyt ID:llä: " + id
    ));
  }
  
  
  /**
   * DELETE-request function which'll delete a bookgroup via provided ID. 
   * 
   * Before bookgroup is deleted, function will call BookRepository -interface's
   * custom native SQL-query which deletes all the books whose own provided
   * bookgroup's ID. Custom query uses @Transactional -annotation so the 
   * changes in there won't be commited if the removal of bookgroup is 
   * unsuccessful.
   * 
   * Using BookgroupRepository -interface, it'll try to find the bookgroup 
   * with ID and if it founds it, that bookgroup will be deleted.
   * 
   * @param id - Bookgroup's ID which'll be deleted
   * @return Deleted bookgroup as an object
   * @throws ResourceNotFoundException - Bookgroup cannot be found with provided ID
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteBook(@PathVariable (value = "id") Integer id) 
      throws ResourceNotFoundException {
    
    bookRepo.deleteBooksByBookgroupId(id);
    
    return bgRepo.findById(id).map(bg -> {
      bgRepo.delete(bg);
      return ResponseEntity.ok().body(bg);
    }).orElseThrow(() -> new ResourceNotFoundException("Kirjaa ei löytynyt ID:llä: " + id));
  }
  
}
