package logic.characters;

import java.util.Arrays;
import java.util.List;

import base.utility.linq.IReferenceEnumerable;
import base.utility.linq.Linq;
import po.ICharacter;

public class CharactersConvert {

	public static List<String> convert(String s){
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
	
}
