package com.example.demo.dtos;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "Book_process")
public class BookProcesses implements Serializable{
	@EmbeddedId
	private BookProcessPK id;
	@ManyToOne
	@MapsId("bookIDpk")
	@JoinColumn(name = "bookID")
	private Books book;
	
	@ManyToOne
	@MapsId("usernamepk")
	@JoinColumn(name = "username")
 	private UsersInfo user;

	@Column(name="isupload", nullable = true)
	private Boolean isUpload;
	
	public BookProcesses(BookProcessPK id, Books book, UsersInfo user) {
		super();
		this.id = id;
		this.book = book;
		this.user = user;
	}
	public BookProcesses() {
		super();
	}
	public Books getBook() {
		return book;
	}
	public void setBook(Books book) {
		this.book = book;
	}
	public UsersInfo getUser() {
		return user;
	}
	public void setUser(UsersInfo user) {
		this.user = user;
	}
	public BookProcessPK getId() {
		return id;
	}
	public void setId(BookProcessPK id) {
		this.id = id;
	}
	public boolean isUpload() {
		return isUpload;
	}
	public void setUpload(boolean isUpload) {
		this.isUpload = isUpload;
	}
	
	
}
