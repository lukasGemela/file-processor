package com.github.lukasgemela.fileprocessor.service;

import com.github.lukasgemela.fileprocessor.common.errorhandling.exception.ServiceExceptions;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.microsoft.OfficeParser;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Component
public class DocumentValidator {

    private final Detector detector;

    private static final List<MediaType> SUPPORTED_MEDIA_TYPES = Arrays.asList(
            OfficeParser.POIFSDocumentType.WORDDOCUMENT.getType(),
            MediaType.application("vnd.openxmlformats-officedocument.wordprocessingml.document"),
            MediaType.application("pdf")
    );

    public DocumentValidator(Detector detector) {
        this.detector = detector;
    }

    void validateDocument(MultipartFile file){

        try(InputStream stream = file.getInputStream();
            TikaInputStream bf = TikaInputStream.get(stream)) {
            MediaType mediaType = detectDocTypeUsingDetector(bf);

            if(!SUPPORTED_MEDIA_TYPES.contains(mediaType)) {
                throw ServiceExceptions.badRequest("unsupported.document.format", "Format of this document is not supported: " + mediaType);
            }
        } catch (IOException e) {
            throw ServiceExceptions.internalServerError("file.could.not.be.opened", "File sent could not be opened");
        }
    }

    private MediaType detectDocTypeUsingDetector(InputStream stream)
            throws IOException {
        Metadata metadata = new Metadata();
        return detector.detect(stream, metadata);
    }
}
