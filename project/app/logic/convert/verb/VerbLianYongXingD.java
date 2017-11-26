package logic.convert.verb;

import logic.convert.SuffixReplace;

class VerbLianYongXingD extends VerbLianYongXing2{

	@Override
	public String from1(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("いく", "いった");
		replace.add("行く", "行った");
		replace.add("う", "った");
		replace.add("く", "いた");
		replace.add("ぐ", "いだ");
		replace.add("す", "した");
		replace.add("つ", "った");
		replace.add("ぬ", "んだ");
		replace.add("ぶ", "んだ");
		replace.add("む", "んだ");
		replace.add("る", "った");
		return replace.replace(word);
	}

	@Override
	public String from2(String word) {
		return super.from2(word) + "た";
	}

	@Override
	public String fromS(String word) {
		return super.fromS(word) + "た";
	}

	@Override
	public String fromK(String word) {
		return super.fromK(word) + "た";
	}

	@Override
	public String fromR(String word) {
		return super.fromR(word) + "た";
	}

}
