package controllers.characters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import base.utility.Strings;
import core.controller.HtmlControllerBase;
import core.controller.validation.Length;
import core.controller.validation.Required;
import logic.characters.CharacterIndexManager;
import logic.characters.ICharacterIndexGroup;
import sun.launcher.resources.launcher;
import sun.security.mscapi.PRNG;

public final class CharacterIndex extends HtmlControllerBase{

	public static void index(){
		page("ai");
	}
	
	public static void page(
		@Required @Length(min=1, max=7) String index
	){
		String argGroup = String.valueOf(index.charAt(0)).toUpperCase();
		
		//索引
		boolean find = false;
		List<CharacterIndexVO> indexesVO = new ArrayList<>();
		
		{
			int seq = 0;
			for(ICharacterIndexGroup group : CharacterIndexManager.getCache()) {
				CharacterIndexVO vo = new CharacterIndexVO();
				vo.name = group.getName();
				vo.seq = seq;
				for(String item : group.getItems()) {
					vo.items.add(item);
					if(Objects.equals(argGroup, vo.name) && Objects.equals(item, index)) {
						find = true;
					}
				}
				indexesVO.add(vo);
				seq++;
			}
		}
		
		renderArgs.put("indexes", indexesVO);
		
		Arg arg = new Arg();
		arg.group = argGroup;
		arg.index = index;
		renderArgs.put("arg", arg);

		if(find == false) {
			notFound(Strings.format("%s not found.", index));
		}
		
		//汉字组
		List<SimpleCharacterGroup> characterGroupsVO = new ArrayList<>();
		//debug
		{
			SimpleCharacterGroup group = new SimpleCharacterGroup();
			group.name = "ai1";
			
			SimpleCharacterVO character = new SimpleCharacterVO();
			group.characters.add(character);
			character.jp = "我";
			character.cn = "我";
			
			SimpleCharacterSyllable pron = new SimpleCharacterSyllable();
			character.syllables.add(pron);
			pron.value = "あいお";
			
			WordPairVO pair = new WordPairVO();
			pron.words.add(pair);
			pron.words.add(pair);
			pair.syllable = "いき";
			pair.word = "生き";
			
			character.fixwords.add(pair);
			character.fixwords.add(pair);
			
			characterGroupsVO.add(group);
		}
		renderArgs.put("characterGroups", characterGroupsVO);
		render("characters/index");
	}
	
	private static class CharacterIndexVO{
		String name;
		List<String> items = new ArrayList<>();
		int seq;
	}
	
	private static class Arg{
		String group;
		String index;
	}
	
	private static class SimpleCharacterGroup{
		String name;
		List<SimpleCharacterVO> characters = new ArrayList<>();
	}
	
	private static class SimpleCharacterVO{
		String jp;
		String cn;
		List<SimpleCharacterSyllable> syllables = new ArrayList<>();
		List<WordPairVO> fixwords = new ArrayList<>();
	}

	private static class SimpleCharacterSyllable{
		String value;
		List<WordPairVO> words = new ArrayList<>();
	}
	
	private static class WordPairVO{
		String word;
		String syllable;
	}
	
}
