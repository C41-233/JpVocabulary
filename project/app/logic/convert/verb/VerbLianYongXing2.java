package logic.convert.verb;

import logic.convert.SuffixReplace;

class VerbLianYongXing2 implements IVerbConvertor {

	@Override
	public String from1(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("いく", "いっ");
		replace.add("行く", "行っ");
		replace.add("う", "っ");
		replace.add("く", "い");
		replace.add("ぐ", "い");
		replace.add("す", "し");
		replace.add("つ", "っ");
		replace.add("ぬ", "ん");
		replace.add("ぶ", "ん");
		replace.add("む", "ん");
		replace.add("る", "っ");
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
		replace.add("る", "っ");
		return replace.replace(word);
	}

}
