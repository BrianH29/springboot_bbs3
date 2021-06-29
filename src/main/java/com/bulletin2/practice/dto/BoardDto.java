package com.bulletin2.practice.dto;

import com.bulletin2.practice.domain.entity.Board;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
@Setter
public class BoardDto {
    private Long id;
    private String title;
    private String writer;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    //domain Board
    public Board toEntity(){
        Board build = Board.builder()
                .title(title)
                .writer(writer)
                .content(content)
                .build();
        return build;
    }

    @Builder
    public BoardDto(Long id, String title, String writer, String content, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

}
