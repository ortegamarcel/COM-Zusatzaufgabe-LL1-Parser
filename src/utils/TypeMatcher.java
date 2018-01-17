package utils;

public class TypeMatcher implements ITypeMatcher {
	@Override
	public Boolean isNum(String value) {
		try {
			int i = Integer.decode(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	@Override
	public Boolean isId(String value) {
		return !isNum(value);
	}
}
