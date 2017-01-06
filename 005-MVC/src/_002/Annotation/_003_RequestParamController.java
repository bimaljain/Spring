package _002.Annotation;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class _003_RequestParamController {

	//a simple URL request in the browser bar is always a GET type
	//both URL pattern and HTTP request type must match
	@RequestMapping(value="/admissionForm.html", method=RequestMethod.GET)
	public ModelAndView getAdmissionForm(){
		ModelAndView modelAndView = new ModelAndView("003-admissionForm");
		return modelAndView;
	}
	
	//use @RequestParam to bind the request parameters
	//use of defaultValue
	@RequestMapping(value="/submitAdmissionForm.html", method=RequestMethod.POST)
	public ModelAndView submitAdmissionForm(@RequestParam(value="studentName", defaultValue="Mr. Bimal") String studentName, 
			@RequestParam("studentHobby") String studentHobby){
		ModelAndView modelAndView = new ModelAndView("003-admissionSuccess");
		modelAndView.addObject("message", "Details submitted:: Name: " + studentName + ", Hobby:" + studentHobby);
		return modelAndView;
	}
	
	//You can also bind all the request params into a single map object.
	@RequestMapping(value="/submitAdmissionForm2.html", method=RequestMethod.POST)
	public ModelAndView submitAdmissionForm2(@RequestParam Map<String, String> requestParams){
		String studentName = requestParams.get("studentName");
		String studentHobby = requestParams.get("studentHobby");
		ModelAndView modelAndView = new ModelAndView("003-admissionSuccess");
		modelAndView.addObject("message", "Details submitted:: Name: " + studentName + ", Hobby:" + studentHobby);
		return modelAndView;
	}
}
