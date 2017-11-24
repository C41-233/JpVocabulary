package logic.convert.verb;

import logic.convert.SuffixReplace;

class VerbBeiDongTai1 implements IVerbConvertor {

	@Override
	public String from1(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("う", "われる");
		replace.add("く", "かれる");
		replace.add("ぐ", "がれる");
		replace.add("す", "される");
		replace.add("つ", "たれる");
		replace.add("ぬ", "なれる");
		replace.add("ぶ", "ばれる");
		replace.add("む", "まれる");
		replace.add("る", "られる");
		return replace.replace(word);
	}

	@Override
	public String from2(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("る", "られる");
		return replace.replace(word);
	}

	@Override
	public String fromS(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("する", "しられる");
		replace.add("ずる", "じられる");
		return replace.replace(word);
	}

	@Override
	public String fromK(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("来る", "来られる");
		replace.add("くる", "こられる");
		return replace.replace(word);
	}

	@Override
	public String fromR(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("る", "られる");
		return replace.replace(word);
	}

}
