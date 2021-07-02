package com.bulletin2.practice.controller;

import com.bulletin2.practice.dto.BoardDto;
import com.bulletin2.practice.dto.FileDto;
import com.bulletin2.practice.service.BoardService;
import com.bulletin2.practice.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
public class BoardController {
    private BoardService boardService;
    private FileService fileService;
    private String fileBaseDirectories;

    @Autowired
    public BoardController(BoardService boardService, FileService fileService) {
        this.boardService = boardService;
        this.fileService = fileService;
    }

    public void fileBaseDirector(HttpServletRequest request){
        fileBaseDirectories = request.getServletContext().getRealPath("/uploadImage");
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
    public ResponseEntity write(@RequestParam("upfile") MultipartFile files){
        String fileName = StringUtils.cleanPath(files.getOriginalFilename());
        Path path = Paths.get(fileBaseDirectories + fileName);

        try{
            Files.copy(files.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException e){
            e.printStackTrace();
        }

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/download/")
                .path(fileName)
                .toUriString();
        return ResponseEntity.ok(fileDownloadUri);
    }

    @GetMapping("/post/{id}")
    public String detail(@PathVariable("id") Long id, Model model){
        BoardDto boardDto = boardService.getPost(id);
        FileDto fileDto = fileService.getFile(id);

        model.addAttribute("boardDto", boardDto);
        model.addAttribute("fileDto", fileDto);
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

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable("fileId") Long fileId) throws IOException{
        FileDto fileDto = fileService.getFile(fileId);
        Path path = Paths.get(fileDto.getFilePath());

        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=/" + fileDto.getOriginFilename())
                .body(resource);
    }
}
