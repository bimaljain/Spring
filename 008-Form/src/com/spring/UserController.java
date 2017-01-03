/*
------------
empform.jsp:
------------
First – notice that we’re including a tag library into our JSP page – the form taglib – to help with defining our form.
Next – the <form:form> tag plays an important role here; it’s very similar to the regular HTLM <form> tag but the modelAttribute attribute is the 
key which specifies name of the model object that backs this form. This will correspond to the @ModelAttribute later on in the controller.
Next – each input fields is using yet another useful tag from the Spring Form taglib – form: prefix. Each of these fields specifies a path attribute – 
this must correspond to a getter / setter of the model attribute (in this case, the Employee class). When the page is loaded, the input fields are 
populated by Spring, which calls the getter of each field bound to an input field. When the form is submitted, the setters are called to save the 
values of the form to the object.
Finally – when the form is submitted, the POST handler in the controller is invoked and the form is automatically bound to the employee argument that 
we passed in.

---------------
UserController:
---------------
If the object called “user” is not added to the model, Spring would complain when we try to access the JSP, because the JSP will be set up to bind 
the form to the “user” model attribute.

To access our form backing object, we need to injected it via the @ModelAttribute annotation. An @ModelAttribute on a method argument indicates 
the argument will be retrieved from the model. If not present in the model, the argument will be instantiated first and then added to the model.

------------------------
Spring MVC Form Binding:
------------------------
1. In controller, you add an object into a model attribute.
2. In HTML form, you use spring:form tag and bind the controller object via modelAttribute.
3. When the HTML form is “POST”, you get the value via @ModelAttribute.

----
URL:
----
http://localhost:8082/008-Form/
 */
package com.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

//Multi-Action Controller
@Controller
public class UserController {
	@Autowired
	UserDAO userDAO;
	
	@RequestMapping(value="/empform", method=RequestMethod.GET)
	public ModelAndView showForm(){
		return new ModelAndView("empform", "user", new User());
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
		 return new ModelAndView("editform", "user", user);
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
