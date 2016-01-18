package com.spring;

public class TextEditor {
	private SpellChecker spellChecker;
	private String name;
	
	public SpellChecker getSpellChecker() { return spellChecker; }
	public void setSpellChecker(SpellChecker spellChecker) { this.spellChecker = spellChecker; }
	
	public void getName() {System.out.println(name);}
	public void setName(String name) { this.name = name; }
	
	public void spellCheck() { spellChecker.checkSpelling(); }
}