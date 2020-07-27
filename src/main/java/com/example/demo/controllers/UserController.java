package com.example.demo.controllers;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dropbox.core.v2.sharing.UserInfo;
import com.example.demo.FileProcess;
import com.example.demo.ResourceNotFoundException;
import com.example.demo.dtos.Books;
import com.example.demo.dtos.Chapters;
import com.example.demo.dtos.UsersInfo;
import com.example.demo.repositories.UserRepository;

@Controller
@RequestMapping("/")
public class UserController {

	@Autowired
	private UserRepository userRepo;
	@RequestMapping("/login") 
	public String checkLogin(@RequestParam("username") String username, @RequestParam("pass") String password, HttpServletRequest request){
		Optional<UsersInfo> user1 = userRepo.findById(username);
		if (!user1.isPresent()) {
			return "redirect:/";
		}
		if (user1.get().getPassword().compareToIgnoreCase(password) == 0) {
			request.getSession().setAttribute("USER", user1.get().getUsername());
			request.getSession().setAttribute("ROLE", user1.get().isAdmin());
			return "redirect:/books";
		}
		else
			return "redirect:/";
	} 
	@RequestMapping("/user/edit")
	public ModelAndView showEditUser() throws ResourceNotFoundException{
	    ModelAndView mav = new ModelAndView("edit_user");
	    List<UsersInfo> listUser = userRepo.findAll();
	    mav.addObject("listUsers", listUser);
	    return mav;
	}
	@RequestMapping(value = "/user/set/{username}")
	public String saveUser(@PathVariable(value="username") String username) {
		Optional<UsersInfo> user = userRepo.findById(username);
		if (user.get().isAdmin()) {
			user.get().setAdmin(false);
		} else
			user.get().setAdmin(true);
		
		userRepo.save(user.get());
	    return "redirect:/user/edit";
	}
	@RequestMapping("/logout")
	public String checkLogout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/";
	}
}
