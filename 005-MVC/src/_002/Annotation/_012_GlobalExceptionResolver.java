/*
For this class, exception handling can be done in 2 ways:
1. Using _011_GlobalExceptionHandlerMethods
2. Using exception resolver in 002-WebApplicationContext.xml

comment one of the two before running this class
 */
package _002.Annotation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class _012_GlobalExceptionResolver{

	//http://localhost:8082/005-MVC/002/011-hello
	@RequestMapping(value = "/011-hello", method = RequestMethod.GET)
	public ModelAndView printHello() {
		//when this exception is thrown, Spring MVC will look for an ExceptionHandler for NullPointerException in the same controller class.
		throw new NullPointerException("Applicaion has encountered null pointer exception");
	}

	//http://localhost:8082/005-MVC/002/011-hello2
	@RequestMapping(value = "/011-hello2", method = RequestMethod.GET)
	public ModelAndView printHello2() {
		//when this exception is thrown, Spring MVC will look for an ExceptionHandler for _010_CustomException in the same controller class.
		throw new _010_CustomException("Applicaion has encountered custom exception");
	}
}
