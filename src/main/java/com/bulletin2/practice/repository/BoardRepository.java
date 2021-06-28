package com.bulletin2.practice.repository;

import com.bulletin2.practice.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
