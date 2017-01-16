/*
-------------
Data Binding:
-------------
Spring MVC allows the use of command objects (aka form backing objects, model attributes, domain model objects basically the objects used to 
transport data between your view and your controller) using just about any type. However, the Servlet API deals with form parameters as Strings.
Spring uses a technique called data binding to covert between the String representation and the real underlying type. This enables user input to be 
bound to the objects you use to process user input. In other words, the values entered by a user in a form can be used to set the property values on 
a chosen object.
As well as binding the values, Spring includes support for validation and binding result analysis.
The binding functionality is provided by Spring's org.springframework.validation.DataBinder class.
And when converting a String to some arbitrary type, DataBinders make use of ProperEditors. 

----------------
PropertyEditors:
----------------
Spring heavily uses the concept of PropertyEditors to effect the conversion between an Object and a String.
Spring has a number of built-in PropertyEditors to make life easy.
If you are using a custom type that Spring is unable to convert to and from a String, you will likely receive a lovely error.
The solution is to create your own custom editor.
The idea of PropertyEditors is simple. You want to be able to convert back and forth between Java objects and their String representations.

-------------------------
Built-in PropertyEditors:
-------------------------
ByteArrayPropertyEditor: Editor for byte arrays. Strings will simply be converted to their corresponding byte representations. Registered by default by 
BeanWrapperImpl.
ClassEditor: Parses Strings representing classes to actual classes and the other way around. When a class is not found, an IllegalArgumentException is 
thrown. Registered by default by BeanWrapperImpl.
CustomBooleanEditor: Customizable property editor for Boolean properties. Registered by default by BeanWrapperImpl, but, can be overridden by registering
 custom instance of it as custom editor.
CustomCollectionEditor: Property editor for Collections, converting any source Collection to a given target Collection type.
CustomDateEditor: Customizable property editor for java.util.Date, supporting a custom DateFormat. NOT registered by default. Must be user registered as 
needed with appropriate format.
CustomNumberEditor: Customizable property editor for any Number subclass like Integer, Long, Float, Double. Registered by default by BeanWrapperImpl, but
 can be overridden by registering custom instance of it as a custom editor.
FileEditor: Capable of resolving Strings to java.io.File objects. Registered by default by BeanWrapperImpl.
InputStreamEditor: One-way property editor, capable of taking a text string and producing (via an intermediate ResourceEditor and Resource) an 
InputStream, so InputStream properties may be directly set as Strings. Note that the default usage will not close the InputStream for you! Registered by 
default by BeanWrapperImpl.
LocaleEditor: Capable of resolving Strings to Locale objects and vice versa (the String format is [country][variant], which is the same thing the 
toString() method of Locale provides). Registered by default by BeanWrapperImpl.
PatternEditor: Capable of resolving Strings to java.util.regex.Pattern objects and vice versa.
PropertiesEditor: Capable of converting Strings (formatted using the format as defined in the javadocs of the java.util.Properties class) to Properties 
objects. Registered by default by BeanWrapperImpl.
StringTrimmerEditor: Property editor that trims Strings. Optionally allows transforming an empty string into a null value. NOT registered by default; 
must be user registered as needed.
URLEditor: Capable of resolving a String representation of a URL to an actual URL object. Registered by default by BeanWrapperImpl.

 */
package _002.Annotation;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import helper.User;

@Controller
public class _006_CustomDataBinderController {
	
	//use this method when you want to customize the data binding feature for your controller class
	//method can have any name, but add @InitBinder annotation and WebDataBinder in the method argument to get WebDataBinder reference 
	@InitBinder
	public void customBinder(WebDataBinder binder){
		binder.setDisallowedFields("phone"); //ignore data binding for phone
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy"); //set a different format for dob
		binder.registerCustomEditor(Date.class, "dob", new CustomDateEditor(dateFormat, false)); // property type, property name, property editor
	}
	
	@RequestMapping(value="/006-admissionForm", method=RequestMethod.GET)
	public ModelAndView showForm(){
		return new ModelAndView("006-admissionForm", "user", new User());
	}
	
	@RequestMapping(value="/006-admissionSuccess", method=RequestMethod.POST)
	public ModelAndView saveForm(@ModelAttribute("user") User user, BindingResult result){
		if (result.hasErrors()){
			return new ModelAndView("006-admissionForm");
		}
		return new ModelAndView("006-admissionSuccess");		
	}	
}
