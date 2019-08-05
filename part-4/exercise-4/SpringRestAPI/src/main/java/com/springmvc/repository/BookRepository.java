package com.springmvc.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springmvc.model.Book;

/**
 * Spring's repository -interface which allows one to create simple,
 * ready-made ORM-queries for the table "book".
 * 
 * Two parameters for the JpaRepository in which the BookRepository is
 * extended for are JPA-class name, and primary key -value.
 * 
 * @Repository -annotation tells Spring to bootstrap the repository
 * during the component scan.
 * 
 * @author samuli
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
  
  /**
   * Custom native SQL-query which can't found from 
   * JpaRepository-interface. Deletes all the books whose 
   * have specific book-group's ID.
   * 
   * This custom query is @Transactional, so if the calling method
   * is unsuccessful in it's queries, this query won't be committed.
   * 
   * @param id - Bookgroup's ID
   */
  @Modifying
  @Transactional
  @Query(value = "DELETE FROM book WHERE bookgroup_id = ?1", nativeQuery = true)
  void deleteBooksByBookgroupId(Integer id);
  
}
