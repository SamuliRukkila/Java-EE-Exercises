package com.springmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springmvc.model.Bookgroup;

/**
 * Spring's repository -interface which allows one to create simple,
 * ready-made ORM-queries for the table "bookgroup".
 * 
 * Two parameters for the JpaRepository in which the BookgroupRepository is
 * extended for are JPA-class name, and primary key -value.
 * 
 * @Repository -annotation tells Spring to bootstrap the repository
 * during the component scan.
 * 
 * @author samuli
 */
@Repository
public interface BookgroupRepository extends JpaRepository<Bookgroup, Integer> {}
