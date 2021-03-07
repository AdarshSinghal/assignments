package com.vmw.assignments.ui.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.vmw.assignments.ui.service.ArticleService;

/**
 * @author adarsh
 *
 */
@Controller
public class HomeController {

	@Autowired
	private ArticleService articleService;

	@GetMapping("/")
	public String root(Model model) throws URISyntaxException, IOException {
		model.addAttribute("articlesList", articleService.getAricles());
		return "index";
	}

	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}

	@GetMapping("/user")
	public String userIndex() {
		return "user/index";
	}
}
