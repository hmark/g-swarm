package grammar;

import java.util.ArrayList;

public class Rule {
	
	private String _nonTerminal;
	private ArrayList<String> _expressions;
	
	public Rule(){
		_expressions = new ArrayList<String>();
	}
	
	public void addExpression(String rule){
		_expressions.add(rule);
	}
	
	public String getExpressionAt(int index){
		return _expressions.get(index);
	}
	
	public int getExpressionsNumber(){
		return _expressions.size();
	}

	public String getNonTerminal() {
		return _nonTerminal;
	}

	public void setNonTerminal(String _nonTerminal) {
		this._nonTerminal = _nonTerminal;
	}
}
