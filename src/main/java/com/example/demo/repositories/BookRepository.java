package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.dtos.Books;
import com.example.demo.dtos.ChapterImages;

@Repository
public interface BookRepository extends JpaRepository<Books, String>{
	@Query("SELECT t FROM Books t Where t.title LIKE CONCAT('%',:title,'%')")
	List<Books> findByName(@Param("title") String title);
	
	
	
}
