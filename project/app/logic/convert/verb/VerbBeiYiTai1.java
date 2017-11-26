package logic.convert.verb;

import logic.convert.SuffixReplace;

class VerbBeiYiTai1 implements IVerbConvertor {

	@Override
	public String from1(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("う", "わされる");
		replace.add("く", "かされる");
		replace.add("ぐ", "がされる");
		replace.add("す", "さされる");
		replace.add("つ", "たされる");
		replace.add("ぬ", "なされる");
		replace.add("ぶ", "ばされる");
		replace.add("む", "まされる");
		replace.add("る", "らされる");
		return replace.replace(word);
	}

	@Override
	public String from2(String word) {
		return null;
	}

	@Override
	public String fromS(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("する", "させられる");
		return replace.replace(word);
	}

	@Override
	public String fromK(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("来る", "来させられる");
		replace.add("くる", "こさせられる");
		return replace.replace(word);
	}

	@Override
	public String fromR(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("る", "らされる");
		return replace.replace(word);
	}

}
