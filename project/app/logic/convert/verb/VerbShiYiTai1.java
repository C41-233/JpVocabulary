package logic.convert.verb;

import logic.convert.SuffixReplace;

class VerbShiYiTai1 implements IVerbConvertor {

	@Override
	public String from1(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("う", "わせる");
		replace.add("く", "かせる");
		replace.add("ぐ", "がせる");
		replace.add("す", "させる");
		replace.add("つ", "たせる");
		replace.add("ぬ", "なせる");
		replace.add("ぶ", "ばせる");
		replace.add("む", "ませる");
		replace.add("る", "らせる");
		return replace.replace(word);
	}

	@Override
	public String from2(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("る", "させる");
		return replace.replace(word);
	}

	@Override
	public String fromS(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("する", "させる");
		return replace.replace(word);
	}

	@Override
	public String fromK(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("来る", "来させる");
		replace.add("くる", "こさせる");
		return replace.replace(word);
	}

	@Override
	public String fromR(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("る", "らせる");
		return replace.replace(word);
	}

}
