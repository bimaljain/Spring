/*
--------------------------------------------
Spring MVC Form Validation with Annotations:
--------------------------------------------
Spring MVC supports any third party library which has implemented JSR 303 or JSR 349 specification.
JSR 303 or JSR 349 implementation provides annotations for bean validation.
We include hibernate validator library in the project.

Add:
hibernate validator library
bean validation annotations in the pojo
@Valid annotation in the request method handler. Without this annotation, Spring MVC will ignore all the bean validation annotations in the pojo.
activate Spring 'annotation-driven' <mvc:annotation-driven /> or @EnableWebMVC

 */
package _002.Annotation;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import helper._008_User;

@Controller
public class _008_FormValidationController {

	@RequestMapping(value="/008-admissionForm", method=RequestMethod.GET)
	public ModelAndView showForm(){
		return new ModelAndView("008-admissionForm", "user", new _008_User());
	}
	
	/*
	Simply adding @Valid tells Spring to validate the Subscriber object. Nice! Notice we also add a BindingResult argument. This is Spring's 
	object that holds the result of the validation and binding and contains errors that may have occurred. The BindingResult must come right after 
	the model object that is validated or else Spring will fail to validate the object and throw an exception.
	
	When Spring sees @Valid, it tries to find the validator for the object being validated. Spring automatically picks up validation annotations 
	if you have annotation-driven enabled. Spring then invokes the validator and puts any errors in the BindingResult and adds the BindingResult 
	to the view model.
	
	The <form:errors> tag outputs errors associated with the specified path.
	 */
	@RequestMapping(value="/008-admissionSuccess", method=RequestMethod.POST)
	public ModelAndView saveForm(@Valid @ModelAttribute("user") _008_User user, BindingResult result){
		if (result.hasErrors()){
			return new ModelAndView("008-admissionForm");
		}
		return new ModelAndView("008-admissionSuccess");		
	}	
}
