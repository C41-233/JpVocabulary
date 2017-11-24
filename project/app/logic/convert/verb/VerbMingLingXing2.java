package logic.convert.verb;

import logic.convert.SuffixReplace;

class VerbMingLingXing2 implements IVerbConvertor {


	@Override
	public String from2(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("る", "よ");
		return replace.replace(word);
	}

	@Override
	public String fromS(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("する", "せよ");
		replace.add("ずる", "ぜよ");
		return replace.replace(word);
	}

	@Override
	public String from1(String word) {
		return null;
	}

	@Override
	public String fromK(String word) {
		return null;
	}

	@Override
	public String fromR(String word) {
		return null;
	}

}
