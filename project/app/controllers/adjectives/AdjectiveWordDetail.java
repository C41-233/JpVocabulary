package controllers.adjectives;

import java.util.ArrayList;
import java.util.List;

import base.utility.linq.Linq;
import core.controller.HtmlControllerBase;
import core.controller.Route;
import core.controller.validation.annotation.Id;
import logic.LogicValidate;
import logic.words.AdjectiveWordsLogic;
import logic.words.VerbWordsLogic;
import po.IAdjectiveWord;

public final class AdjectiveWordDetail extends HtmlControllerBase{

	public static void index(@Id long id, String refer) {
		IAdjectiveWord word = AdjectiveWordsLogic.getAdjectiveWordAndUpdate(id);
		if(word == null) {
			notFound("形容词不存在：id="+id);
		}
		
		if(refer == null) {
			refer = Route.get(AdjectiveWordIndex.class, "index");
		}
		renderArgs.put("refer", refer);

		WordVO wordVO = new WordVO();
		wordVO.id = id;
		
		List<ConvertVO> convertsVO = new ArrayList<>();
		
		Linq.from(word.getValues())
			.select(w->w.getValue())
			.orderBy(VerbWordsLogic.ValueComparator)
			.foreach(value->{
				if(LogicValidate.isValidSyllable(value)) {
					wordVO.values2.add(value);
				}
				else {
					wordVO.values1.add(value);
				}
				ConvertVO convert = new ConvertVO();
				
				convertsVO.add(convert);
			});
			
		renderArgs.put("converts", convertsVO);
		
		Linq.from(word.getTypes()).foreach(t->wordVO.types.add(t.toString()));
		Linq.from(word.getMeanings()).select(m->m+"。").foreach(m->wordVO.meanings.add(m));
		Linq.from(word.getFixwords())
			.select(f->{
				FixwordVO vo = new FixwordVO();
				vo.value = f.getValue();
				vo.meaning = f.getMeaning();
				return vo;
			}).foreach(m->wordVO.fixwords.add(m));
		
		renderArgs.put("word", wordVO);
		
		render("adjs/adj-detail");
	}

	private static class WordVO{
		public long id;
		public List<String> values1 = new ArrayList<>();
		public List<String> values2 = new ArrayList<>();
		public List<String> types = new ArrayList<>();
		public List<String> meanings = new ArrayList<>();
		public List<FixwordVO> fixwords = new ArrayList<>();
	}
	
	private static class FixwordVO{
		public String value;
		public String meaning;
	}
	
	private static class ConvertVO{
		public String 基本型;
		public String 终止型;
		public String 连体型;
		public String 未然型1;
		public String 未然型2;
		public String 连用型1;
		public String 连用型2;
		public String 假定型;
		public String 意志型;
		public String 推量型;
		public String 连用型T;
		public String 连用型CJ;
		public String 连用型D;
		public String 假定型B;
		public String S;
		public String GZR;
	}
	
}
