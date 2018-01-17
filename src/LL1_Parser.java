import java.util.Stack;
import utils.ITypeMatcher;

public class LL1_Parser {
	
	private String[] input;
	private int inputIndex = -1;
	private Stack<String> stack = new Stack<String>();
	private String[][][] table;
	private String[] nonTerminals;
	private String[] terminals;
	private String idTerminal = "id";
	private String numTerminal = "num";
	private InputParser inputParser;
	private ITypeMatcher typeMatcher;
	
	public LL1_Parser(String[][][] table, String[] nonTerminals, String[] terminals, ITypeMatcher typeMatcher) {
		this.table = table;
		this.nonTerminals = nonTerminals;
		this.terminals = terminals;
		this.inputParser = new InputParser(nonTerminals, terminals);
		this.typeMatcher = typeMatcher;
	}
			
	public LL1_Parser(String[][][] table, String[] nonTerminals, String[] terminals, ITypeMatcher typeMatcher, String idTerminal, String numTerminal) {
		this(table, nonTerminals, terminals, typeMatcher);
		this.idTerminal = idTerminal;
		this.numTerminal = numTerminal;
	}
	
	public void parse(String rawInput) {
		input = inputParser.parse(rawInput);
		inputIndex = -1;
		stack.push(input[0]);
		stack.push("stmt");
		
		String token = readNextToken();
		String top = null;
		
		do {
			top = stack.pop();
			
			System.out.println("top: " + top + " | token: " + token);
			
			if (isNonTerminal(top)) {
				String[] rule = getRule(top, token);
				if (rule == null) {
					System.out.println("Error: No rule found for: " + top + " -> " + token);
					break;
				}
				System.out.println(top + " -> " + String.join(" ", rule));
				pushRule(rule);
			} else if (isTerminal(top)) {
				if (top.equals(token) || 
						(top.equals(numTerminal) && typeMatcher.isNum(token)) || 
						(top.equals(idTerminal)&& typeMatcher.isId(token))) {
					System.out.println("Matching terminal: " + token);
					token = readNextToken();
				} else {
					System.out.println("Error: " + top + " should be " + token);
					break;
				}
			} else {
				System.out.println("Unexpected error! Caused by: " + token);
				break;
			}
			
			if (token.equals("$")) {
				break;
			}
		} while(true);
		
		if(token.equals("$")) {
			System.out.println("> Input \"" + rawInput + "\" is accepted by LL(1)-Parser");   
        } else {
			System.out.println("> Input \"" + rawInput + "\" is not accepted by LL(1)-Parser");   
		}
	}
	
	private void pushRule(String[] rule) {
		for (int i = rule.length-1; i >= 0; i--) {
			if (!rule[i].equals("")) {
				stack.push(rule[i]);
			}
		}
	}
	
	private String[] getRule(String nonTerminal, String terminal) {
		int row = getNonTerminalIndex(nonTerminal);
		int column = getTerminalIndex(terminal);
		String[] rule = table[row][column];
		
		return rule;
	}
	
	private int getNonTerminalIndex(String nonTerminal) {
		int i = 0;
		for (i = 0; i < nonTerminals.length; i++) {
			if (nonTerminals[i].equals(nonTerminal)) {
				return i;
			}
		}
		System.out.println("NonTerminal \"" + nonTerminal + "\" not found.");
		return -1;
	}
	
	private int getTerminalIndex(String terminal) {
		int i = 0;
		for (i = 0; i < terminals.length; i++) {
			if (terminals[i].equals(terminal)) {
				return i;
			}
		}
		if (typeMatcher.isNum(terminal)) {
			System.out.println(terminal + " interpreted as " + numTerminal + ".");
			return getTerminalIndex(numTerminal);
		} else if (typeMatcher.isId(terminal)) {
			System.out.println(terminal + " interpreted as " + idTerminal + ".");
			return getTerminalIndex(idTerminal);
		}
		return -1;
	}
	
	private Boolean isNonTerminal(String value) {
		for (String nonTerminal : nonTerminals) {
			if (nonTerminal.equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	private Boolean isTerminal(String value) {
		for (String terminal : terminals) {
			if (terminal.equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	private String readNextToken() {
		return input[++inputIndex];
	}
}
