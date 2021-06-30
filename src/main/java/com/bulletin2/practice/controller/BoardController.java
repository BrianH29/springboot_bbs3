package com.bulletin2.practice.controller;

import com.bulletin2.practice.dto.BoardDto;
import com.bulletin2.practice.dto.FileDto;
import com.bulletin2.practice.service.BoardService;
import com.bulletin2.practice.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class BoardController {
    private BoardService boardService;
    private FileService fileService;

    @Autowired
    public BoardController(BoardService boardService, FileService fileService) {
        this.boardService = boardService;
        this.fileService = fileService;
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
    public String write(@RequestParam("upfile") MultipartFile files, HttpServletRequest request, BoardDto boardDto){
        try{
            String originFilename = files.getOriginalFilename();

            //업로드하는 시점의 날짜 와 시간
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String currentTime = sdf.format(new Date());

            int ranNum = (int)(Math.random()*90000+10000);

            //파일 속성 .jpg 짤라오기
            int dot = originFilename.lastIndexOf(".");
            String ext = originFilename.substring(dot);

            //파일명을 바꿔주기 위해 ex)20210630140522987320.jpg
            String fileName = currentTime + ranNum + ext;

            String savePath = request.getServletContext().getRealPath("/uploadImage");
            String filePath = savePath + "/" + fileName;

            System.out.println("====================savePath =================");
            System.out.println(savePath);

            files.transferTo(new File(filePath));

            FileDto fileDto = new FileDto();
            fileDto.setOriginFilename(originFilename);
            fileDto.setFilename(fileName);
            fileDto.setFilePath(filePath);

            Long fileId = fileService.saveFile(fileDto);
            boardDto.setFileId(fileId);
            boardService.savePost(boardDto);

        }catch(Exception e){
            e.printStackTrace();
        }

        return "redirect:/";
    }

    @GetMapping("/post/{id}")
    public String detail(@PathVariable("id") Long id, Model model){
        BoardDto boardDto = boardService.getPost(id);

        model.addAttribute("boardDto", boardDto);
        return "bbsDetail";
    }

    @GetMapping("/post/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        BoardDto boardDto = boardService.getPost(id);
        model.addAttribute("boardDto",boardDto);
        return "bbsEdit";
    }

    @PutMapping("/post/edit/{id}")
    public String update(BoardDto boardDto){
        boardService.savePost(boardDto);
        return "redirect:/";
    }

    @DeleteMapping("/post/{id}")
    public String delete(@PathVariable("id") Long id){
        boardService.deletePost(id);
        return "redirect:/";
    }
}
