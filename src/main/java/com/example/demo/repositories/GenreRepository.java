package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.dtos.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer>{
	
	List<Genre> findByGenreBookBookID(String bookID);
	
	List<Genre> findByGenreBookBookIDAndGenre(String bookID, String genre);
	
	Genre findByGenre(String genre);
	
	Genre findByGenreID(int genreID);
}
