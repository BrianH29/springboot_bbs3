package com.bulletin2.practice.domain.entity;

import com.bulletin2.practice.dto.BoardDto;
import com.bulletin2.practice.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class TimeEntityTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void getCreatedDate() {
        //given
        LocalDateTime now = LocalDateTime.of(2021,06,29,0,0,0);
        BoardDto boardDto = BoardDto.builder()
                .id(1L)
                .title("Hello Java")
                .content("this is java")
                .writer("Bj")
                .createdDate(now)
                .build();
        boardRepository.save(boardDto.toEntity());

        //when
        List<Board> boardList = boardRepository.findAll();

        //then
        Board board = boardList.get(0);

        assertThat(board.getCreatedDate()).isAfter(now);

    }

    @Test
    void getModifiedDate() {
    }
}