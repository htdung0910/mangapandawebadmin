package com.example.demo.controllers;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.example.demo.FileProcess;
import com.example.demo.ResourceNotFoundException;
import com.example.demo.dtos.Books;
import com.example.demo.dtos.ChapterImages;
import com.example.demo.dtos.Chapters;
import com.example.demo.repositories.ChapterImageRepository;
import com.example.demo.repositories.ChapterRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/")
public class ChapterController {
	private Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	@Autowired
	private ChapterRepository chapterRepo;
	@Autowired
	private ChapterImageRepository imgRepo;
	@GetMapping("/chapters")
    public List<Chapters> getAllChapters() {
        return chapterRepo.findAll();
    }
	@RequestMapping(value = "/saveChapter", method = RequestMethod.POST)
	public String saveChapter(@ModelAttribute("chapter") Chapters chapter, @RequestParam("files") MultipartFile[] files) {	
		chapter.setChapterID(chapter.getBookID() + "-");
		long chapterNum = chapterRepo.countByBookID(chapter.getBookID());
		chapter.setChapterID(chapter.getBookID() + "-" + (chapterNum + 1));
		chapter.setUploadDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
		chapterRepo.save(chapter);
		List<String> listImage = Arrays.asList(files).stream().map(file -> FileProcess.uploadToDropBox(file)).collect(Collectors.toList());
		int i = 1;
		for (String string : listImage) {
			ChapterImages chapImg = new ChapterImages();
			chapImg.setChapter_id(chapter.getChapterID());
			chapImg.setId(chapter.getChapterID() + "-" + i);
			chapImg.setUrl(string);
			imgRepo.save(chapImg);
			i++;
		}
	    return "redirect:/books/edit/" + chapter.getBookID();
	}
	@RequestMapping("/chapter/new/{id}")
	public String showNewChapter(Model model, @PathVariable(value = "id") String bookID) {
	    Chapters chapter = new Chapters();
	    chapter.setBookID(bookID);
	    model.addAttribute("chapter", chapter);
	    return "new_chapter";
	}
	@RequestMapping("/chapter/delete/{id}")
    public String deleteChapter(@PathVariable(value = "id") String chapterId)
         throws ResourceNotFoundException {
        Chapters chapter = chapterRepo.findById(chapterId)
       .orElseThrow(() -> new ResourceNotFoundException("Book not found for this id : " + chapterId));
        List<ChapterImages> listImg = imgRepo.findByChapterID(chapterId);
        for (ChapterImages chapterImages : listImg) {
			imgRepo.delete(chapterImages);
		}
        chapterRepo.delete(chapter);
        return "redirect:/books/edit/" + chapter.getBookID();  
    }
	
	public ResponseEntity<String> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return new ResponseEntity<>(gson.toJson(
                                Arrays.asList(files).stream()
                                .map(file -> FileProcess.uploadToDropBox(file))
                                .collect(Collectors.toList())),
                HttpStatus.OK);
    }
}
