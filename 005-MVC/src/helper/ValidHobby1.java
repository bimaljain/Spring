/*
The code below is mostly just boiler-plate. The three methods in the annotation are required by the JSR-303 spec. If our annotation accepted any 
arguments, we would have defined them there as methods. We will see this in our next annotation later in this tutorial. The most important part of 
the class above is the @Constraint annotation on the class which specifies that we will use our PhoneConstraintValidator class for the validation 
logic. The message() method defines how the message is resolved.
 */

package helper;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = Hobby1ConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidHobby1 { 
     
	//default error message
    String message() default "{Hobby1 is not valid. Can be only cricket or football}";
     
    Class<?>[] groups() default {};
     
    Class<? extends Payload>[] payload() default {};
      
}
