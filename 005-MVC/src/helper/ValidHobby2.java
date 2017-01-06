package helper;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = Hobby2ConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidHobby2 { 	
	
	//the listofHobbies() method exposes the listofHobbies argument of the annotation which we will use to pass the hobbies that the annotation should 
	//validate against	
	String listofHobbies();
     
	//default error message
    String message() default "{Hobby2 is not valid. Can be only cricket or football}";
     
    Class<?>[] groups() default {};
     
    Class<? extends Payload>[] payload() default {};
      
}
