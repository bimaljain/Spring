package helper;

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
