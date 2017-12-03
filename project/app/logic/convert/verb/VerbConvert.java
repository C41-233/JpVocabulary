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
	未然型2(new VerbWeiRanXing2()),
	假定型(new VerbJiaDingXing()),
	命令型1(new VerbMingLingXing1()),
	命令型2(new VerbMingLingXing2()),
	意志型(new VerbYiZhiXing()),
	推量型(new VerbTuiLiangXing()),
	可能态1(new VerbKeNengTai1()),
	可能态2(new VerbKeNengTai2()),
	被动态1(new VerbBeiDongTai1()),
	被动态2(new VerbBeiDongTai2()),
	自发态1(new VerbZiFaTai1()),
	自发态2(new VerbZiFaTai2()),
	使役态1(new VerbShiYiTai1()),
	使役态2(new VerbShiYiTai2()),
	被役态1(new VerbBeiYiTai1()),
	被役态2(new VerbBeiYiTai2()),
	连用型CJ(new VerbLianYongXingCJ()),
	连用型D(new VerbLianYongXingD()),
	未然型N(new VerbWeiRanXingN()),
	未然型Z(new VerbWeiRanXingZ()),
	假定型B(new VerbJiaDingXingB()),
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
