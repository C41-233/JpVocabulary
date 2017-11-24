package logic.convert.verb;

import logic.convert.SuffixReplace;

class VerbJiaDingXing implements IVerbConvertor {

	@Override
	public String from1(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("う", "え");
		replace.add("く", "け");
		replace.add("ぐ", "げ");
		replace.add("す", "せ");
		replace.add("つ", "て");
		replace.add("ぬ", "ね");
		replace.add("ぶ", "べ");
		replace.add("む", "め");
		replace.add("る", "れ");
		return replace.replace(word);
	}

	@Override
	public String from2(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("る", "れ");
		return replace.replace(word);
	}

	@Override
	public String fromS(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("する", "すれ");
		replace.add("ずる", "ずれ");
		return replace.replace(word);
	}

	@Override
	public String fromK(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("来る", "来");
		replace.add("くる", "くれ");
		return replace.replace(word);
	}

	@Override
	public String fromR(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("る", "れ");
		return replace.replace(word);
	}

}
