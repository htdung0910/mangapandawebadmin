package com.example.demo.dtos;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Chapter_image")
public class ChapterImages implements Serializable{

	@Id
	private String id;
	private String chapter_id;
	private String url;
	public ChapterImages(String id, String chapter_id, String url) {
		super();
		this.id = id;
		this.chapter_id = chapter_id;
		this.url = url;
	}
	public ChapterImages() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getChapter_id() {
		return chapter_id;
	}
	public void setChapter_id(String chapter_id) {
		this.chapter_id = chapter_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
