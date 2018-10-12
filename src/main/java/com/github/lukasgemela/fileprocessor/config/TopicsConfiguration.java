package com.github.lukasgemela.fileprocessor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@ConfigurationProperties
@Configuration
public class TopicsConfiguration {

    private Map<String, List<Pattern>> topics;

    @SuppressWarnings("unused")
    public void setTopics(Map<String, List<String>> topics) {
        this.topics = toPatternMap(topics);
    }

    private static Map<String, List<Pattern>> toPatternMap(Map<String, List<String>> topics) {
        return topics.entrySet()
                .stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), mapToPatterns(entry.getValue())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static List<Pattern> mapToPatterns(List<String> regexps) {
        return regexps.stream()
                .map(Pattern::compile)
                .collect(Collectors.toList());
    }

    public Map<String, List<Pattern>> getTopics() {
        return topics;
    }
}
