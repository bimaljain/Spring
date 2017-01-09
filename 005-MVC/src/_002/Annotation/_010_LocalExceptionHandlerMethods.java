/*
------------------------------
Spring MVC Exception Handling:
------------------------------
Spring MVC Exception Handling is very important to make sure you are not sending server exceptions to client. Today we will look into Spring Exception 
Handling using @ExceptionHandler, @ControllerAdvice and HandlerExceptionResolver. Any web application requires good design for exception handling 
because we don’t want to serve container generated page when any unhandled exception is thrown by our application. Spring MVC Framework provides 
following ways to help us achieving robust exception handling.

1. Controller Based – We can define exception handler methods in our controller classes. All we need is to annotate these methods with @ExceptionHandler 
annotation. This annotation takes Exception class as argument. So if we have defined one of these for Exception class, then all the exceptions thrown 
by our request handler method will have handled. These exception handler methods are just like other request handler methods and we can build error 
response and respond with different error page. If there are multiple exception handler methods defined, then handler method that is closest to the 
Exception class is used. For example, if we have two handler methods defined for IOException and Exception and our request handler method throws 
IOException, then handler method for IOException will get executed.
So with this approach, we must provide ExceptionHandler methods in every controller class.

2. Global Exception Handler – Exception Handling is a cross-cutting concern, it should be done for all the pointcuts in our application. We have 
already looked into Spring AOP and that’s why Spring provides @ControllerAdvice annotation that we can use with any class to define our global 
exception handler. The handler methods in Global Controller Advice is same as Controller based exception handler methods and used when controller 
class is not able to handle the exception.

3. HandlerExceptionResolver – For generic exceptions, most of the times we serve static pages. Spring Framework provides HandlerExceptionResolver 
interface that we can implement to create global exception handler. The reason behind this additional way to define global exception handler is that 
Spring framework also provides default implementation classes that we can define in our spring bean configuration file to get spring framework 
exception handling benefits. SimpleMappingExceptionResolver is the default implementation class, it allows us to configure exceptionMappings where we 
can specify which resource to use for a particular exception. We can also override it to create our own global handler with our application specific 
changes, such as logging of exception messages.

 */
package _002.Annotation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import helper._010_CustomException;

@Controller
public class _010_LocalExceptionHandlerMethods{

	//http://localhost:8082/005-MVC/002/010-hello
	@RequestMapping(value = "/010-hello", method = RequestMethod.GET)
	public ModelAndView printHello() {
		//when this exception is thrown, Spring MVC will look for an ExceptionHandler for NullPointerException in the same controller class.
		throw new NullPointerException("Applicaion has encountered null pointer exception");
	}

	//http://localhost:8082/005-MVC/002/010-hello2
	@RequestMapping(value = "/010-hello2", method = RequestMethod.GET)
	public ModelAndView printHello2() {
		//when this exception is thrown, Spring MVC will look for an ExceptionHandler for _010_CustomException in the same controller class.
		throw new _010_CustomException("Applicaion has encountered custom exception");
	}
	
	@ExceptionHandler(value=NullPointerException.class)
	public ModelAndView handleNullPointerException(Exception ex){
		ModelAndView modelAndView = new ModelAndView("010-exceptionpage");
		modelAndView.addObject("ex", ex);
		return modelAndView;
	}
	
	@ExceptionHandler(value=_010_CustomException.class)
	public ModelAndView handleCustomException(_010_CustomException ex){
		ModelAndView modelAndView = new ModelAndView("010-exceptionpage");
		modelAndView.addObject("ex", ex.getExceptionMsg());
		return modelAndView;
	}
	
	//generic exception handler to handle any other exception thrown by the request handler methods in the same controller class.
	@ExceptionHandler(value=Exception.class)
	public ModelAndView handleException(Exception ex){
		ModelAndView modelAndView = new ModelAndView("010-exceptionpage");
		modelAndView.addObject("ex", ex);
		return modelAndView;
	}
}
