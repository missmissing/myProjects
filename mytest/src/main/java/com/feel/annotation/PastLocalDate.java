package com.feel.annotation;


import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.time.LocalDate;

/**
 * Created by yulong.li on 2017/7/10.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PastLocalDate.PastValidator.class)
@Documented
public @interface PastLocalDate {
    String message() default "ss";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class PastValidator implements ConstraintValidator<PastLocalDate, LocalDate> {

        @Override
        public void initialize(PastLocalDate pastLocalDate) {

        }

        @Override
        public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
            return localDate == null || localDate.isBefore(LocalDate.now());
        }
    }
}
