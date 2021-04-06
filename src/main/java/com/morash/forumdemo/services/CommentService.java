package com.morash.forumdemo.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.morash.forumdemo.data.entity.Board;
import com.morash.forumdemo.data.entity.Comment;
import com.morash.forumdemo.data.entity.Post;
import com.morash.forumdemo.data.repository.CommentRepository;
import com.morash.forumdemo.exceptions.BoardNotFoundException;
import com.morash.forumdemo.exceptions.CommentNotFoundException;
import com.morash.forumdemo.exceptions.PostNotFoundException;
import com.morash.forumdemo.exceptions.UserNotLoggedInException;

@Service
public class CommentService {
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private CommentRepository commentRepo;
	
	public ArrayList<Comment> getAllComments() {
		return (ArrayList<Comment>) commentRepo.findAll();
	}
	
	public ArrayList<Comment> getCommentsForBoard(String boardName) throws BoardNotFoundException {
		Board board = boardService.getBoardByName(boardName);
		return commentRepo.findTopLevelCommentsForBoard(board);
	}
	
	public ArrayList<Comment> getCommentsForPost(int postId) throws PostNotFoundException {
		Post post = postService.getPostById(postId);
		return commentRepo.findTopLevelCommentsForPost(post);
	}
	
	public ArrayList<Comment> getCommentsRespondingToComment(int commentId) throws CommentNotFoundException {
		Comment comment = getCommentById(commentId);
		return commentRepo.findByRespondingToComment(comment);
	}
	
	public Comment getCommentById(int id) throws CommentNotFoundException {
		Optional<Comment> comment = commentRepo.findById(id);
		
		if (!comment.isPresent()) {
			throw new CommentNotFoundException();
		}
		
		return comment.get();
	}
	
	public void createComment(Comment newComment, Post respondingToPost, Comment respondingToComment) throws UserNotLoggedInException {
		newComment.setPoster(loginService.requiredLogin());
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
