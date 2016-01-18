package com.spring;

public class TextEditor {
	private SpellChecker spellChecker;
	private String name;
	
	public TextEditor() { 
	}
	public TextEditor(SpellChecker spellChecker, String name) {
		this.spellChecker = spellChecker;
		this.name = name;
	}
	public SpellChecker getSpellChecker() { return spellChecker; }
	public void setSpellChecker(SpellChecker spellChecker) { this.spellChecker = spellChecker; }
	
	public void getName() {System.out.println(name);}
	public void setName(String name) { this.name = name; }
	
	public void spellCheck() { spellChecker.checkSpelling(); }
}