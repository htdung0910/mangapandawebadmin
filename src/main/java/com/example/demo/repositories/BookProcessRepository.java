package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.dtos.BookProcessPK;
import com.example.demo.dtos.BookProcesses;
import com.example.demo.dtos.Books;

@Repository
public interface BookProcessRepository extends JpaRepository<BookProcesses, BookProcessPK>{
	List<BookProcesses> findById_usernamepkAndIsUpload(String username, boolean IsUpload);
}
