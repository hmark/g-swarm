package gswarm.grammar;


import java.util.ArrayList;

/**
 * Trieda reprezentujuca BNF gramatiku.
 * @author Marek Hlav·Ë <mark.hlavac@gmail.com>
 *
 */
public class Grammar {
	
	private ArrayList<Rule> _rules;
	private int _rulesNum = 0;

	/**
	 * Konstruktor.
	 */
	public Grammar(){
		_rules = new ArrayList<Rule>();
	}
	
	/**
	 * Pridaj pravidlo.
	 * @param rule
	 */
	public void addRule(Rule rule){
		_rules.add(rule);
		_rulesNum++;
	}
	
	/**
	 * Vrat pravidlo na indexe.
	 * @param index
	 * @return	pravidlo
	 */
	public Rule getRuleAt(int index){
		return _rules.get(index);
	}
	
	/**
	 * Vrat pravidlo pomocou neterminalneho symbolu.
	 * @param nonTerminal	neterminalny symbol
	 * @return	pravidlo
	 */
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
	
	/**
	 * Vrat pocet pravidiel.
	 * @return
	 */
	public int getRulesNum(){
		return _rules.size();
	}
}
