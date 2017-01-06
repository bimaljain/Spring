package helper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class Hobby3ConstraintValidator implements ConstraintValidator<ValidHobby3, String> {
	 String listOfHobbies;

    @Override
    public void initialize(ValidHobby3 hobby) { 
    	listOfHobbies = hobby.listofHobbies();
    }
 
    @Override
    public boolean isValid(String hobby3Field, ConstraintValidatorContext cxt) {
        if(hobby3Field == null) {
            return false;
        }
        return hobby3Field.toLowerCase().matches(listOfHobbies);
    }
 
}