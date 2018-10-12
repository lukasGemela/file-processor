package com.github.lukasgemela.fileprocessor.model;

public class UploadFileResponse {

    private final String fileName;
    private final String topic;

    public UploadFileResponse(String fileName, String topic) {
        this.fileName = fileName;
        this.topic = topic;
    }

    public String getFileName() {
        return fileName;
    }

    public String getTopic() {
        return topic;
    }
}
