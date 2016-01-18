package org.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class TextEditor3 {
	
	@Autowired
	private MessageSource messageSource;
	
	public MessageSource getMessageSource() {
		return messageSource;
	}
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public void messages(){
		System.out.println(messageSource.getMessage("family.greeting", new String[] {"Bimal", "Bharat"}, "Default greeting", null));
	}
}
