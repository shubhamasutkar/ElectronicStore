package com.bikkadit.electronic.store.ElectronicStore.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
public @interface ImageNameValid {

    //default error message
    String message() default "{javax.validation.constraints.NotBlank.message}";

    //represents group of constraints
    Class<?>[]groups() default {};

    //additional information about annotations
    Class<? extends Payload>[] payload() default { };
}
