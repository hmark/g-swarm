package evo;

import pso.PSO;
import grammar.*;

public class GSwarm extends PSO {
	
	private Grammar _grammar;

	public GSwarm() {
		loadGrammar();
		start();
	}
	
	public void loadGrammar(){
		_grammar = new Grammar();
		
		Rule rule1;
		rule1 = new Rule();
		rule1.setNonTerminal("");
		rule1.addExpression("");
		rule1.addExpression("");
		
		_grammar.addRule(rule1);
	}

}
