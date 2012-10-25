package gswarm;

import generator.BehaviorGenerator;
import grammar.Rule;
import pso.Particle;
import pso.Setup;

public class GSwarmBehaviorGenerator extends BehaviorGenerator {

	public GSwarmBehaviorGenerator(String key, String filename){
		super(key, filename);
	}
	
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
			derivationValue = particle.getLocationValueAt(vectorIndex);
			ruleIndex = derivationValue % expressionsNum;
			System.out.println(derivationValue + " % " + expressionsNum + " = " + ruleIndex);
			expression = rule.getExpressionAt(ruleIndex);
			
			//System.out.println("replace " + nonTerminal + " with " + expression);
			_body = _body.substring(0, index) + expression + _body.substring(index + 6);
			//System.out.println(_body);
			
			if (++vectorIndex >= dims)
				vectorIndex = 0;
		}
		
		return vectorIndex;
	}
}
