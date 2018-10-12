package com.github.lukasgemela.fileprocessor.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileProcessorService {

    String processFile(MultipartFile file);

}
