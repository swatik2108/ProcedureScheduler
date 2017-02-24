package com.scape.procscheduler.web;


import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.scape.procscheduler.model.Study;
import com.scape.procscheduler.repository.StudyRepository;

/**
 * Instructs Spring MVC on how to parse and print elements of type 'Study'. 
 *
 * @author swatik
 */
@Component
public class StudyFormatter implements Formatter<Study> {

    private final StudyRepository study;

    @Autowired
    public StudyFormatter(StudyRepository study) {
        this.study = study;
    }

    @Override
    public String print(Study study, Locale locale) {
        return study.getDescription();
    }

    @Override
    public Study parse(String text, Locale locale) throws ParseException {
        Collection<Study> findStudy = this.study.findStudy();
        for (Study study : findStudy) {
            if (study.getDescription().equals(text)) {
                return study;
            }
        }
        throw new ParseException("study not found: " + text, 0);
    }

}
