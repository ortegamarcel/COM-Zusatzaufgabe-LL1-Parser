import utils.TypeMatcher;

public class Main {

	private static String[][][] table = new String[][][] {
		{{"(", "expr", ")"}, null, null, null, null, null, null},
		{null, null, {"func", "2terme", "terme"}, {"func", "2terme", "terme"}, null, null, null},
		{null, null, {"add"}, {"sub"}, null, null, null},
		{{"term", "term"}, {null}, {null}, {null}, {"term", "term"}, {"term", "term"}, null},
		{{"term", "terme"}, {""}, null, null, {"term", "terme"}, {"term", "terme"}, null},
		{{"stmt"}, null, null, null, {"id"}, {"num"}, null}
	};
	private static String[] nonTerminals = new String[] {"stmt", "expr", "func", "2terme", "terme", "term"};
	private static String[] terminals = new String[] {"(", ")", "add", "sub", "id", "num", "$"};
	
	public static void main(String[] args) {
		LL1_Parser parser = new LL1_Parser(table, nonTerminals, terminals, new TypeMatcher());
		parser.parse("(add 1 __)$");
		parser.parse("(add 1 _31_4324)$");
		parser.parse("(add __ (add 4 4) )$");
		parser.parse("(sub 123 a (sub 3 a b))$)$");
		parser.parse("(sub $$ (add _1232 0 (sub sublim (add addidas 4324324) addition)) (add 1 a))$");
	}
}
