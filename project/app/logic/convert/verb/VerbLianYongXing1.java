package logic.convert.verb;

import logic.convert.SuffixReplace;

class VerbLianYongXing1 implements IVerbConvertor {

	@Override
	public String from1(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("う", "い");
		replace.add("く", "き");
		replace.add("ぐ", "ぎ");
		replace.add("す", "し");
		replace.add("つ", "ち");
		replace.add("ぬ", "に");
		replace.add("ぶ", "び");
		replace.add("む", "み");
		replace.add("る", "り");
		return replace.replace(word);
	}

	@Override
	public String from2(String word) {
		if(word.endsWith("る")) {
			return word.substring(0, word.length()-1);
		}
		return null;
	}

	@Override
	public String fromS(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("する", "し");
		replace.add("ずる", "じ");
		return replace.replace(word);
	}

	@Override
	public String fromK(String word) {
		switch(word) {
		case "来る": return "来";
		case "くる": return "き";
		}
		return null;
	}

	@Override
	public String fromR(String word) {
		if(word.endsWith("る")) {
			return word.substring(0, word.length()-1)+"り";
		}
		return null;
	}

}
