package helper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class Hobby2ConstraintValidator implements ConstraintValidator<ValidHobby2, String> {
	// this time, we are saving the listOfHobbies passed into the annotation as a member variable of the constraint validator class. This allows us 
	//to access the value in our “isValid” method.	
	 String listOfHobbies;
	
	//@ValidHobby2 will call initialize() method first and then call isValid() method
    @Override
    public void initialize(ValidHobby2 hobby) { 
    	listOfHobbies = hobby.listofHobbies();
    }
 
    @Override
    public boolean isValid(String hobby2Field, ConstraintValidatorContext cxt) {
        if(hobby2Field == null) {
            return false;
        }
        return hobby2Field.toLowerCase().matches(listOfHobbies);
    }
 
}