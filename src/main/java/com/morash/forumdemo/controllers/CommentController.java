/**
 * 
 */
package com.morash.forumdemo.controllers;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.morash.forumdemo.data.entity.Comment;
import com.morash.forumdemo.data.entity.Post;
import com.morash.forumdemo.data.entity.User;
import com.morash.forumdemo.data.repository.CommentRepository;
import com.morash.forumdemo.data.repository.PostRepository;

/**
 * @author Michael
 *
 */

@Controller
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private CommentRepository commentRepo;
	
	@GetMapping("/create/respondtopost/{postId}")
	public ModelAndView createForPost(RedirectAttributes attributes, ModelMap model, HttpServletRequest request, @PathVariable(name="postId", required=true) Integer postId) {
		// Serves form to create a new top-level comment for post
		// Redirects to login page if user is not currently logged in
		// Throws 404 if no post exists with post id
		
		HttpSession session = request.getSession();
		Optional<Post> respondToPost = postRepo.findById(postId);
		
		if (session.getAttribute(SessionKeyNames.USER_KEY) == null) {
			attributes.addFlashAttribute(ModelKeyNames.ERROR_MESSAGE, ErrorMessages.COMMENT_NO_USER);
			return new ModelAndView("redirect:/user/login", model);
		}
		
		if (respondToPost.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No post found with id " + postId);
		}
		
		model.addAttribute(ModelKeyNames.POST, respondToPost.get());
		
		return new ModelAndView(JspPaths.COMMENT_CREATE_FOR_POST, model);
	}
	
	@PostMapping("/create/respondtopost/{postId}")
	public RedirectView createForPost(RedirectAttributes attributes, HttpServletRequest request, @PathVariable(name="postId", required=true) Integer postId, Comment newComment) {
		// Creates a new comment using post parameters
		// Redirects to login page if no user is currently logged in
		// Returns 404 if no post exists with postId
		
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute(SessionKeyNames.USER_KEY);
		Optional<Post> respondToPost = postRepo.findById(postId);
		
		if (currentUser == null) {
			attributes.addFlashAttribute(ModelKeyNames.ERROR_MESSAGE, ErrorMessages.COMMENT_NO_USER);
			return new RedirectView("/user/login");
		}
		
		if (respondToPost.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No post found with id " + postId);
		}
		
		// Finish instantiation by adding currently logged in user, current time, and the post to respond to
		newComment.setPoster(currentUser);
		newComment.setPostDate(LocalDateTime.now());
		newComment.setRespondingToPost(respondToPost.get());
		
		commentRepo.save(newComment);

		return new RedirectView("/post/view/" + postId);
	}
	
	@GetMapping("/create/respondtocomment/{commentId}")
	public ModelAndView createForComment(RedirectAttributes attributes, ModelMap model, HttpServletRequest request, @PathVariable(name="commentId", required=true) Integer commentId) {
		// Serves the form to create a new comment in response to another comment
		// Redirects to login page if no user is currently logged in
		// Returns 404 if no comment with commentId exists
		
		HttpSession session = request.getSession();
		Optional<Comment> respondToComment = commentRepo.findById(commentId);
		
		if (session.getAttribute(SessionKeyNames.USER_KEY) == null) {
			attributes.addFlashAttribute(ModelKeyNames.ERROR_MESSAGE, ErrorMessages.COMMENT_NO_USER);
			return new ModelAndView("redirect:/user/login", model);
		}
		
		if (respondToComment.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No comment found with id " + commentId);
		}
		
		model.addAttribute(ModelKeyNames.POST, respondToComment.get().getRespondingToPost());
		model.addAttribute(ModelKeyNames.COMMENT, respondToComment.get());
		
		return new ModelAndView(JspPaths.COMMENT_CREATE_FOR_COMMENT, model);
	}
	
	@PostMapping("/create/respondtocomment/{commentId}")
	public RedirectView createForComment(RedirectAttributes attributes, HttpServletRequest request, @PathVariable(name="commentId", required=true) Integer commentId, Comment newComment) {
		// Creates new comment from post parameters in response to another comment
		// Redirects to login if no user is currently logged in
		// Returns 404 if no comment exists with commentId
		
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute(SessionKeyNames.USER_KEY);
		Optional<Comment> respondToComment = commentRepo.findById(commentId);
		
		if (currentUser == null) {
			attributes.addFlashAttribute(ModelKeyNames.ERROR_MESSAGE, ErrorMessages.COMMENT_NO_USER);
			return new RedirectView("/user/login");
		}
		
		if (respondToComment.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No comment found with id " + commentId);
		}
		
		// Finish instantiating by adding currently logged in user, current time, the comment that this is responding to
		// and the post that this is responding to
		newComment.setPoster(currentUser);
		newComment.setPostDate(LocalDateTime.now());
		newComment.setRespondingToComment(respondToComment.get());
		newComment.setRespondingToPost(respondToComment.get().getRespondingToPost());
		
		commentRepo.save(newComment);
		
		return new RedirectView("/comment/view/" + newComment.getId());
	}
	
	@GetMapping("/view/{commentId}")
	public String view(Model model, HttpServletRequest request, @PathVariable(name="commentId") Integer commentId) {
		// Shows the comment
		// Returns 404 if no comment exists with commentId
		
		Optional<Comment> comment = commentRepo.findById(commentId);
		
		if (comment.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No comment found with id " + commentId);
		}
		
		model.addAttribute(ModelKeyNames.COMMENT, comment.get());
		
		return JspPaths.COMMENT_VIEW;
	}
}
