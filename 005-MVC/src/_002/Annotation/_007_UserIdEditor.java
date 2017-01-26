/*
PropertyEditorSupport: This interface defines methods to convert a property’s value to a String (getAsText()), and to set a property given a 
String (setAsText(String)). In particular, Spring Web MVC converts incoming request String data to the appropriate data type using PropertyEditors 
for the fields of the command beans.
 */

package _002.Annotation;

import java.beans.PropertyEditorSupport;

public class _007_UserIdEditor extends PropertyEditorSupport{
	
	//setAsText: Sets the property value by parsing a given String. 
	@Override
	public void setAsText(String userId){
		if (userId.contains("Mr.") || userId.contains("Mrs.")){
			setValue(userId);
		} else 
			setValue("Mr. " + userId);		
	}
}
