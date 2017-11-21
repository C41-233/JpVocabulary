package logic.convert.verb;

interface IVerbConvertor {

	 //一类动词
	 public String from1(String word);
	 
	 //二类动词
	 public String from2(String word);
	 
	 //サ变动词
	 public String fromS(String word);
	 
	 //カ变动词
	 public String fromK(String word);
	 
	 //ラ变动词
	 public String fromR(String word);
	 
}
