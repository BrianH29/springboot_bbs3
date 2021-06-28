package com.bulletin2.practice.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 30, nullable = false)
    private String writer;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @Builder
    public Board(Long id, String title, String writer, String content) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
    }
}
