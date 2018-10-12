package com.github.lukasgemela.fileprocessor.config;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.exception.TikaException;
import org.apache.tika.parser.AutoDetectParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

import java.io.IOException;

@Configuration
public class TikaConfiguration {

    @Value("classpath:tika-config.xml")
    private Resource tikaConfig;

    @Bean
    public TikaConfig tikaConfig() throws IOException, TikaException, SAXException {
        return new TikaConfig(tikaConfig.getInputStream());
    }

    @Bean
    public Detector detector(TikaConfig tikaConfig) {
        return tikaConfig.getDetector();
    }

    @Bean
    public AutoDetectParser parser(TikaConfig tikaConfig) {
        return new AutoDetectParser(tikaConfig);
    }
}
