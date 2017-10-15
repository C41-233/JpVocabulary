package logic.pinyins;

public final class Pinyins{

	private Pinyins() {}
	
	private static final char[][] ss = new char[][] {
		{'a', 'ā', 'á', 'ǎ', 'à'},
		{'e', 'ē', 'é', 'ě', 'è'},
		{'i', 'ī', 'í', 'ǐ', 'ì'},
		{'o', 'ō', 'ó', 'ǒ', 'ò'},
		{'u', 'ū', 'ú', 'ǔ', 'ù'},
		{'ü', 'ǖ', 'ǘ', 'ǚ', 'ǜ'},
	};
	
	public static String toPinyin(String index) {
		String token = index.substring(0, index.length()-1);
		int seq = index.charAt(index.length()-1) - '0';
		
		token = token.replace('v', 'ü');
		for(char[] line : ss) {
			int i = token.indexOf(line[0]);
			if(i >= 0) {
				return token.replace(line[0], line[seq]);
			}
		}
		return token;
	}
	
	public static String toAlphaIndex(String index) {
		return index.substring(0, index.length()-1);
	}
	
}