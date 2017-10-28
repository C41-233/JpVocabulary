package controllers.words;

import java.util.ArrayList;
import java.util.List;

import base.utility.linq.Linq;
import core.controller.HtmlControllerBase;
import core.controller.Route;
import core.controller.validation.annotation.Id;
import logic.LogicValidate;
import logic.words.VerbWordsLogic;
import po.IVerbWord;
import po.VerbWordType;

public class VerbWordDetail extends HtmlControllerBase{

	public static void index(@Id long id, String refer) {
		if(refer == null) {
			refer = Route.get(VerbWordIndex.class, "index");
		}
		
		renderArgs.put("refer", refer);
		
		IVerbWord word = VerbWordsLogic.getVerbWordAndUpdate(id);
		if(word == null) {
			notFound("动词不存在：id=%d"+id);
		}
		
		WordVO wordVO = new WordVO();
		wordVO.id = id;
		
		for(String value : 
			Linq.from(word.getValues())
				.select(w->w.getValue())
				.orderBy(VerbWordsLogic.ValueComparator)) {
			if(LogicValidate.isValidSyllable(value)) {
				wordVO.values2.add(value);
			}
			else {
				wordVO.values1.add(value);
			}
		}
		
		for(VerbWordType type : word.getTypes()) {
			wordVO.types.add(type.toFull());
		}
		
		renderArgs.put("word", wordVO);
		
		render("words/word-verb-detail");
	}
	
	private static class WordVO{
		public long id;
		public List<String> values1 = new ArrayList<>();
		public List<String> values2 = new ArrayList<>();
		public List<String> types = new ArrayList<>();
	}
	
}
