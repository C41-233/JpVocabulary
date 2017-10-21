package po;

import base.utility.linq.Linq;

public final class NotionalWordType {

	private static String[] types = new String[] {
		//实词
		"名词", 
		"自动词",
		"他动词",
		"形容动词",
		
		//虚词
		"副词",
		"代词",
		"连体词",
		"接续词",
		"疑问词",
		"接头词",
		"数量词",
		"叹词",
	};
	
	public static Iterable<String> all(){
		return Linq.from(types);
	}
	
	public static int getValue(String type) {
		return Linq.from(types).findIndex(type);
	}
	
	public static boolean isValidType(String type) {
		return Linq.from(types).isExist(type);
	}
	
}
