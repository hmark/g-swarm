package grammar;

import java.util.ArrayList;

public class Grammar {
	
	private ArrayList<Rule> _rules;

	public Grammar(){
		_rules = new ArrayList<Rule>();
	}
	
	public void loadFromFile(String filename){
		
	}
	
	public void addRule(Rule rule){
		_rules.add(rule);
	}
}
