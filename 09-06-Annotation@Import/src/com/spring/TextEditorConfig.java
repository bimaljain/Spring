package com.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SpellCheckerConfig.class)
public class TextEditorConfig {

   @Bean 
   public TextEditor textEditor(){ return new TextEditor( new SpellCheckerConfig().spellChecker() ); }
}