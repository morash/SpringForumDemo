package com.morash.forumdemo.data.repository;

import java.util.ArrayList;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.morash.forumdemo.data.entity.Board;
import com.morash.forumdemo.data.entity.Comment;
import com.morash.forumdemo.data.entity.Post;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {
	@Query("SELECT c FROM Comment c WHERE c.respondingToPost.board = :board")
	public ArrayList<Comment> findByBoard(@Param("board") Board board);
	
	@Query("SELECT c FROM Comment c WHERE c.respondingToPost.board = :board AND c.respondingToComment = null")
	public ArrayList<Comment> findTopLevelCommentsForBoard(@Param("board") Board board);
	
	@Query("SELECT c FROM Comment c WHERE c.respondingToPost = :post")
	public ArrayList<Comment> findByPost(@Param("post") Post post);

	@Query("SELECT c FROM Comment c WHERE c.respondingToPost = :post AND c.respondingToComment = null")
	public ArrayList<Comment> findTopLevelCommentsForPost(@Param("post") Post post);
	
	@Query("SELECT c FROM Comment c WHERE c.respondingToComment = :comment")
	public ArrayList<Comment> findByRespondingToComment(@Param("comment") Comment comment);
}