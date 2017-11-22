package logic.convert.verb;

import logic.convert.SuffixReplace;

class VerbWeiRanXing1 implements IVerbConvertor {

	@Override
	public String from1(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("う", "わ");
		replace.add("く", "か");
		replace.add("ぐ", "が");
		replace.add("す", "さ");
		replace.add("つ", "た");
		replace.add("ぬ", "な");
		replace.add("ぶ", "ば");
		replace.add("む", "ま");
		replace.add("る", "ら");
		return replace.replace(word);
	}

	@Override
	public String from2(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("る", "");
		return replace.replace(word);
	}

	@Override
	public String fromS(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("する", "し");
		replace.add("ずる", "じ");
		return replace.replace(word);
	}

	@Override
	public String fromK(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("来る", "来");
		replace.add("くる", "こ");
		return replace.replace(word);
	}

	@Override
	public String fromR(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("る", "ら");
		return replace.replace(word);
	}

}
