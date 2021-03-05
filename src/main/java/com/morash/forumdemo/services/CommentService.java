package com.morash.forumdemo.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.morash.forumdemo.data.entity.Comment;
import com.morash.forumdemo.data.entity.Post;
import com.morash.forumdemo.data.repository.CommentRepository;
import com.morash.forumdemo.exceptions.CommentNotFoundException;
import com.morash.forumdemo.exceptions.UserNotLoggedInException;

@Service
public class CommentService {
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentRepository commentRepo;
	
	public Comment getCommentById(int id) throws CommentNotFoundException {
		Optional<Comment> comment = commentRepo.findById(id);
		
		if (comment.isEmpty()) {
			throw new CommentNotFoundException();
		}
		
		return comment.get();
	}
	
	public void createComment(Comment newComment, Post respondingToPost, Comment respondingToComment) throws UserNotLoggedInException {
		newComment.setPoster(userService.requiredLogin());
		newComment.setRespondingToPost(respondingToPost);
		newComment.setRespondingToComment(respondingToComment);
		newComment.setPostDate(LocalDateTime.now());
		
		commentRepo.save(newComment);
	}
	
	public void createComment(Comment newComment, Post respondingToPost) throws UserNotLoggedInException {
		createComment(newComment, respondingToPost, null);
	}
	
	public void createComment(Comment newComment, Comment respondingToComment) throws UserNotLoggedInException {
		createComment(newComment, respondingToComment.getRespondingToPost(), respondingToComment);
	}
}
