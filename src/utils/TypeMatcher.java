package utils;

public class TypeMatcher implements ITypeMatcher {
	@Override
	public Boolean isNum(String value) {
		return value.matches("[0-9]+");
	}
	
	@Override
	public Boolean isId(String value) {
		return value.matches("[a-zA-Z_$][a-zA-Z_$0-9]*");
	}
}
