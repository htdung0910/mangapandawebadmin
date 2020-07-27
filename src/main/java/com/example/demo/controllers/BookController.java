package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.expression.Lists;

import com.example.demo.FileProcess;
import com.example.demo.ResourceNotFoundException;
import com.example.demo.dtos.Genre;
import com.example.demo.dtos.BookProcesses;
import com.example.demo.dtos.Books;
import com.example.demo.dtos.Chapters;
import com.example.demo.repositories.BookProcessRepository;
import com.example.demo.repositories.BookRepository;
import com.example.demo.repositories.ChapterRepository;
import com.example.demo.repositories.GenreRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class BookController {
	@Autowired
    private BookRepository bookRepo;
	@Autowired
	private GenreRepository genRepo;
	@Autowired
	private ChapterRepository chapRepo;
	@Autowired
	private BookProcessRepository bookProcessRepo;
	@RequestMapping("/") 
	public String home(){
	    return "login"; 
	} 
	
	@RequestMapping(value = "/books")
    public String getAllBooks(Model model, HttpSession session) {
		if (session.getAttribute("ROLE") == null) {
			return "login";
		}
		boolean role = (boolean) session.getAttribute("ROLE");
		String username = (String) session.getAttribute("USER");
		List<Books> listBooks = new ArrayList<Books>();
		if (username != "") {
			if (role==true) {
				listBooks = bookRepo.findAll();
			} else if(role == false) {
				List<BookProcesses> listBookPro = bookProcessRepo.findById_usernamepkAndIsUpload(username, true);
				for (BookProcesses bookProcesses : listBookPro) {
					listBooks.add(bookProcesses.getBook());
					System.out.println(bookProcesses.getBook());
				}
				
			}
		} else if(username == null){
			return "login";
		}
		model.addAttribute("listBooks", listBooks);
		model.addAttribute("role", role);
		
	     
	    return "index";
    }
	@RequestMapping("/search")
    public String getBookByName(Model model, @RequestParam(value = "search") String search){
		List<Books> listBooks = bookRepo.findByName(search);
		model.addAttribute("listBooks", listBooks);  
        return "index";
    }
	@RequestMapping("/books/new")
	public String showNewBook(Model model) {
	    Books book = new Books();
	    model.addAttribute("book", book);
	    List<Genre> listGenre = genRepo.findAll();
	    model.addAttribute("listGenre", listGenre);
	    return "new_book";
	}
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveBook(@ModelAttribute("book") Books book, @RequestParam("file") MultipartFile file, @RequestParam(value = "list" , required = false) String[] list) {
		String fileName = FileProcess.uploadToDropBox(file);
		book.setBookID(RandomString(16));
		book.setThumnailPath(fileName);	
		if (list != null) {
			List<Genre> tmpList = new ArrayList<Genre>();
			for (String genre : list) {
				Genre tmpGenre = genRepo.findByGenre(genre);
				tmpList.add(tmpGenre);
			}
			Set<Genre> setGenre = new HashSet<Genre>(tmpList);
			book.setBookGenre(setGenre);
		}
		
	    bookRepo.save(book);	     
	    return "redirect:/books";
	}
	@RequestMapping(value = "/saveBook", method = RequestMethod.POST)
	public String saveBookExist(@ModelAttribute("book") Books book, @RequestParam(value = "list" , required = false) String[] list) {
		if (list != null) {
			List<Genre> tmpList = new ArrayList<Genre>();
			for (String genre : list) {
				Genre tmpGenre = genRepo.findByGenre(genre);
				tmpList.add(tmpGenre);
			}
			Set<Genre> setGenre = new HashSet<Genre>(tmpList);
			book.setBookGenre(setGenre);
		}
		System.out.println(book.getBookGenre());
	    bookRepo.save(book);	     
	    return "redirect:/books/edit/" + book.getBookID();  
	}
	@RequestMapping("/books/edit/{id}")
	public ModelAndView showEditBook(@PathVariable(value = "id") String bookId) throws ResourceNotFoundException{
	    ModelAndView mav = new ModelAndView("edit_book");
	    Books book = bookRepo.findById(bookId)
	            .orElseThrow(() -> new ResourceNotFoundException("Book not found for this id : " + bookId));
	    List<Chapters> listChap = chapRepo.findByBookID(bookId);
	    List<Genre> listGenre = genRepo.findByGenreBookBookID(bookId);
	    mav.addObject("book", book);
	    mav.addObject("listChaps", listChap);
	    mav.addObject("bookID", bookId);
	    mav.addObject("listGenre", listGenre);
	    return mav;
	}
	@RequestMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable(value = "id") String bookId)
         throws ResourceNotFoundException {
        bookRepo.deleteById(bookId);
        return "redirect:/books";  
    }
	public static String RandomString(int length)
    {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }
}

