package po;

import base.utility.linq.Linq;

public enum NotionalWordType {

		//实词
		名词, 
		自动词,
		他动词,
		形容动词,
		
		//虚词
		副词,
		代词,
		连体词,
		接续词,
		疑问词,
		接头词,
		数量词,
		叹词;
	
	public static boolean isValidType(String type) {
		return Linq.from(NotionalWordType.values()).isExist(t->t.toString().equals(type));
	}
	
}
