package com.example.demo.controllers;


import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.FileProcess;
import com.example.demo.ResourceNotFoundException;
import com.example.demo.dtos.Books;
import com.example.demo.dtos.ChapterImages;
import com.example.demo.dtos.Chapters;
import com.example.demo.repositories.ChapterImageRepository;

@Controller
public class ChapterImageController {
	@Autowired
	private ChapterImageRepository imgRepo;
	@RequestMapping("/chapter/edit/{id}")
	public ModelAndView showEditChapter(@PathVariable(value = "id") String chapterId) throws ResourceNotFoundException{
	    ModelAndView mav = new ModelAndView("edit_chapter");
	    List<ChapterImages> chap = imgRepo.findByChapterID(chapterId);
	    mav.addObject("listImages", chap);
	    return mav;
	}
	@RequestMapping("/chapter/edit/image/{id}")
	public ModelAndView showEditImage(@PathVariable(value = "id") String imageId) throws ResourceNotFoundException{
	    ModelAndView mav = new ModelAndView("edit_image");
	    Optional<ChapterImages> img = imgRepo.findById(imageId);
	    mav.addObject("images", img);
	    return mav;
	}
	@RequestMapping(value = "/editImageChapter", method = RequestMethod.POST)
	public String editImageChapter(@RequestParam("file") MultipartFile file, @RequestParam("id") String id) {
		String fileName = FileProcess.uploadToDropBox(file);	     
		Optional<ChapterImages> img = imgRepo.findById(id);
		img.get().setUrl(fileName);
		imgRepo.save(img.get());
		String chapterId = img.get().getChapter_id();
	    return "redirect:/chapter/edit/" + chapterId;
	}
	@RequestMapping("/chapter/delete/image/{id}")
    public String deleteImage(@PathVariable(value = "id") String id)
         throws ResourceNotFoundException {
        ChapterImages img = imgRepo.findById(id)
       .orElseThrow(() -> new ResourceNotFoundException("Chapter not found for this id : " + id));
        String chapterId = img.getChapter_id();
        imgRepo.delete(img);
        return "redirect:/chapter/edit/" + chapterId; 
    }
}
