/*
If spring MVC fails to bind the data, you get an error page which is not descriptive at all. (try "abc" for phone)
To handle this scenario, common solution is to return the admissionform again along with the complete description of the error.

Use BindingResult. This is Spring's object that holds the result of the validation and binding and contains errors that may have occurred. The 
BindingResult must come right after the model object that is validated or else Spring will fail to validate the object and throw an exception. 
BindingResult reference will hold any error happened during data binding. Spring puts any errors in the BindingResult and adds the BindingResult to the 
view model.
	
The <form:errors> tag in the jsp outputs errors associated with the specified path.
*/

package _002.Annotation;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import helper.User;

@Controller
public class _005_BindingErrorController {
	
	//http://localhost:8082/005-MVC/002/005-admissionForm
	@RequestMapping(value="/005-admissionForm", method=RequestMethod.GET)
	public ModelAndView showForm(){
		return new ModelAndView("005-admissionForm", "user", new User());
	}

	@RequestMapping(value="/005-admissionSuccess", method=RequestMethod.POST)
	public ModelAndView saveForm(@ModelAttribute("user") User user, BindingResult result){
		if (result.hasErrors()){
			return new ModelAndView("005-admissionForm");
		}
		return new ModelAndView("005-admissionSuccess");		
	}
}
