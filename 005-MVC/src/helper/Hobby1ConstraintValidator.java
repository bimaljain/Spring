/*
The templated type of the superclass takes two types: the type of the annotation it supports, and the type of the property it validates (in this example, 
Phone, String).

The �initialize� method is empty here, but it can be used to save data from the annotation, as we will see below when we define our other annotation.

Finally, the actual logic happens in the �isValid� method. The field value is passed in as the first argument, and we do our validation here

 */
package helper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class Hobby1ConstraintValidator implements ConstraintValidator<ValidHobby1, String> {
	 
    @Override
    public void initialize(ValidHobby1 phone) { 
    	// not needed for this case
    }
 
    @Override
    public boolean isValid(String hobby1Field, ConstraintValidatorContext cxt) {
        if(hobby1Field == null) {
            return false;
        }
        return hobby1Field.toLowerCase().matches("cricket|football");
    }
 
}