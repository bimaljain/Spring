package _004.Form;

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
	
	@RequestMapping(value="/menu", method=RequestMethod.GET)
	public ModelAndView showMenu(){
		return new ModelAndView("menu");
	}
	
	@RequestMapping(value="/empform", method=RequestMethod.GET)
	public ModelAndView showForm(){
		return new ModelAndView("empform", "user", new User());
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public ModelAndView saveForm(@ModelAttribute("user") User user){		
		System.out.println(user.getUserid() + " " + user.getPassword());
		userDAO.createUser(user);
		return new ModelAndView("redirect:viewemp");//will redirect to viewemp request mapping 		
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
		 return new ModelAndView("redirect:/004/viewemp");
	}
	 
	 @RequestMapping(value="/deleteemp/{id}", method=RequestMethod.GET)  
	public ModelAndView delete(@PathVariable("id") int id){
		 userDAO.deleteUser(id);
		 return new ModelAndView("redirect:/004/viewemp");
	}
	 
	 @RequestMapping(value="/pageview/{page}", method=RequestMethod.GET)  
	public ModelAndView pageView(@PathVariable("page") int page){
		 int total = 5;
		 if (page==1){}
		 else page=page*total;
		 return new ModelAndView("viewemp","userList",userDAO.getUserByPage(page, total));
	}
}
