package grammar;

import java.util.ArrayList;

public class Grammar {
	
	private ArrayList<Rule> _rules;

	public Grammar(){
		_rules = new ArrayList<Rule>();
	}
	
	public void addRule(Rule rule){
		_rules.add(rule);
	}
	
	public Rule getRuleAt(int index){
		return _rules.get(index);
	}
	
	public int getRulesNum(){
		return _rules.size();
	}
}
