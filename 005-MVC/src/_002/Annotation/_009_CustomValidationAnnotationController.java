/*
we will write custom validation annotations which we can then re-use, just like the built-in JSR-303 ones.

To create custom validation annotation, you need to create 2 classes:
1. A java file called as an annotation type or simply an interface, having complete definition of this custom annotation.
2. A java file having the validation logic used by this custom annotation to validate the user input.

We will check 3 different annotations:
1. ValidHobby1: Internally this will check if the supplied hobby is cricket or football; else throw an error message.
2. ValidHobby2: This annotation will accept an argument. So the developer can provide a valid list of hobbies along with the annotation.
3. ValidHobby3: In the above case, we get error if we don't provide list of hobbies along with the annotation. We can overcome this by providing a
default list of hobbies in the interface file.

 */
package _002.Annotation;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import helper._009_User;

@Controller
public class _009_CustomValidationAnnotationController {

	@RequestMapping(value="/009-admissionForm", method=RequestMethod.GET)
	public ModelAndView showForm(){
		return new ModelAndView("009-admissionForm", "user", new _009_User());
	}

	@RequestMapping(value="/009-admissionSuccess", method=RequestMethod.POST)
	public ModelAndView saveForm(@Valid @ModelAttribute("user") _009_User user, BindingResult result){
		if (result.hasErrors()){
			return new ModelAndView("009-admissionForm");
		}
		return new ModelAndView("009-admissionSuccess");		
	}	
}


