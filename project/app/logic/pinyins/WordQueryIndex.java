package logic.pinyins;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import base.utility.Chars;
import base.utility.linq.Linq;
import logic.characters.CharactersQueryLogic;
import po.ICharacter;

public final class WordQueryIndex {

	public static List<String> getWordQueryIndex(String word) {
		char ch = word.charAt(0);
		String first = String.valueOf(ch);
		if(Chars.isCJKUnifiedIdeograph(ch) == false) {
			return Arrays.asList(first);
		}
		ICharacter character = CharactersQueryLogic.findCharacter(first);
		if(character == null) {
			return Collections.emptyList();
		}
		return Linq.from(character.getPinyins()).toList();
	}
	
}
