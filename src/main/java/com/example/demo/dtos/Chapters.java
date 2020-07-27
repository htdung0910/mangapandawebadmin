package com.example.demo.dtos;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Chapter")
public class Chapters implements Serializable{

	@Id
	private String chapterID;
	private String bookID;
	private String chapterName;
	private Date uploadDate;
	
	public Chapters(String chapterID, String bookID, String chapterName, Date uploadDate) {
		super();
		this.chapterID = chapterID;
		this.bookID = bookID;
		this.chapterName = chapterName;
		this.uploadDate = uploadDate;
	}
	public Chapters() {
		super();
	}
	public String getChapterID() {
		return chapterID;
	}
	public void setChapterID(String chapterID) {
		this.chapterID = chapterID;
	}
	public String getBookID() {
		return bookID;
	}
	public void setBookID(String bookID) {
		this.bookID = bookID;
	}
	public String getChapterName() {
		return chapterName;
	}
	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	
	
	
}
