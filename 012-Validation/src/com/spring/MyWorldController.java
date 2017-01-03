package com.spring;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spring.validators.EmailValidator;
import com.spring.validators.UserValidator;

@Controller
@RequestMapping("/myworld")
public class MyWorldController {
	@Autowired
	private UserValidator userValidator;
	@Autowired
	private EmailValidator emailValidator;

	@RequestMapping(value="signup", method = RequestMethod.GET)
	public ModelAndView user(){
		return new ModelAndView("user","user",new User());
	}
	@InitBinder
	public void dataBinding(WebDataBinder binder) {
		binder.addValidators(userValidator, emailValidator);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, "dob", new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value="save", method = RequestMethod.POST)
	public String createUser(@Validated @ModelAttribute("user") User user,BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			return "user";
		}
		System.out.println("Name:"+ user.getName());
		System.out.println("Email:"+ user.getEmail());
		System.out.println("Date of Birth:"+ user.getDob());
		model.addAttribute("msg", "Welcome to My World!");
		return "success";
	}	
}