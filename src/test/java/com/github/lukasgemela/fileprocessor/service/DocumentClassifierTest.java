package com.github.lukasgemela.fileprocessor.service;

import com.github.lukasgemela.fileprocessor.config.TopicsConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DocumentClassifierTest {

    @Mock
    TopicsConfiguration topicsConfiguration;

    private DocumentClassifier documentClassifier;

    @Before
    public void before() {
        documentClassifier = new DocumentClassifier(topicsConfiguration);
    }

    @Test
    public void noPatternMatches_shouldReturnUnknownTopic() {
        Map<String, List<Pattern>> patternMap = new HashMap<>();
        patternMap.put("topic", singletonList(Pattern.compile("notmatchingregexp")));

        when(topicsConfiguration.getTopics()).thenReturn(patternMap);
        assertThat(documentClassifier.classifyDocument("java")).isEqualTo("Unknown");
    }

    @Test
    public void partOfPatternsMatches_shouldReturnUnknownTopic() {
        Map<String, List<Pattern>> patternMap = new HashMap<>();
        patternMap.put("topic", Arrays.asList(
                Pattern.compile("java"),
                Pattern.compile("notmatchingregexp")
        ));

        when(topicsConfiguration.getTopics()).thenReturn(patternMap);
        assertThat(documentClassifier.classifyDocument("java")).isEqualTo("Unknown");
    }

    @Test
    public void allPatternMatchesForSingleTopic_shouldReturnTopic() {
        Map<String, List<Pattern>> patternMap = new HashMap<>();
        patternMap.put("topic", Arrays.asList(
                Pattern.compile("j"),
                Pattern.compile("a")
        ));

        when(topicsConfiguration.getTopics()).thenReturn(patternMap);
        assertThat(documentClassifier.classifyDocument("java")).isEqualTo("topic");
    }

    @Test
    public void allPatternMatchesForMultipleTopics_shouldReturnFirstTopic() {
        Map<String, List<Pattern>> patternMap = new HashMap<>();
        patternMap.put("topic1", Arrays.asList(
                Pattern.compile("j"),
                Pattern.compile("a")
        ));
        patternMap.put("topic2", Arrays.asList(
                Pattern.compile("v"),
                Pattern.compile("a")
        ));

        when(topicsConfiguration.getTopics()).thenReturn(patternMap);
        assertThat(documentClassifier.classifyDocument("java")).isEqualTo("topic1");
    }
}
