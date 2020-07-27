package com.example.demo.dtos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BookProcessPK implements Serializable{
	@Column(name = "bookID")
	private String bookIDpk;
	@Column(name = "username")
	private String usernamepk;
}
