package com.bulletin2.practice.service;

import com.bulletin2.practice.domain.entity.Board;
import com.bulletin2.practice.dto.BoardDto;
import com.bulletin2.practice.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class BoardServiceTest {
    @Autowired
    private BoardRepository boardRepository;

    //given
    String title = "Hello there";
    String writer = "Jane";
    String content = "GoodBye java";

    @Test
    void getBoardList() {

        //when
        BoardDto boardDto = BoardDto.builder()
                .title(title)
                .writer(writer)
                .content(content)
                .build();

        boardRepository.save(boardDto.toEntity());
        List<Board> boardList = boardRepository.findAll();

        //then
        assertThat(boardList.get(0).getTitle()).isEqualTo(title);


    }

    @Test
    void getPost() {
        //when
        BoardDto boardDto = BoardDto.builder()
                .title(title)
                .writer(writer)
                .content(content)
                .build();

        Long saveId = boardRepository.save(boardDto.toEntity()).getId();
        Optional<Board> findPost = boardRepository.findById(saveId);
        System.out.println("saveid ========" + saveId);
        //then
        assertThat(findPost.get().getId()).isEqualTo(saveId);
        assertThat(findPost.get().getTitle()).isEqualTo(title);
        assertThat(findPost.get().getWriter()).isEqualTo(writer);
        assertThat(findPost.get().getContent()).isEqualTo(content);
    }
}