package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

import com.example.demo.FileProcess;
import com.example.demo.ResourceNotFoundException;
import com.example.demo.dtos.Genre;
import com.example.demo.dtos.Books;
import com.example.demo.dtos.Chapters;
import com.example.demo.repositories.GenreRepository;

@Controller
@RequestMapping("/")
public class GenreController {
	@Autowired
	private GenreRepository genreRepo;
	@RequestMapping("/genre")
    public String getAllGenres(Model model) {
		List<Genre> listGenre = genreRepo.findAll();
		model.addAttribute("listGenre", listGenre);
        return "genre";
    }
	@RequestMapping("/genre/edit/{id}")
	public ModelAndView showEditGenre(@PathVariable(value = "id") String genreID) throws ResourceNotFoundException{
	    ModelAndView mav = new ModelAndView("edit_genre");
	    Genre genre = genreRepo.findByGenreID(Integer.parseInt(genreID));
	    mav.addObject("genre", genre);
	    return mav;
	}
	@RequestMapping(value = "/saveGenre", method = RequestMethod.POST)
	public String saveGenre(@RequestParam("genre") String genreName, @RequestParam("genreID") int genreID) {
		Genre genre = genreRepo.findByGenreID(genreID);
		genre.setGenre(genreName);;
	    genreRepo.save(genre);    
	    return "redirect:/genre";
	}
	@RequestMapping("/genre/delete/{id}")
    public String deleteGenre(@PathVariable(value = "id") String genreID)
         throws ResourceNotFoundException {
        genreRepo.deleteById(Integer.parseInt(genreID));
        return "redirect:/genre";  
    }
	@RequestMapping("/genre/new")
	public String showNewGenre(Model model) {
	    Genre genre = new Genre();
	    model.addAttribute("genres", genre);
	    return "new_genre";
	}
	@RequestMapping(value = "/createGenre", method = RequestMethod.POST)
	public String createGenre(@RequestParam("genre") String genre) {
		Genre genres = new Genre();
		genres.setGenre(genre);
		genreRepo.save(genres);
	    return "redirect:/genre";
	}
}
