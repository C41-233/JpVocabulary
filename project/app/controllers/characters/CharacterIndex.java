package controllers.characters;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.sun.tracing.dtrace.ProviderAttributes;

import base.utility.Strings;
import core.controller.HtmlControllerBase;
import core.controller.validation.Length;
import logic.characters.CharacterIndexManager;
import logic.characters.ICharacterIndexGroup;

public final class CharacterIndex extends HtmlControllerBase{

	public static void index(){
		page("ai");
	}
	
	public static void page(
		@Length(min=1, max=7) String index
	){
		String argGroup = String.valueOf(index.charAt(0)).toUpperCase();
		
		boolean find = false;
		List<CharacterIndexVO> indexes = new ArrayList<>();
		
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
				indexes.add(vo);
				seq++;
			}
		}
		
		renderArgs.put("indexes", indexes);
		
		Arg arg = new Arg();
		arg.group = argGroup;
		arg.index = index;
		renderArgs.put("arg", arg);

		if(find == false) {
			notFound(Strings.format("%s not found.", index));
		}
		
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
	
}
