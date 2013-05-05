package gswarm.grammar;

import java.util.ArrayList;

/**
 * Trieda reprezentujuca pravidlo.
 * @author Marek Hlav·Ë <mark.hlavac@gmail.com>
 *
 */
public class Rule {
	
	private String _nonTerminal;
	private ArrayList<String> _expressions;
	
	/**
	 * Kontruktor.
	 */
	public Rule(){
		_expressions = new ArrayList<String>();
	}
	
	/**
	 * Pridaj vyraz (pravu stranu pravidla).
	 * @param rule
	 */
	public void addExpression(String rule){
		_expressions.add(rule);
	}
	
	/**
	 * Vrat vyraz na pravej strane pravidla.
	 * @param index
	 * @return
	 */
	public String getExpressionAt(int index){
		return _expressions.get(index);
	}
	
	/**
	 * Vrat pocet vyrazov (pocet moznych pravych stran pravidla).
	 * @return
	 */
	public int getExpressionsNumber(){
		return _expressions.size();
	}

	/**
	 * Vrat neterminalny symbol.
	 * @return
	 */
	public String getNonTerminal() {
		return _nonTerminal;
	}

	/**
	 * Nastav neterminalny symbol.
	 * @param _nonTerminal
	 */
	public void setNonTerminal(String _nonTerminal) {
		this._nonTerminal = _nonTerminal;
	}
}
