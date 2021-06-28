package com.bulletin2.practice.controller;

import com.bulletin2.practice.dto.BoardDto;
import com.bulletin2.practice.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

@Controller
public class BoardController {
    private BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/bbsList")
    public String list(Model model){
        List<BoardDto> boardDtoList = boardService.getBoardList();
        model.addAttribute("boardList",boardDtoList);
        return "bbsListView";
    }

    @GetMapping("/post")
    public String write(){
        return "bbsWrite";
    }

    @PostMapping("/post")
    public String write(BoardDto boardDto){
        boardService.savePost(boardDto);
        return "redirect:/";
    }
}
