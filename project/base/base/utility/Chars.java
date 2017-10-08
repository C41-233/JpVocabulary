package base.utility;

public final class Chars {

	private Chars() {}
	
	public static boolean isAscii(char ch) {
		return ch <= 256;
	}
	
	public static boolean isDigit(char ch) {
		return ch >= '0' && ch <= '9';
	}
	
	public static boolean isCJKCharacter(char ch) {
		return ch >= 0x4E00 && ch <= 0x9FCC;
	}
	
}
