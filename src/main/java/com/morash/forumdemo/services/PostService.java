/**
 * 
 */
package com.morash.forumdemo.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.morash.forumdemo.data.entity.Board;
import com.morash.forumdemo.data.entity.Comment;
import com.morash.forumdemo.data.entity.Post;
import com.morash.forumdemo.data.repository.CommentRepository;
import com.morash.forumdemo.data.repository.PostRepository;
import com.morash.forumdemo.exceptions.BoardNotFoundException;
import com.morash.forumdemo.exceptions.PostNotFoundException;
import com.morash.forumdemo.exceptions.UserNotLoggedInException;

/**
 * @author Michael
 *
 */
@Service
public class PostService {
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private CommentRepository commentRepo;
	
	public ArrayList<Post> getAllPosts() {
		ArrayList<Post> postList = (ArrayList<Post>) postRepo.findAll();
		
		return postList;
	}
	
	public ArrayList<Post> getPostsForBoard(String boardName) throws BoardNotFoundException {
		Board board = boardService.getBoardByName(boardName);
		ArrayList<Post> postList = (ArrayList<Post>) postRepo.getPostsForBoard(board, Sort.by(Direction.DESC, "postDate"));
		
		return postList;
	}
	
	public Post getPostById(int id) throws PostNotFoundException {
		Optional<Post> post = postRepo.findById(id);
		
		if (!post.isPresent()) {
			throw new PostNotFoundException();
		}
		
		return post.get();
	}
	
	public void createPost(Board board, Post post) throws UserNotLoggedInException {
		post.setBoard(board);
		post.setPoster(loginService.requiredLogin());
		post.setPostDate(LocalDateTime.now());
		
		postRepo.save(post);
	}
}
