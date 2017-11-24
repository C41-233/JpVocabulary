package logic.convert.verb;

import logic.convert.SuffixReplace;

class VerbYiZhiXing implements IVerbConvertor {

	@Override
	public String from1(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("う", "おう");
		replace.add("く", "こう");
		replace.add("ぐ", "ごう");
		replace.add("す", "そう");
		replace.add("つ", "とう");
		replace.add("ぬ", "のう");
		replace.add("ぶ", "ぼう");
		replace.add("む", "もう");
		replace.add("る", "ろう");
		return replace.replace(word);
	}

	@Override
	public String from2(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("る", "よう");
		return replace.replace(word);
	}

	@Override
	public String fromS(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("する", "しよう");
		replace.add("ずる", "じよう");
		return replace.replace(word);
	}

	@Override
	public String fromK(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("来る", "来よう");
		replace.add("くる", "こよう");
		return replace.replace(word);
	}

	@Override
	public String fromR(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("る", "ろう");
		return replace.replace(word);
	}

}
