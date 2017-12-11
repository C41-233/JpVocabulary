package controllers.sentences;

import java.util.ArrayList;
import java.util.List;

import base.utility.comparator.Comparators;
import base.utility.linq.Linq;
import core.controller.HtmlControllerBase;
import logic.words.NotionalWordsQueryLogic;
import po.INotionalWord;
import po.INotionalWordValue;
import po.NotionalWordValueType;

public class Proverb extends HtmlControllerBase{

	public static void index() {
		List<INotionalWord> words = NotionalWordsQueryLogic.findProverbs();
		List<WordVO> wordsVO = new ArrayList<>();
		for(INotionalWord word : words) {
			String syllable = Linq.from(word.getSyllables()).first();
			for(INotionalWordValue value : word.getValues()) {
				if(value.getType() == NotionalWordValueType.Syllable) {
					continue;
				}
				WordVO vo = new WordVO();
				vo.id = word.getId();
				vo.value = value.getValue();
				vo.syllable = syllable;
				vo.key = Linq.from(value.getIndexes()).first();
				for(String meaning : word.getMeanings()) {
					vo.meanings.add(meaning);
				}
				wordsVO.add(vo);
			}
		}
		
		wordsVO.sort((v1, v2)->Comparators.compare(v1.key, v2.key));
		
		renderArgs.put("words", wordsVO);
		render("sentences/proverbs");
	}
	
	private static class WordVO{
		public String key;
		public long id;
		public String value;
		public String syllable;
		public List<String> meanings = new ArrayList<>();
	}
	
}
