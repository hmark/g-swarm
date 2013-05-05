package gswarm.generators;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gswarm.Conf;
import gswarm.grammar.Grammar;
import gswarm.grammar.Rule;
import pso.Particle;
import pso.Setup;

/**
 * Trieda zabezpecujuca generovanie ciastkovych programov spravania robota.
 * @author Marek Hlav·Ë <mark.hlavac@gmail.com>
 *
 */
public class GSwarmBehaviorGenerator {
	
	private String _key;
	private String _filename;
	
	protected Grammar _grammar = new Grammar();
	protected String _body;

	/**
	 * Konstruktor.
	 * @param key			identifikacny kluc pre cast, ktora ma generovat program (napr. #gswarm-net.grm 
	 * @param filename		nazov suboru s prislusnou gramatikou (napr. gswarm-net.grm)
	 */
	public GSwarmBehaviorGenerator(String key, String filename){
		_key = key;
		_filename = filename;
	}
	
	/**
	 * Nastavenie tela na pociatocny vyraz. 
	 */
	protected void resetBody(){
		_body = Conf.INIT_STRING;
	}
	
	/**
	 * Vrat identifikacny kluc spravania.
	 * @return
	 */
	public String getKey(){
		return _key;
	}
	
	/**
	 * Vrat vygenerovane telo robota.
	 * @return
	 */
	public String getBody(){
		return _body;
	}
	
	/**
	 * Spracovanie suboru s BFN gramatikou a vytvorenie pravidiel. 
	 */
	public void loadGrammar(){
		try{
			// load grammar file
			FileInputStream fstream = new FileInputStream("conf/" + _filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			// load grammar and rules
			Rule rule;
			String strLine, nonTerminal;
			String []expressions;
			int rulesNum;
			Pattern nontermPattern = Pattern.compile("^<!...> =");
			Matcher matcher;
			
			while ((strLine = br.readLine()) != null)   {
				rule = new Rule();
				matcher = nontermPattern.matcher(strLine);
				matcher.find();
				
				nonTerminal = matcher.group(0).substring(0, 6);
				rule.setNonTerminal(nonTerminal);
				//System.out.println("NON: " + nonTerminal);
				
				expressions = strLine.substring(9).split("= | \\| ");
				rulesNum = expressions.length;
				for (int i = 0; i < rulesNum; i++){
					rule.addExpression(expressions[i]);
					//System.out.println("TERM: " + expressions[i]);
				}
				
				_grammar.addRule(rule);
			}
		}
		catch(Exception e){
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Vygeneruj telo na zaklade aktualnej pozicie castice.
	 * @param particle
	 * @param vectorIndex
	 * @return
	 */
	public int generateBody(Particle particle, int vectorIndex){
		resetBody();
		
		String nonTerminal, expression;
		int index, expressionsNum, derivationValue, ruleIndex;
		int dims = Setup.DIMENSIONS;
		Rule rule;
			
		while ((index = _body.indexOf("<!")) != -1){ // TODO - optimalization issue, WHILE cycle is ineffective because we are searching and replacing in two steps
			nonTerminal = _body.substring(index, index + 6);
			
			rule = _grammar.getRuleByNonTerminal(nonTerminal);
			expressionsNum = rule.getExpressionsNumber();
			derivationValue = particle.getLocationValueAt(vectorIndex % dims);
			ruleIndex = derivationValue % expressionsNum;
			//System.out.println(derivationValue + " % " + expressionsNum + " = " + ruleIndex);
			expression = rule.getExpressionAt(ruleIndex);
			
			//System.out.println("replace " + nonTerminal + " with " + expression);
			_body = _body.substring(0, index) + expression + _body.substring(index + 6);
			//System.out.println(_body);
			
			if (++vectorIndex > Setup.CONSTRAINT)
				return -1;
		}
		
		return vectorIndex;
	}
	
	/**
	 * Vygeneruj telo na zaklade najlepsej pozicie castice.
	 * @param particle
	 * @param vectorIndex
	 * @return
	 */
	public int generateBodyFromBestLocation(Particle particle, int vectorIndex){
		resetBody();
		
		String nonTerminal, expression;
		int index, expressionsNum, derivationValue, ruleIndex;
		int dims = Setup.DIMENSIONS;
		Rule rule;
			
		while ((index = _body.indexOf("<!")) != -1){ // TODO - optimalization issue, WHILE cycle is ineffective because we are searching and replacing in two steps
			nonTerminal = _body.substring(index, index + 6);
			
			rule = _grammar.getRuleByNonTerminal(nonTerminal);
			expressionsNum = rule.getExpressionsNumber();
			derivationValue = particle.getBestLocationValueAt(vectorIndex % dims);
			ruleIndex = derivationValue % expressionsNum;
			//System.out.println(derivationValue + " % " + expressionsNum + " = " + ruleIndex);
			expression = rule.getExpressionAt(ruleIndex);
			
			//System.out.println("replace " + nonTerminal + " with " + expression);
			_body = _body.substring(0, index) + expression + _body.substring(index + 6);
			//System.out.println(_body);
			
			if (++vectorIndex > Setup.CONSTRAINT)
				return -1;
		}
		
		return vectorIndex;
	}
}
