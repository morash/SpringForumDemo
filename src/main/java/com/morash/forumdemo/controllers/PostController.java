/**
 * 
 */
package com.morash.forumdemo.controllers;

import java.util.ArrayList;
import java.util.Set;

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
import com.morash.forumdemo.data.entity.Comment;
import com.morash.forumdemo.data.entity.Post;
import com.morash.forumdemo.exceptions.BoardNotFoundException;
import com.morash.forumdemo.exceptions.PostNotFoundException;
import com.morash.forumdemo.exceptions.UserNotLoggedInException;
import com.morash.forumdemo.services.BoardService;
import com.morash.forumdemo.services.LoginService;
import com.morash.forumdemo.services.PostService;

/**
 * @author Michael
 *
 */
@RestController
@RequestMapping({"api/board/~/post", "api/board/{boardName}/post"})
public class PostController {
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private PostService postService;
	
	@GetMapping("/") 
	public ArrayList<Post> boardIndex(@PathVariable(name="boardName", required=false) String boardName) throws BoardNotFoundException {
		if (boardName != null) {
			return postService.getPostsForBoard(boardName);
		}
		
		return postService.getAllPosts();
	}
	
	@GetMapping("/{postId}")
	public Post view(@PathVariable(name="postId", required=true) Integer postId) throws PostNotFoundException {
		return postService.getPostById(postId);
	}
	
	@GetMapping("/create")
	public ModelAndView create(RedirectAttributes attributes, ModelMap model,
			@PathVariable(name="boardName", required=true) String boardName) throws BoardNotFoundException {
		// Gives the form to create a post in the board with boardName
		// Redirects to login page if user isn't logged in
		// Throws 404 if no board exists with boardName
		
		Board b = boardService.getBoardByName(boardName);
		
		model.addAttribute(ModelKeyNames.BOARD, b);
		
		return new ModelAndView(JspPaths.POST_CREATE, model);
	}
	
	@PostMapping("/create")
	public RedirectView create(RedirectAttributes attributes,
			@PathVariable(name="boardName", required=true) String boardName, Post newPost) throws BoardNotFoundException, UserNotLoggedInException {
		// Creates a new post using the post parameters
		// Redirects to login page if no user is logged in
		// Throws 404 if no board exists with boardName
		
		Board board = boardService.getBoardByName(boardName);
		
		postService.createPost(board, newPost);
		
		return new RedirectView("/board/view/" + board.getName());
	}
}
