/**
 * 
 */
package com.morash.forumdemo.controllers;

import java.time.LocalDateTime;
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
import com.morash.forumdemo.data.entity.Comment;
import com.morash.forumdemo.data.entity.Post;
import com.morash.forumdemo.data.entity.User;
import com.morash.forumdemo.data.repository.BoardRepository;
import com.morash.forumdemo.data.repository.CommentRepository;
import com.morash.forumdemo.data.repository.PostRepository;

/**
 * @author Michael
 *
 */
@Controller
@RequestMapping("/post")
public class PostController {
	@Autowired
	private BoardRepository boardRepo;
	
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private CommentRepository commentRepo;
	
	@GetMapping("/create/{boardName}")
	public ModelAndView create(RedirectAttributes attributes, HttpServletRequest request, ModelMap model, @PathVariable(name="boardName", required=true) String boardName) {
		// Gives the form to create a post in the board with boardName
		// Redirects to login page if user isn't logged in
		// Throws 404 if no board exists with boardName
		
		HttpSession session = request.getSession();
		Optional<Board> b = boardRepo.findBoardByName(boardName);
		
		if (session.getAttribute(SessionKeyNames.USER_KEY) == null) {
			attributes.addFlashAttribute(ModelKeyNames.ERROR_MESSAGE, ErrorMessages.POST_NO_USER);
			return new ModelAndView("redirect:/user/login", model);
		}
		
		if (b.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No board found with name " + boardName);
		}
		
		model.addAttribute(ModelKeyNames.BOARD, b.get());
		
		return new ModelAndView(JspPaths.POST_CREATE, model);
	}
	
	@PostMapping("/create/{boardName}")
	public RedirectView create(HttpServletRequest request, RedirectAttributes attributes, @PathVariable(name="boardName", required=true) String boardName, Post newPost) {
		// Creates a new post using the post parameters
		// Redirects to login page if no user is logged in
		// Throws 404 if no board exists with boardName
		
		HttpSession session = request.getSession();
		Optional<Board> b = boardRepo.findBoardByName(boardName);
		
		if (session.getAttribute(SessionKeyNames.USER_KEY) == null) {
			attributes.addFlashAttribute(ModelKeyNames.ERROR_MESSAGE, ErrorMessages.POST_NO_USER);
			return new RedirectView("/user/login");
		}
		
		if (b.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No board found with name " + boardName);
		}
		
		// Finish instantiation with board from url, currently logged in user, and current time
		newPost.setBoard(b.get());
		newPost.setPoster((User) session.getAttribute(SessionKeyNames.USER_KEY));
		newPost.setPostDate(LocalDateTime.now());
		
		postRepo.save(newPost);
		
		return new RedirectView("/board/view/" + b.get().getName());
	}
	
	@GetMapping("/view/{postId}")
	public String view(Model model, HttpServletRequest request, @PathVariable(value = "postId") Integer postId) {
		// Shows the post passing top-level comment in through model
		// Throws 404 if no post with postId
		
		Optional<Post> viewPost = postRepo.findById(postId);
		
		if (viewPost.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No post found with id " + postId);
		}
		
		Set<Comment> topLevelComments = commentRepo.getTopLevelCommentsForPost(viewPost.get(), Sort.by(Direction.DESC, "postDate"));
		
		model.addAttribute(ModelKeyNames.POST, viewPost.get());
		model.addAttribute(ModelKeyNames.COMMENT_LIST, topLevelComments);
		
		return JspPaths.POST_VIEW;
	}
}
