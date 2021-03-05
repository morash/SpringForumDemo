/**
 * 
 */
package com.morash.forumdemo.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.morash.forumdemo.data.entity.Board;
import com.morash.forumdemo.data.entity.Post;
import com.morash.forumdemo.data.repository.BoardRepository;
import com.morash.forumdemo.data.repository.PostRepository;
import com.morash.forumdemo.exceptions.BoardNotFoundException;
import com.morash.forumdemo.exceptions.UserNotLoggedInException;

/**
 * @author Michael
 *
 */
@Service
public class BoardService {
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private BoardRepository boardRepo;
	
	@Autowired
	private PostRepository postRepo;
	
	public ArrayList<Board> getBoardIndex() {
		return (ArrayList<Board>) boardRepo.findAll();
	}
	
	public Board getBoardByName(String name) throws BoardNotFoundException {
		Optional<Board> board = boardRepo.findBoardByName(name);
		
		if (board.isEmpty()) {
			throw new BoardNotFoundException();
		}
		
		return board.get();
	}
	
	public void createBoard(Board newBoard) throws UserNotLoggedInException {
		newBoard.setCreatedBy(loginService.requiredLogin());
		boardRepo.save(newBoard);
	}
	
	public ArrayList<Post> getPostsForBoard(Board board) {
		return postRepo.getPostsForBoard(board, Sort.by(Direction.DESC, "postDate"));
	}
}
