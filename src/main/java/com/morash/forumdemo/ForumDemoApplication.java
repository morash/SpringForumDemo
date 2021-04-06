package com.morash.forumdemo;

import java.security.Principal;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.StaticResourceLocation;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.morash.forumdemo.data.constants.ModelKeyNames;
import com.morash.forumdemo.data.entity.Board;
import com.morash.forumdemo.data.repository.BoardRepository;
import com.morash.forumdemo.services.LoginService;

@SpringBootApplication
@Controller
public class ForumDemoApplication extends SpringBootServletInitializer {
	@Autowired
	BoardRepository boardRepo;
	
	@Autowired
	LoginService loginService;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ForumDemoApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ForumDemoApplication.class, args);
	}

	@GetMapping("/")
	public String hello(Model model, HttpSession session) {
		Set<Board> topBoardList = boardRepo.getTopBoards();
		model.addAttribute(ModelKeyNames.TOP_BOARD_LIST, topBoardList);

		return "home";
	}

	//@GetMapping("/error")
	public String error() {
		return "error";
	}
}
