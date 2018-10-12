package com.github.lukasgemela.fileprocessor.service;

import com.github.lukasgemela.fileprocessor.common.errorhandling.exception.ServiceExceptions;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

@Component
public class DocumentTransformer {

    private final AutoDetectParser parser;

    @Autowired
    public DocumentTransformer(AutoDetectParser parser) {
        this.parser = parser;
    }

    String documentToString(MultipartFile file) {
        try(InputStream stream = file.getInputStream();
            TikaInputStream bf = TikaInputStream.get(stream)) {
            return readUsingParser(bf);

        } catch (IOException e) {
            throw ServiceExceptions.internalServerError("file.could.not.be.opened", "File sent could not be opened");
        }
    }

    private String readUsingParser(TikaInputStream bf) {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();

        try {
            parser.parse(bf, handler, metadata);
            return handler.toString();
        } catch (IOException | SAXException | TikaException e) {
            throw ServiceExceptions.internalServerError("file.could.not.be.parsed", "File sent could not be parsed");
        }
    }
}
