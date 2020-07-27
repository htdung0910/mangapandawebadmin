package com.example.demo.dtos;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Nationalized;

import com.sun.istack.NotNull;

@Entity
@Table(name="Book")
public class Books implements Serializable{

	@Id
	private String bookID;
	private String title;
	private float ratingValue;
	private float ratingCount;
	private String thumnailPath;
	private String author;
	@Column(name = "islogin")
	private boolean mustLogin;
	private String detailContent;
	@OneToMany(mappedBy = "book")
	private Set<BookProcesses> bookProcesses = new HashSet<BookProcesses>();
	@ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH}, fetch = FetchType.LAZY)
	@JoinTable(name = "Book_genres", joinColumns = @JoinColumn(name = "bookID"), inverseJoinColumns = @JoinColumn(name = "genreID"))
	private Set<Genre> bookGenre = new HashSet<Genre>();
	
	
	public Books(String bookID, String title, float ratingValue, float ratingCount, String thumnailpath,
			String detailContent, String author, boolean mustLogin) {
		super();
		this.bookID = bookID;
		this.title = title;
		this.ratingValue = ratingValue;
		this.ratingCount = ratingCount;
		this.thumnailPath = thumnailpath;
		this.detailContent = detailContent;
		this.author = author;
		this.mustLogin = mustLogin;
	}
	public Books() {
		super();
	}
	public String getBookID() {
		return bookID;
	}
	public void setBookID(String bookID) {
		this.bookID = bookID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public boolean isMustLogin() {
		return mustLogin;
	}
	public void setMustLogin(boolean mustLogin) {
		this.mustLogin = mustLogin;
	}
	public float getRatingValue() {
		return ratingValue;
	}
	public void setRatingValue(float ratingValue) {
		this.ratingValue = ratingValue;
	}
	public float getRatingCount() {
		return ratingCount;
	}
	public void setRatingCount(float ratingCount) {
		this.ratingCount = ratingCount;
	}
	public String getThumnailPath() {
		return thumnailPath;
	}
	public void setThumnailPath(String thumnailPath) {
		this.thumnailPath = thumnailPath;
	}
	public String getDetailContent() {
		return detailContent;
	}
	public void setDetailContent(String detailContent) {
		this.detailContent = detailContent;
	}
	public Set<Genre> getBookGenre() {
		return bookGenre;
	}
	public void setBookGenre(Set<Genre> bookGenre) {
		this.bookGenre = bookGenre;
	}
	
	
	
	
}
