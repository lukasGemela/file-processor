package com.github.lukasgemela.fileprocessor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileProcessorServiceImpl implements FileProcessorService {

    private final DocumentValidator documentValidator;
    private final DocumentTransformer documentTransformer;
    private final DocumentClassifier documentClassifier;

    @Autowired
    public FileProcessorServiceImpl(DocumentValidator documentValidator,
                                    DocumentTransformer documentTransformer,
                                    DocumentClassifier documentClassifier) {
        this.documentValidator = documentValidator;
        this.documentTransformer = documentTransformer;
        this.documentClassifier = documentClassifier;
    }

    @Override
    public String processFile(MultipartFile file) {

        documentValidator.validateDocument(file);
        String documentAsString = documentTransformer.documentToString(file);
        String topic = documentClassifier.classifyDocument(documentAsString);

        return topic;
    }



}
