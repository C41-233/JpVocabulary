package logic.convert.adjective;

import logic.convert.SuffixReplace;

class AdjWeiRanXing1 implements IAdjConvert{

	@Override
	public String convert(String value, String syllable) {
		if(value.equals("いい")) {
			return "よかろ";
		}
		SuffixReplace replace = new SuffixReplace();
		replace.add("い", "かろ");
		return replace.replace(value);
	}

}
