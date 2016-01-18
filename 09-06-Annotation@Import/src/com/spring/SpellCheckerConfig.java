package com.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpellCheckerConfig {
	
	@Bean
	public SpellChecker spellChecker() { return new SpellChecker();}
}