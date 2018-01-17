import java.util.ArrayList;
import java.util.List;

public class InputParser {
	private String[] nonTerminals;
	private String[] terminals;
	
	public InputParser(String[] nonTerminals, String[] terminals) {
		this.nonTerminals = nonTerminals;
		this.terminals = terminals;
	}
	
	public String[] parse(String input) {
		List<String> inputList = new ArrayList<String>();
		String tmp = "";
		
		for (int i = 0; i < input.length(); i++) {
			char nextChar = input.charAt(i);
			String nextCharAsString = String.valueOf(nextChar);
			
			if (nextChar == ' ' || nextChar == ')') {
				if (!tmp.equals("")) {
					inputList.add(tmp);
					tmp = "";
				}
				if (isValidInput(nextCharAsString)) {
					inputList.add(nextCharAsString);
				}
			} else if (isValidInput(nextCharAsString)) {
				inputList.add(nextCharAsString);
			} else {
				tmp += String.valueOf(nextChar);
				if (tmp.equals("$")) {
					inputList.add(tmp);
					tmp = "";
				}
			}
		}
		
		if (!tmp.equals("")) {
			throw new RuntimeException("Input-String is not valid: " + input + tmp);
		}
		
		return inputList.toArray(new String[] {});
	}
	
	public Boolean isValidInput(String input) {
		for (String elem : nonTerminals) {
			if (elem.equals(input)) {
				return true;
			}
		}
		for (String elem : terminals) {
			if (elem.equals(input)) {
				return true;
			}
		}
		return false;
	}
}
