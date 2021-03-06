package logic.convert.verb;

import logic.convert.SuffixReplace;

class VerbLianYongXing1 implements IVerbConvertor {

	@Override
	public String from1(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("う", "い");
		replace.add("く", "き");
		replace.add("ぐ", "ぎ");
		replace.add("す", "し");
		replace.add("つ", "ち");
		replace.add("ぬ", "に");
		replace.add("ぶ", "び");
		replace.add("む", "み");
		replace.add("る", "り");
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
		replace.add("くる", "き");
		return replace.replace(word);
	}

	@Override
	public String fromR(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("る", "り");
		return replace.replace(word);
	}

}
