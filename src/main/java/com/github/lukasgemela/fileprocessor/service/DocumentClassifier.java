package com.github.lukasgemela.fileprocessor.service;

import com.github.lukasgemela.fileprocessor.config.TopicsConfiguration;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

@Component
public class DocumentClassifier {

    private final TopicsConfiguration topicsConfiguration;
    private static final String UNKNOWN_TOPIC = "Unknown";

    public DocumentClassifier(TopicsConfiguration topicsConfiguration) {
        this.topicsConfiguration = topicsConfiguration;
    }

    public String classifyDocument(String document) {
        return topicsConfiguration.getTopics()
                .keySet()
                .stream()
                .filter(topic -> belongsToTopic(topic, document))
                .findFirst()
                .orElse(UNKNOWN_TOPIC);
    }

    private boolean belongsToTopic(String topic, String document) {
        return topicsConfiguration.getTopics()
                .get(topic)
                .stream()
                .allMatch(regexp -> isPatternMatch(regexp, document));
    }

    private boolean isPatternMatch(Pattern pattern, String document) {
        return pattern.matcher(document).find();
    }

}
