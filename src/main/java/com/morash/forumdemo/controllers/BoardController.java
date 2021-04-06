package com.morash.forumdemo.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.morash.forumdemo.data.constants.ErrorMessages;
import com.morash.forumdemo.data.constants.JspPaths;
import com.morash.forumdemo.data.constants.ModelKeyNames;
import com.morash.forumdemo.data.entity.Board;
import com.morash.forumdemo.data.entity.Post;
import com.morash.forumdemo.exceptions.BoardNotFoundException;
import com.morash.forumdemo.exceptions.UserNotLoggedInException;
import com.morash.forumdemo.services.BoardService;
import com.morash.forumdemo.services.LoginService;

@RestController
@RequestMapping("/api/board")
public class BoardController {
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/")
	public ArrayList<Board> index(Model model) {
		// Serves the board index page
		// TODO add pagination
		ArrayList<Board> boardIndex = boardService.getBoardIndex();
		
		return boardIndex;
	}
	
	@GetMapping("/{boardName}")
	public Board view(Model model, @PathVariable(name="boardName") String boardName) throws BoardNotFoundException {
		Board board = boardService.getBoardByName(boardName);
		
		return board;
	}
	
	@GetMapping("/create")
	public ModelAndView create(RedirectAttributes attributes, ModelMap model) {
		// Serves the form page to create a new board
		// Redirects to login page if user isn't registered to session
		ModelAndView showCreatePage = new ModelAndView(JspPaths.BOARD_CREATE, model);
		
		return showCreatePage;
	}
	
	@PostMapping("/create")
	public RedirectView create(RedirectAttributes attributes, Board newBoard) throws UserNotLoggedInException {
		// Creates a new board from post parameters
		// Otherwise redirects to view the new board on successful completion
		
		boardService.createBoard(newBoard);
		return new RedirectView("/board/view" + newBoard.getName());
	}
}
