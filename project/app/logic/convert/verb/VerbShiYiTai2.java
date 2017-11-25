package logic.convert.verb;

import logic.convert.SuffixReplace;

class VerbShiYiTai2 implements IVerbConvertor {

	@Override
	public String fromS(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("する", "しさせる");
		return replace.replace(word);
	}

	@Override
	public String from1(String word) {
		return null;
	}

	@Override
	public String from2(String word) {
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
