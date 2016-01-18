package com.spring;

import org.springframework.beans.factory.annotation.Autowired;

public class Autowired_on_Properties {
	@Autowired
	private SpellChecker spellChecker;

	public SpellChecker getSpellChecker( ) { return spellChecker; }
	public void spellCheck() { spellChecker.checkSpelling(); }
}