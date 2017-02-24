package com.scape.procscheduler.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * @author swatik
 *         Simple test to make sure that Bean Validation is working
 *        
 */
public class ValidatorTests {

    private Validator createValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }

    @Test
    public void test_WhenFirstNameEmpty_ShouldNotValidate() {

        LocaleContextHolder.setLocale(Locale.ENGLISH);
        Patient patient = new Patient();
        patient.setFirstName("");
        patient.setLastName("Ann");

        Validator validator = createValidator();
        Set<ConstraintViolation<Patient>> constraintViolations = validator.validate(patient);

        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<Patient> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("firstName");
        assertThat(violation.getMessage()).isEqualTo("may not be empty");
    }

}
