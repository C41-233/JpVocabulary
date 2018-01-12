package c41.utility;

public final class Chars {

	private Chars() {}
	
	//ASCII字符
	public static boolean isAscii(int ch) {
		return ch <= 256;
	}
	
	//基本拉丁数字
	public static boolean isBasicLatinDigit(int ch) {
		return ch >= '0' && ch <= '9';
	}
	
	//CJK统一表意字符
	public static boolean isCJKUnifiedIdeograph(int ch) {
		return ch >= 0x4E00 && ch <= 0x9FCC;
	}
	
	//平假名
	public static boolean isHiragana(int ch) {
		return ch >= 0x3040 && ch <= 0x309F;
	}
	
	//片假名及片假名扩展
	public static boolean isKatakana(int ch) {
		return ch >= 0x30A0 && ch <= 0x30FF || ch >= 0x31F0 && ch <= 0x31FF; 
	}
	
	
}
