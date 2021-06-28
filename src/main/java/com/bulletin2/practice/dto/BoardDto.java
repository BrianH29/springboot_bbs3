package com.bulletin2.practice.dto;

import com.bulletin2.practice.domain.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class BoardDto {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifedDate;

    @Builder
    public BoardDto(String writer, String title, String content, LocalDateTime createdDate, LocalDateTime modifedDate) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifedDate = modifedDate;
    }


    public Board toEntity(){
        Board build = Board.builder()
                .writer(writer)
                .title(title)
                .content(content)
                .build();
        return build;
    }
}
