package com.bulletin2.practice.service;

import com.bulletin2.practice.domain.Board;
import com.bulletin2.practice.dto.BoardDto;
import com.bulletin2.practice.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {
    private BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional
    public List<BoardDto> getBoardList() {
        List<Board> boards = boardRepository.findAll();
        List<BoardDto> boardDtoLists = new ArrayList<>();

        for(Board board : boards){
            BoardDto boardDto = BoardDto.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .writer(board.getWriter())
                    .content(board.getContent())
                    .createdDate(board.getCreatedDate())
                    .build();
            boardDtoLists.add(boardDto);
        }
        return boardDtoLists;
    }

    @Transactional
    public Long savePost(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getId();
    }
}
