package com.morash.forumdemo.data.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.morash.forumdemo.data.entity.Board;

@Repository
public interface BoardRepository extends CrudRepository<Board, Integer> {
	@Query("SELECT b FROM Board b WHERE b.name = :boardName")
	public Optional<Board> findBoardByName(@Param("boardName") String boardName);
	
	//TODO: Figure out complex query
	@Query("SELECT b FROM Board b")
	public Set<Board> getTopBoards();
}
