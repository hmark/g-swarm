package grammar;

import java.util.ArrayList;

public class Grammar {
	
	private ArrayList<Rule> _rules;
	private int _rulesNum = 0;

	public Grammar(){
		_rules = new ArrayList<Rule>();
	}
	
	public void addRule(Rule rule){
		_rules.add(rule);
		_rulesNum++;
	}
	
	public Rule getRuleAt(int index){
		return _rules.get(index);
	}
	
	public Rule getRuleByNonTerminal(String nonTerminal){
		Rule rule;
		for (int i = 0; i < _rulesNum; i++){
			rule = _rules.get(i);
			
			if (rule.getNonTerminal().equals(nonTerminal))
				return rule;
		}
		
		try {
			throw new Exception("GrammarError: unable to load rule with nonterminal: '" + nonTerminal + "'.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		
		return null;
	}
	
	public int getRulesNum(){
		return _rules.size();
	}
}
