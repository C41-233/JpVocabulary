package controllers.words;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import base.utility.Strings;
import core.controller.HtmlControllerBase;
import core.controller.validation.annotation.Required;
import logic.characters.CharacterIndexManager;
import logic.characters.ICharacterIndexGroup;

public class CharacterWordIndex extends HtmlControllerBase{

	public static void index() {
		page("ai");
	}
	
	public static void page(@Required String index) {
		String argGroup = String.valueOf(index.charAt(0)).toUpperCase();

		{
			//查询参数填充
			ArgVO arg = new ArgVO();
			arg.group = argGroup;
			arg.index = index;
			renderArgs.put("arg", arg);
		}

		{
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
			if(find == false) {
				notFound(Strings.format("%s not found.", index));
			}
		}
		
		render("words/word-index");
	}

	private static class ArgVO{
		String group;
		String index;
	}

	private static class CharacterIndexVO{
		String name;
		List<String> items = new ArrayList<>();
		int seq;
	}
	
}
