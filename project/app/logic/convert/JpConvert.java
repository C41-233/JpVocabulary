package logic.convert;

import java.util.Arrays;
import java.util.List;

import c41.utility.Chars;
import c41.utility.linq.IReferenceEnumerable;
import c41.utility.linq.Linq;
import logic.characters.CharactersQueryLogic;
import po.ICharacter;

public class JpConvert {

	public static List<String> toJpCharacter(String s){
		IReferenceEnumerable<String> rst = Linq.from(Arrays.asList(""));
		for(String value : Linq.from(s).select(c->String.valueOf(c))) {
			List<ICharacter> characters = CharactersQueryLogic.findCharactersByCn(value);
			if(characters.size() == 0) {
				rst = rst.join(Arrays.asList(value), (s1,s2)->s1+s2);
			}
			else {
				rst = rst.join(characters, (s1,s2)->s1+s2.getJpValue());
			}
		}
		
		List<String> list = rst.toList();
		{
			int count = list.size();
			for(int i=0; i<count; i++) {
				char[] chars = list.get(i).toCharArray();
				boolean set = false;
				for(int j=1; j<chars.length; j++) {
					if(chars[j-1] == chars[j]) {
						chars[j] = '々';
						set = true;
					}
					else if(chars[j] == '々') {
						chars[j] = chars[j-1];
						set = true;
					}
				}
				if(set) {
					list.add(new String(chars));
				}
			}
		}
		return list;
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

	public static String toHiragana(String s) {
		StringBuilder sb = new StringBuilder();
		Linq.from(s).foreach(ch->{
			if(Chars.isKatakana(ch)) {
				sb.append((char)(ch - 0x60));
			}
			else {
				sb.append(ch);
			}
		});
		return sb.toString();
	}
	
}
