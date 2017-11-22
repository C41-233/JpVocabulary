package logic.convert.verb;

import po.VerbWordType;

public enum VerbConvert {

	原型(new VerbYuanXing()),
	终止型(new VerbYuanXing()),
	连体型(new VerbYuanXing()),
	连用型1(new VerbLianYongXing1()),
	连用型2(new VerbLianYongXing2()),
	连用型M(new VerbLianYongXingM()),
	连用型T(new VerbLianYongXingT()),
	未然型1(new VerbWeiRanXing1()),
	;
	
	private final IVerbConvertor convertor;
	
	VerbConvert(IVerbConvertor convertor) {
		this.convertor = convertor;
	}
	
	public String convert(String value, VerbWordType type) {
		switch (type) {
		case 一类动词:
			return convertor.from1(value);
		case 二类动词:
			return convertor.from2(value);
		case サ变动词:
			return convertor.fromS(value);
		case カ变动词:
			return convertor.fromK(value);
		case ラ变动词:
			return convertor.fromR(value);
		default:
			return null;
		}
	}
	
}
