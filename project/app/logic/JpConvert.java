package logic;

import java.util.Arrays;
import java.util.List;

import base.utility.Chars;
import base.utility.linq.IReferenceEnumerable;
import base.utility.linq.Linq;
import logic.characters.CharactersLogic;
import po.ICharacter;

public class JpConvert {

	public static List<String> toJpCharacter(String s){
		IReferenceEnumerable<String> rst = Linq.from(Arrays.asList(""));
		for(String value : Linq.from(s).select(c->String.valueOf(c))) {
			List<ICharacter> characters = CharactersLogic.findCharactersByCn(value);
			if(characters.size() == 0) {
				rst = rst.join(Arrays.asList(value), (s1,s2)->s1+s2);
			}
			else {
				rst = rst.join(characters, (s1,s2)->s1+s2.getJpValue());
			}
		}
		
		return rst.toList();
	}
	
	public static String toKatakana(String s) {
		StringBuilder sb = new StringBuilder();
		Linq.from(s).foreach(ch->{
			if(Chars.isHiragana(ch)) {
				sb.append((char)(ch + 0x60));
			}
			else {
				sb.append(ch);
			}
		});
		return sb.toString();
	}
	
}
