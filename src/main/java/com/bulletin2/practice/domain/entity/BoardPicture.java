package com.bulletin2.practice.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardPicture {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long boardIdx;

    @Column(nullable = false)
    private String original_file_name;

    @Column(nullable = false)
    private String stored_file_path;

    private Long file_size;

}
