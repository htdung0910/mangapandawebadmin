package com.example.demo.dtos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="Genres")
public class Genre implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int genreID;
	private String genre;
	@ManyToMany(mappedBy = "bookGenre")
	Set<Books> genreBook = new HashSet<Books>();
	
	public Genre(int genreID, String genre) {
		super();
		this.genreID = genreID;
		this.genre = genre;
	}
	
	
	public Genre() {
		super();
	}


	public int getGenreID() {
		return genreID;
	}


	public void setGenreID(int genreID) {
		this.genreID = genreID;
	}


	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public Set<Books> getGenreBook() {
		return genreBook;
	}
	public void setGenreBook(Set<Books> genreBook) {
		this.genreBook = genreBook;
	}
	
}
