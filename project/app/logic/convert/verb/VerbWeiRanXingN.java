package logic.convert.verb;

class VerbWeiRanXingN extends VerbWeiRanXing1 {

	@Override
	public String from1(String word) {
		return super.from1(word) + "ない";
	}

	@Override
	public String from2(String word) {
		return super.from2(word) + "ない";
	}

	@Override
	public String fromS(String word) {
		return super.fromS(word) + "ない";
	}

	@Override
	public String fromK(String word) {
		return super.fromK(word) + "ない";
	}

	@Override
	public String fromR(String word) {
		return super.fromR(word) + "ない";
	}

}
