package com.bulletin2.practice.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SequenceGenerator(
        name = "BOARD_SEQ",
        sequenceName = "board_id",
        initialValue = 1,
        allocationSize = 1
)
public class Board extends TimeEntity{

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "board_id"
    )
    private Long id;

    @Column(length = 15, nullable = false)
    private String title;

    @Column(length = 30, nullable = false)
    private String writer;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @Builder
    public Board(String title, String writer, String content) {
        this.title = title;
        this.writer = writer;
        this.content = content;
    }
}
