package utils;

public class TypeMatcher implements ITypeMatcher {
	@Override
	public Boolean isNum(String value) {
		return value.matches("[0-9]+");
	}
	
	@Override
	public Boolean isId(String value) {
		return value.matches("([a-zA-Z][a-zA-Z_0-9]*)|(_[a-zA-Z0-9_]+)");
	}
}
