package controllers.words;

import java.util.ArrayList;
import java.util.List;

import base.utility.Strings;
import base.utility.linq.Linq;
import core.controller.HtmlControllerBase;
import core.controller.validation.annotation.Required;
import logic.characters.CharacterIndexManager;

public class CharacterWordIndex extends HtmlControllerBase{

	public static void index() {
		page("ai");
	}
	
	public static void page(@Required String index) {
		if(CharacterIndexManager.isValidIndex(index) == false) {
			notFound(Strings.format("%s not found.", index));
		}
		
		ArgVO arg = new ArgVO();
		arg.index = index;
		renderArgs.put("arg", arg);
			
		List<CharacterIndexVO> indexesVO = Linq.from(CharacterIndexManager.getCache()).select((group, i)->{
			CharacterIndexVO vo = new CharacterIndexVO();
			vo.name = group.getName();
			vo.seq = i;
			for(String item : group.getItems()) {
				vo.items.add(item);
				if(item.equals(index)) {
					arg.group = vo.name;
				}
			}
			return vo;
		}).toList();
		
		renderArgs.put("indexes", indexesVO);
		
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
