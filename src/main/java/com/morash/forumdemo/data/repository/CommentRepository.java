package com.morash.forumdemo.data.repository;

import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.morash.forumdemo.data.entity.Comment;
import com.morash.forumdemo.data.entity.Post;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {
	@Query("SELECT c FROM Comment c WHERE c.respondingToPost = :post AND c.respondingToComment = null")
	public Set<Comment> getTopLevelCommentsForPost(@Param("post") Post post, Sort sort);
}