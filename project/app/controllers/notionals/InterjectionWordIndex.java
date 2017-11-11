package controllers.notionals;

import java.util.ArrayList;
import java.util.List;

import core.controller.HtmlControllerBase;
import logic.words.NotionalWordsQueryLogic;
import po.INotionalWord;

public class InterjectionWordIndex extends HtmlControllerBase{

	public static void index() {
		List<INotionalWord> words = NotionalWordsQueryLogic.findInterjectionWords();
		List<WordVO> wordsVO = new ArrayList<>();
		for(INotionalWord word : words) {
			WordVO vo = new WordVO();
			vo.id = word.getId();
			for(String syllable : word.getSyllables()) {
				vo.values.add(syllable);
			}
			for(String meaning : word.getMeanings()) {
				vo.meanings.add(meaning);
			}
			wordsVO.add(vo);
		}
		renderArgs.put("words", wordsVO);
	}
	
	private static class WordVO{
		public long id;
		public List<String> values = new ArrayList<>();
		public List<String> meanings = new ArrayList<>();
		
	}
	
}
