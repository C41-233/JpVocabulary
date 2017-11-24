package logic.convert.verb;

import logic.convert.SuffixReplace;

class VerbKeNengTai1 implements IVerbConvertor {

	@Override
	public String from1(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("う", "える");
		replace.add("く", "ける");
		replace.add("ぐ", "げる");
		replace.add("す", "せる");
		replace.add("つ", "てる");
		replace.add("ぬ", "ねる");
		replace.add("ぶ", "べる");
		replace.add("む", "める");
		replace.add("る", "れる");
		return replace.replace(word);
	}

	@Override
	public String from2(String word) {
		return null;
	}

	@Override
	public String fromS(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("する", "させる");
		return replace.replace(word);
	}

	@Override
	public String fromK(String word) {
		return null;
	}

	@Override
	public String fromR(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("る", "れる");
		return replace.replace(word);
	}

}
