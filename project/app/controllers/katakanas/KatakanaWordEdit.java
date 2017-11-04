package controllers.katakanas;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import base.utility.linq.Linq;
import core.controller.HtmlControllerBase;
import core.controller.Route;
import core.controller.validation.annotation.Id;
import logic.words.KatakanaWordsLogic;
import po.IKatakanaWord;
import po.KatakanaWordContext;
import po.KatakanaWordType;

public final class KatakanaWordEdit extends HtmlControllerBase{

	public static void index(@Id long id, String refer) {
		IKatakanaWord word = KatakanaWordsLogic.getKatakanawordAndUpdate(id);
		if(word == null) {
			notFound("片假名词不存在：id="+id);
		}
		
		jsArgs.put("id", id);
		
		WordVO wordVO = new WordVO();
		wordVO.value = word.getValue();
		wordVO.alias = word.getAlias();
		
		StringBuilder meaningsSb = new StringBuilder();
		for(String meaning : word.getMeanings()) {
			wordVO.meanings.add(meaning);
			meaningsSb.append(meaning).append("\n");
		}
		wordVO.meaningsText = meaningsSb.toString();
		
		renderArgs.put("word", wordVO);
		
		List<TypeVO> typesVO = new ArrayList<>();
		Set<KatakanaWordType> types = Linq.from(word.getTypes()).toSet();
		for(KatakanaWordType type : KatakanaWordType.values()) {
			TypeVO typeVO = new TypeVO();
			typeVO.name = type.toString();
			if(types.contains(type)) {
				typeVO.has = true;
			}
			typesVO.add(typeVO);
		}
		renderArgs.put("types", typesVO);
		
		List<ContextVO> contextsVO = new ArrayList<>();
		for(KatakanaWordContext context : KatakanaWordContext.values()) {
			ContextVO contextVO = new ContextVO();
			contextVO.name = context.toString();
			if(word.getContext() == context) {
				contextVO.has = true;
			}
			contextsVO.add(contextVO);
		}
		renderArgs.put("contexts", contextsVO);
		
		if(refer == null) {
			refer = Route.get(KatakanaWordIndex.class, "index");
		}
		
		renderArgs.put("refer", refer);
		jsArgs.put("refer", refer);
		
		render("katakanas/katakana-edit");
	}
	
	private static class WordVO{
		public String value;
		public List<String> meanings = new ArrayList<>();
		public String meaningsText;
		public String alias;
	}
	
	private static class TypeVO{
		public String name;
		public boolean has;
	}
	
	private static class ContextVO{
		public String name;
		public boolean has;
	}
	
}
