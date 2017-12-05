package logic.convert.adjective;

import logic.convert.SuffixReplace;

class AdjWeiRanXing2 implements IAdjConvert{

	@Override
	public String convert(String value, String syllable) {
		if(value.equals("いい")) {
			return "よから";
		}
		SuffixReplace replace = new SuffixReplace();
		replace.add("い", "から");
		return replace.replace(value);
	}

}
