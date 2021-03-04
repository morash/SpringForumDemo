package com.morash.forumdemo.controllers;

import java.net.http.HttpRequest;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.morash.forumdemo.data.constants.ErrorMessages;
import com.morash.forumdemo.data.constants.JspPaths;
import com.morash.forumdemo.data.constants.ModelKeyNames;
import com.morash.forumdemo.data.constants.SessionKeyNames;
import com.morash.forumdemo.data.entity.Board;
import com.morash.forumdemo.data.entity.Post;
import com.morash.forumdemo.data.entity.User;
import com.morash.forumdemo.data.repository.BoardRepository;
import com.morash.forumdemo.data.repository.PostRepository;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardRepository boardRepo;
	
	@Autowired
	private PostRepository postRepo;
	
	@GetMapping("/")
	public String index(Model model, HttpServletRequest request) {
		// Serves the board index page
		// TODO add pagination
		model.addAttribute(ModelKeyNames.BOARD_INDEX_LIST, boardRepo.findAll());
		
		return JspPaths.BOARD_INDEX;
	}
	
	@GetMapping("/create")
	public ModelAndView create(RedirectAttributes attributes, HttpServletRequest request, ModelMap model) {
		// Serves the form page to create a new board
		// Redirects to login page if user isn't registered to session
		
		HttpSession session = request.getSession();
		
		if (session.getAttribute(SessionKeyNames.USER_KEY) == null) {
			attributes.addFlashAttribute(ModelKeyNames.ERROR_MESSAGE, ErrorMessages.BOARD_NO_USER);
			return new ModelAndView("redirect:/user/login", model);
		}
		
		ModelAndView showCreatePage = new ModelAndView(JspPaths.BOARD_CREATE, model);
		
		return showCreatePage;
	}
	
	@PostMapping("/create")
	public RedirectView create(RedirectAttributes attributes, HttpServletRequest request, Board newBoard) {
		// Creates a new board from post parameters
		// Redirects to login page if user isn't logged in
		// Otherwise redirects to view the new board on successful completion
		HttpSession session = request.getSession();
		
		if (session.getAttribute(SessionKeyNames.USER_KEY) == null) {
			attributes.addFlashAttribute(ModelKeyNames.ERROR_MESSAGE, ErrorMessages.BOARD_NO_USER);
			return new RedirectView("/user/login");
		}
		
		newBoard.setCreatedBy((User) session.getAttribute(SessionKeyNames.USER_KEY));
		
		boardRepo.save(newBoard);
		
		return new RedirectView("/board/view" + newBoard.getName());
	}
	
	@GetMapping("/view/{boardName}")
	public String view(Model model, HttpServletRequest request, @PathVariable(name="boardName") String boardName) {
		// Shows the board with boardName
		// Throws 404 if no board exists with name
		
		Optional<Board> b = boardRepo.findBoardByName(boardName);
		
		if (b.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No board found with name " + boardName);
		}
		
		Set<Post> boardPosts = postRepo.getPostsForBoard(b.get(), Sort.by(Direction.DESC, "postDate"));
		
		model.addAttribute(ModelKeyNames.BOARD, b.get());
		model.addAttribute(ModelKeyNames.POST_LIST, boardPosts);
		
		return JspPaths.BOARD_VIEW;
	}
}
