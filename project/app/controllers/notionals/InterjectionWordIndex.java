package controllers.notionals;

import java.util.ArrayList;
import java.util.List;

import base.utility.comparator.Comparators;
import core.controller.HtmlControllerBase;
import logic.words.NotionalWordsQueryLogic;
import po.INotionalWord;
import po.INotionalWordValue;

public class InterjectionWordIndex extends HtmlControllerBase{

	public static void index() {
		List<INotionalWord> words = NotionalWordsQueryLogic.findInterjectionWords();
		List<WordVO> wordsVO = new ArrayList<>();
		for(INotionalWord word : words) {
			WordVO vo = new WordVO();
			vo.id = word.getId();
			for(INotionalWordValue value : word.getValues()) {
				vo.values.add(value.getValue());
			}
			for(String meaning : word.getMeanings()) {
				vo.meanings.add(meaning);
			}
			wordsVO.add(vo);
		}
		
		wordsVO.sort((w1, w2)->Comparators.compare(w1.values.get(0), w2.values.get(0)));
		
		renderArgs.put("words", wordsVO);
		render("notionals/interjection-index");
	}
	
	private static class WordVO{
		public long id;
		public List<String> values = new ArrayList<>();
		public List<String> meanings = new ArrayList<>();
		
	}
	
}
