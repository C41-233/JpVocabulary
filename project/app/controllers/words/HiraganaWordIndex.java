package controllers.words;

import java.util.ArrayList;
import java.util.List;

import base.utility.Strings;
import base.utility.linq.Linq;
import core.controller.HtmlControllerBase;
import core.controller.validation.annotation.Required;
import logic.indexes.IndexManager;

public final class HiraganaWordIndex extends HtmlControllerBase{

	public static void index() {
		page(IndexManager.Hiragana.getFirst());
	}
	
	public static void page(@Required String index) {
		if(IndexManager.Character.isValidIndex(index) == false && IndexManager.Hiragana.isValidIndex(index) == false) {
			notFound(Strings.format("%s not found.", index));
		}

		//TODO 标签化，有三处重复了，改成一个tag
		ArgVO arg = new ArgVO();
		arg.index = index;
		renderArgs.put("arg", arg);
			
		List<HiraganaIndexVO> indexesVO = Linq.from(IndexManager.Hiragana.getGroups())
				.union(IndexManager.Character.getGroups())
				.select((group, i)->{
					HiraganaIndexVO vo = new HiraganaIndexVO();
					vo.name = group.getName();
					vo.seq = i;
					for(String item : group.getItems()) {
						vo.items.add(item);
						if(item.equals(index)) {
							arg.group = vo.name;
						}
					}
					return vo;
				})
				.toList();
		
		renderArgs.put("indexes", indexesVO);

		render("words/word-hiragana-index");
	}

	private static class ArgVO{
		String group;
		String index;
	}

	private static class HiraganaIndexVO{
		String name;
		List<String> items = new ArrayList<>();
		int seq;
	}
	
}
