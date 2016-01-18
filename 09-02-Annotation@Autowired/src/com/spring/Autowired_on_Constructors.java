package com.spring;

import org.springframework.beans.factory.annotation.Autowired;

public class Autowired_on_Constructors {
	private SpellChecker spellChecker;

	@Autowired
	public Autowired_on_Constructors(SpellChecker spellChecker) { this.spellChecker = spellChecker; }
	public void spellCheck() { spellChecker.checkSpelling(); }
}