/*
----------------------
004-admissionForm.jsp:
----------------------
First – notice that we’re including a tag library into our JSP page – the form taglib – to help with defining our form.
Next – the <form:form> tag plays an important role here; it’s very similar to the regular HTLM <form> tag but the modelAttribute attribute is the 
key which specifies name of the model object that backs this form. This will correspond to the @ModelAttribute later on in the controller.
Next – each input fields is using yet another useful tag from the Spring Form taglib – form: prefix. Each of these fields specifies a path attribute – 
this must correspond to a getter / setter of the model attribute (in this case, the User class). When the page is loaded, the input fields are 
populated by Spring, which calls the getter of each field bound to an input field. When the form is submitted, the setters are called to save the 
values of the form to the object.
Finally – when the form is submitted, the POST handler in the controller is invoked and the form is automatically bound to the user argument that 
we passed in.

------------------------------
_004_ModelAttributeController:
------------------------------
If the object called “user” is not added to the model, Spring would complain when we try to access the JSP, because the JSP will be set up to bind 
the form to the “user” model attribute.

To access our form backing object, we need to inject it via the @ModelAttribute annotation. An @ModelAttribute on a method argument indicates 
the argument will be retrieved from the model. If not present in the model, the argument will be instantiated first and then added to the model.

------------------------
Spring MVC Form Binding:
------------------------
1. In controller, you add an object into a model attribute.
2. In HTML form, you use spring:form tag and bind the controller object via modelAttribute.
3. When the HTML form is “POST”, you get the value via @ModelAttribute.

Here we are also checking Data Binding with different build-in datatypes like, Date, Collection (Arraylist), Long etc, and also with custom datatype
like Address. Spring MVC does data binding for all these datatypes automatically.

http://localhost:8082/005-MVC/002/004-admissionForm
 */
package _002.Annotation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import helper.User;

@Controller
public class _004_ModelAttributeController {
	
	@RequestMapping(value="/004-admissionForm", method=RequestMethod.GET)
	public ModelAndView showForm(){
		return new ModelAndView("004-admissionForm", "user", new User());
	}
	
	@RequestMapping(value="/004-admissionSuccess", method=RequestMethod.POST)
	public ModelAndView saveForm(@ModelAttribute("user") User user){		
		return new ModelAndView("004-admissionSuccess");		
	}

//	Another way	
//	@RequestMapping(value="/004-admissionSuccess", method=RequestMethod.POST)
//	public ModelAndView saveForm(@RequestParam("userid") String userid, @RequestParam("password") String password, 
//								  @RequestParam("phone") Long phone, @RequestParam("dob") Date dob,
//								  @RequestParam("skills") ArrayList skills){
//		ModelAndView modelAndView = new ModelAndView("004-admissionSuccess");
//		User user = new User();
//		user.setDob(dob);
//		user.setPassword(password);
//		user.setPhone(phone);
//		user.setSkills(skills);
//		user.setUserid(userid);
//		modelAndView.addObject("user", user);
//		return modelAndView;		
//	}
	
	//@ModelAttribute can be used at the method level and as method argument
	//Spring will automatically add the model object in the modelandview object for each request method handler present in this controller 
	//Spring MVC will call this method first before calling any request method handler present in this controller.
	@ModelAttribute
	public void commonModelObject(Model model){
		model.addAttribute("commonHeader","Hello Fremont User");
	}
}
