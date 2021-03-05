/**
 * 
 */
package com.morash.forumdemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.morash.forumdemo.data.constants.ErrorMessages;
import com.morash.forumdemo.data.constants.JspPaths;
import com.morash.forumdemo.data.constants.ModelKeyNames;
import com.morash.forumdemo.data.entity.Comment;
import com.morash.forumdemo.data.entity.Post;
import com.morash.forumdemo.exceptions.CommentNotFoundException;
import com.morash.forumdemo.exceptions.PostNotFoundException;
import com.morash.forumdemo.exceptions.UserNotLoggedInException;
import com.morash.forumdemo.services.CommentService;
import com.morash.forumdemo.services.LoginService;
import com.morash.forumdemo.services.PostService;

/**
 * @author Michael
 *
 */

@Controller
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	private LoginService loginService;

	@Autowired
	private PostService postService;

	@Autowired
	private CommentService commentService;

	@GetMapping("/create/respondtopost/{postId}")
	public ModelAndView createForPost(RedirectAttributes attributes, ModelMap model,
			@PathVariable(name = "postId", required = true) Integer postId) throws PostNotFoundException {
		// Serves form to create a new top-level comment for post
		// Redirects to login page if user is not currently logged in
		// Throws 404 if no post exists with post id

		Post respondToPost = postService.getPostById(postId);

		if (!loginService.isLoggedIn()) {
			attributes.addFlashAttribute(ModelKeyNames.ERROR_MESSAGE, ErrorMessages.COMMENT_NO_USER);
			return new ModelAndView("redirect:/user/login", model);
		}

		model.addAttribute(ModelKeyNames.POST, respondToPost);

		return new ModelAndView(JspPaths.COMMENT_CREATE_FOR_POST, model);
	}

	@PostMapping("/create/respondtopost/{postId}")
	public RedirectView createForPost(RedirectAttributes attributes,
			@PathVariable(name = "postId", required = true) Integer postId, Comment newComment)
			throws PostNotFoundException, UserNotLoggedInException {
		// Creates a new comment using post parameters
		// Redirects to login page if no user is currently logged in
		// Returns 404 if no post exists with postId

		Post respondToPost = postService.getPostById(postId);

		// Finish instantiation by adding currently logged in user, current time, and
		// the post to respond to
		commentService.createComment(newComment, respondToPost);

		return new RedirectView("/post/view/" + postId);
	}

	@GetMapping("/create/respondtocomment/{commentId}")
	public ModelAndView createForComment(RedirectAttributes attributes, ModelMap model,
			@PathVariable(name = "commentId", required = true) Integer commentId) throws CommentNotFoundException {
		// Serves the form to create a new comment in response to another comment
		// Redirects to login page if no user is currently logged in
		// Returns 404 if no comment with commentId exists

		Comment respondToComment = commentService.getCommentById(commentId);

		if (!loginService.isLoggedIn()) {
			attributes.addFlashAttribute(ModelKeyNames.ERROR_MESSAGE, ErrorMessages.COMMENT_NO_USER);
			return new ModelAndView("redirect:/user/login", model);
		}

		model.addAttribute(ModelKeyNames.POST, respondToComment.getRespondingToPost());
		model.addAttribute(ModelKeyNames.COMMENT, respondToComment);

		return new ModelAndView(JspPaths.COMMENT_CREATE_FOR_COMMENT, model);
	}

	@PostMapping("/create/respondtocomment/{commentId}")
	public RedirectView createForComment(RedirectAttributes attributes,
			@PathVariable(name = "commentId", required = true) Integer commentId, Comment newComment)
			throws UserNotLoggedInException, CommentNotFoundException {
		// Creates new comment from post parameters in response to another comment
		// Redirects to login if no user is currently logged in
		// Returns 404 if no comment exists with commentId

		Comment respondToComment = commentService.getCommentById(commentId);

		commentService.createComment(newComment, respondToComment);

		return new RedirectView("/comment/view/" + newComment.getId());
	}

	@GetMapping("/view/{commentId}")
	public String view(Model model, @PathVariable(name = "commentId") Integer commentId)
			throws CommentNotFoundException {
		// Shows the comment
		// Returns 404 if no comment exists with commentId

		Comment comment = commentService.getCommentById(commentId);

		model.addAttribute(ModelKeyNames.COMMENT, comment);

		return JspPaths.COMMENT_VIEW;
	}
}
