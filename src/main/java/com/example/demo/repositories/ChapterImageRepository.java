package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.dtos.ChapterImages;
import com.example.demo.dtos.Chapters;

@Repository
public interface ChapterImageRepository extends JpaRepository<ChapterImages, String>{
	@Query("SELECT t FROM ChapterImages t Where t.chapter_id = ?1")
	List<ChapterImages> findByChapterID(String chapterID);

}
