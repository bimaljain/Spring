package _002.Annotation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class _011_GlobalExceptionHandlerMethods{
	
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR) //otherwise server will send "200 OK" HTTP STATUS to the client
	@ExceptionHandler(value=NullPointerException.class)
	public ModelAndView handleNullPointerException(Exception ex){
		ModelAndView modelAndView = new ModelAndView("010-exceptionpage");
		modelAndView.addObject("ex", ex);
		return modelAndView;
	}
	
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value=_010_CustomException.class)
	public ModelAndView handleCustomException(_010_CustomException ex){
		ModelAndView modelAndView = new ModelAndView("010-exceptionpage");
		modelAndView.addObject("ex", ex.getExceptionMsg());
		return modelAndView;
	}
	
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value=Exception.class)
	public ModelAndView handleException(Exception ex){
		ModelAndView modelAndView = new ModelAndView("010-exceptionpage");
		modelAndView.addObject("ex", ex);
		return modelAndView;
	}
}
