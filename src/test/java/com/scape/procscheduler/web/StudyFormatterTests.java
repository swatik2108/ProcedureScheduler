package com.scape.procscheduler.web;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.scape.procschedule.TestData;
import com.scape.procscheduler.model.Study;
import com.scape.procscheduler.repository.StudyRepository;


/**
 * Test class for {@link StudyFormatter}
 *
 * @author swatik
 */
@RunWith(MockitoJUnitRunner.class)
public class StudyFormatterTests implements TestData {

    @Mock
    private StudyRepository study;

    private StudyFormatter studyFormatter;

    @Before
    public void setup() {
        this.studyFormatter = new StudyFormatter(study);
    }

    @Test
    public void testPrint() {
        Study study = new Study();
        study.setDescription("Electrocardiography");
        String studyName = this.studyFormatter.print(study, Locale.ENGLISH);
        assertEquals("Electrocardiography", studyName);
    }

   @Test
    public void shouldParse() throws ParseException {
        Mockito.when(this.study.findStudy()).thenReturn(makeStudies());
        Study study = studyFormatter.parse("Electromyography", Locale.ENGLISH);
        assertEquals("Electromyography", study.getDescription());
    }

    @Test(expected = ParseException.class)
    public void shouldThrowParseException() throws ParseException {
        Mockito.when(this.study.findStudy()).thenReturn(makeStudies());
        studyFormatter.parse("TestFail", Locale.ENGLISH);
    }

    /**
     * Helper method to produce some sample study types just for test purpose
     *
     * @return {@link Collection} of {@link Study}
     */
    private List<Study> makeStudies() {
        List<Study> studies = new ArrayList<>();
        studies.add(new Study(){
            {
            	setDescription("Electromyography");
            }
        });
        studies.add(new Study(){
            {
            	setDescription("Electromyography");
            }
        });
        return studies;
    }

}
