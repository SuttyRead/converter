package com.converter.service;

import com.converter.model.DocumentResponse;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ParseService {

    private static final String DETECT_SPACES_REGEX = "\\s+";

    @Value("${class.for.select}")
    private String classForSelect;

    @Value("${nlp.model}")
    private String nlpModel;

    public DocumentResponse parseHtml(String path) throws IOException {
        String text = getTextFromHtml(path);
        String normalizedSpaceText = StringUtils.normalizeSpace(text).trim().replaceAll(DETECT_SPACES_REGEX, StringUtils.SPACE);
        String[] sentences = getSentences(normalizedSpaceText);

        return new DocumentResponse(normalizedSpaceText, sentences);
    }

    private String getTextFromHtml(String path) throws IOException {
        Document html = Jsoup.connect(path).get();
        Element body = html.body();
        Elements fullContent = body.select(classForSelect);

        return fullContent.text();
    }

    private String[] getSentences(String normalizedSpaceText) throws IOException {

        SentenceModel model = new SentenceModel(new File(nlpModel));
        SentenceDetectorME sentenceDetectorME = new SentenceDetectorME(model);

        return sentenceDetectorME.sentDetect(normalizedSpaceText);
    }
}
