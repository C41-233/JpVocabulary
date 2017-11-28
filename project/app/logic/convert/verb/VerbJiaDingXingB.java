package logic.convert.verb;

class VerbJiaDingXingB extends VerbJiaDingXing {

	@Override
	public String from1(String word) {
		return super.from1(word) + "ば";
	}

	@Override
	public String from2(String word) {
		return super.from2(word) + "ば";
	}

	@Override
	public String fromS(String word) {
		return super.fromS(word) + "ば";
	}

	@Override
	public String fromK(String word) {
		return super.fromK(word) + "ば";
	}

	@Override
	public String fromR(String word) {
		return super.fromR(word) + "ば";
	}

}
