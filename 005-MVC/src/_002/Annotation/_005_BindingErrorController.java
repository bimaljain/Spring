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
	
	@RequestMapping(value="/005-admissionForm", method=RequestMethod.GET)
	public ModelAndView showForm(){
		return new ModelAndView("005-admissionForm", "user", new User());
	}
	
	/*
	If spring MVC fails to bind the data, you get an error page which is not descriptive at all. (try "abc" for phone)
	To handle this scenario, common solution is to return the admissionform again along with the complete description of the error.
	Use BindingResult. It must be placed immediately after @ModelAttribute in the request method handler argument list.
	BindingResult reference will hold any error happened during data binding.
	Also add <form:errors> in your jsp to print any error.
	*/
	@RequestMapping(value="/005-admissionSuccess", method=RequestMethod.POST)
	public ModelAndView saveForm(@ModelAttribute("user") User user, BindingResult result){
		if (result.hasErrors()){
			return new ModelAndView("005-admissionForm");
		}
		return new ModelAndView("005-admissionSuccess");		
	}
}
