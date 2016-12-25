/*
Command Object - a JavaBean which will be populated with the data from your forms
Think of Command Object as a POJO/JavaBean/etc.. that backs the form in your presentation layer.

Once the form is submitted, all the individual attributes are mapped/bound to this object. On the way up to presentation, Command Object properties 
may be used to pre/populate the form.


 */
package com.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
	@Autowired
	UserDAO userDAO;
	
	@RequestMapping("/empform")
	public ModelAndView showForm(){
		return new ModelAndView("empform", "command", new User());
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public ModelAndView saveForm(@ModelAttribute("user") User user){		
		System.out.println(user.getUserid() + " " + user.getPassword());
		userDAO.createUser(user);
		return new ModelAndView("redirect:/viewemp");//will redirect to viewemp request mapping 		
	}
	
	 @RequestMapping(value="/viewemp")  
	public ModelAndView viewEmp(){
		 return new ModelAndView("viewemp", "userList", userDAO.getUsers());
	}
	 
	 @RequestMapping(value="/editemp/{id}")  
	public ModelAndView editForm(@PathVariable("id") int id){
		 User user = userDAO.getUserById(id);
		 return new ModelAndView("editform", "command", user);
	}
	 
	 @RequestMapping(value="/editemp/update", method=RequestMethod.POST)  
	public ModelAndView update(@ModelAttribute("user") User user){
		 userDAO.updateUser(user);
		 return new ModelAndView("redirect:/viewemp");
	}
	 
	 @RequestMapping(value="/deleteemp/{id}", method=RequestMethod.GET)  
	public ModelAndView delete(@PathVariable("id") int id){
		 userDAO.deleteUser(id);
		 return new ModelAndView("redirect:/viewemp");
	}
	 
	 @RequestMapping(value="/pageview/{page}", method=RequestMethod.GET)  
	public ModelAndView pageView(@PathVariable("page") int page){
		 int total = 5;
		 if (page==1){}
		 else page=page*total;
		 return new ModelAndView("viewemp","userList",userDAO.getUserByPage(page, total));
	}
}
