package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.dtos.Chapters;

@Repository
public interface ChapterRepository extends JpaRepository<Chapters, String>{
	@Query("SELECT t FROM Chapters t Where t.bookID = ?1 order by length(t.chapterID),t.chapterID ASC")
	List<Chapters> findByBookID(String bookID);
	
	Long countByBookID(String bookID);
}
