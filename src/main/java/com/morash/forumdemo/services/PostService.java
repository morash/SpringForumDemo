/**
 * 
 */
package com.morash.forumdemo.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.morash.forumdemo.data.entity.Board;
import com.morash.forumdemo.data.entity.Comment;
import com.morash.forumdemo.data.entity.Post;
import com.morash.forumdemo.data.repository.CommentRepository;
import com.morash.forumdemo.data.repository.PostRepository;
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
	private PostRepository postRepo;
	
	@Autowired
	private CommentRepository commentRepo;
	
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
	
	public ArrayList<Comment> getTopLevelCommentsForPost(Post post) {
		return commentRepo.getTopLevelCommentsForPost(post, Sort.by(Direction.DESC, "postDate"));
	}
}
