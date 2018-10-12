package com.github.lukasgemela.fileprocessor.controller;

import com.github.lukasgemela.fileprocessor.model.UploadFileResponse;
import com.github.lukasgemela.fileprocessor.service.FileProcessorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

    private final FileProcessorServiceImpl fileProcessorService;

    @Autowired
    public FileUploadController(FileProcessorServiceImpl fileProcessorService) {
        this.fileProcessorService = fileProcessorService;
    }

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String topic = fileProcessorService.processFile(file);
        return new UploadFileResponse(file.getOriginalFilename(), topic);
    }


}
