package org.spring;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class TextEditor4 {
	
	@Autowired
	private MessageSource messageSource;
	
	public MessageSource getMessageSource() {
		return messageSource;
	}
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public void messages(){
		System.out.println(messageSource.getMessage("greeting", null, "Default greeting", Locale.US));
		System.out.println(messageSource.getMessage("greeting", null, "Default greeting", Locale.UK));
	}
}
