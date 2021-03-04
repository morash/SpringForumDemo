package com.morash.forumdemo.data.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.morash.forumdemo.data.entity.Board;
import com.morash.forumdemo.data.entity.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {
	@Query("SELECT p FROM Post p WHERE p.board = :board")
	public ArrayList<Post> getPostsForBoard(@Param("board") Board board, Sort sort);
}
