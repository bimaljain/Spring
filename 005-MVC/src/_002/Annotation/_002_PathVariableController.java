package _002.Annotation;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

//Multi-Action Controller: Controller class with more than one request handler method.
//Using @RequestMapping at class level

@Controller
@RequestMapping("/greet")
public class _002_PathVariableController{

	//http://localhost:8082/005-MVC/002/greet/hello1
	@RequestMapping(value = "/hello1", method = RequestMethod.GET)
	public ModelAndView printHello() {
		ModelAndView modelAndView = new ModelAndView("001-hello");
		modelAndView.addObject("message", "Hello Spring MVC Framework!");
		return modelAndView;
	}

	/* 
   Binding path variables using @PathVariable
   http://localhost:8082/005-MVC/002/greet/hello2/India/Bimal
	 */
	@RequestMapping(value = "/hello2/{countryName}/{userName}", method = RequestMethod.GET)
	public ModelAndView printHello2(@PathVariable("countryName") String countryName, @PathVariable("userName") String userName) {
		ModelAndView modelAndView = new ModelAndView("001-hello");
		modelAndView.addObject("message", "Hello " + userName + ". You are from " + countryName);
		return modelAndView;
	}

	/*
	 You can also bind all the path variables into a single map object.
	 Add: <mvc:annotation-driven/>: The support for @Controller and @RequestMapping is provided by Spring by default. However, by enabling 
	 mvc:annotation-driven you get support for processing the requests that are mapped to annotated controller methods, such as declarative 
	 validation, formatting and conversion service.
	 
	 http://localhost:8082/005-MVC/002/greet/hello3/India/Bimal
	 */
	@RequestMapping(value = "/hello3/{countryName}/{userName}", method = RequestMethod.GET)
	public ModelAndView printHello3(@PathVariable Map<String, String> pathVariable) {
		String userName = pathVariable.get("userName");
		String countryName = pathVariable.get("countryName");
		ModelAndView modelAndView = new ModelAndView("001-hello");
		modelAndView.addObject("message", "Hello " + userName + ". You are from " + countryName);
		return modelAndView;
	}
	
}
