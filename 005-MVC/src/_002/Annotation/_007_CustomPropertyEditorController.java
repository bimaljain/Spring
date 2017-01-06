/*
Write your customg property editor class which extends PropertyEditorSupport
Register the class with the data binder
 */
package _002.Annotation;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import helper.User;
import helper._007_UserIdEditor;

@Controller
public class _007_CustomPropertyEditorController {

	@InitBinder
	public void customBinder(WebDataBinder binder){
		binder.setDisallowedFields("phone"); //ignore data binding for phone
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy"); //set a different format for dob
		binder.registerCustomEditor(Date.class, "dob", new CustomDateEditor(dateFormat, false));
		binder.registerCustomEditor(String.class, "userid", new _007_UserIdEditor()); // property type, property name, property editor
	}
	
	@RequestMapping(value="/007-admissionForm", method=RequestMethod.GET)
	public ModelAndView showForm(){
		return new ModelAndView("007-admissionForm", "user", new User());
	}
	
	@RequestMapping(value="/007-admissionSuccess", method=RequestMethod.POST)
	public ModelAndView saveForm(@ModelAttribute("user") User user, BindingResult result){
		if (result.hasErrors()){
			return new ModelAndView("007-admissionForm");
		}
		return new ModelAndView("007-admissionSuccess");		
	}	
}
