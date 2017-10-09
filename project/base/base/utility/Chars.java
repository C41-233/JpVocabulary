package base.utility;

public final class Chars {

	private Chars() {}
	
	public static boolean isAscii(int ch) {
		return ch <= 256;
	}
	
	public static boolean isDigit(int ch) {
		return ch >= '0' && ch <= '9';
	}
	
	public static boolean isCJKCharacter(int ch) {
		return ch >= 0x4E00 && ch <= 0x9FCC;
	}
	
}
