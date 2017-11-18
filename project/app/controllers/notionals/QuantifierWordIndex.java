package controllers.notionals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import base.utility.collection.map.HashArrayListMultiMap;
import base.utility.linq.Linq;
import core.controller.HtmlControllerBase;
import logic.words.NotionalWordsQueryLogic;
import po.INotionalWord;
import po.INotionalWordValue;
import po.NotionalWordValueType;

public class QuantifierWordIndex extends HtmlControllerBase{

	public static void index() {
		List<WordVO> wordsVO = new ArrayList<>();
		{
			List<INotionalWord> words = NotionalWordsQueryLogic.findQuantifierWords();
			for(INotionalWord word : words) {
				HashArrayListMultiMap<NotionalWordValueType, String> valuesMap = new HashArrayListMultiMap<>();
				for(INotionalWordValue value : word.getValues()) {
					valuesMap.put(value.getType(), value.getValue());
				}
				
				if(valuesMap.count(NotionalWordValueType.Character) > 0) {
					for(String value : valuesMap.values(NotionalWordValueType.Character)) {
						AddWord(wordsVO, word, value, valuesMap.values(NotionalWordValueType.Syllable));
					}
				}
				else if(valuesMap.count(NotionalWordValueType.Mixed)> 0) {
					for(String value : valuesMap.values(NotionalWordValueType.Mixed)) {
						AddWord(wordsVO, word, value, valuesMap.values(NotionalWordValueType.Syllable));
					}
				}
				else {
					for(String value : valuesMap.values(NotionalWordValueType.Syllable)) {
						AddWord(wordsVO, word, value, Collections.emptyList());
					}
				}
			}
		}
		
		renderArgs.put("words", wordsVO);
		render("notionals/quantifier-index");
	}
	
	private static void AddWord(List<WordVO> wordsVO, INotionalWord word, String value, Iterable<String> alias) {
		WordVO vo = new WordVO();
		vo.id = word.getId();
		vo.value = value;
		
		Linq.from(alias).foreach(s->vo.syllables.add(s));
		Linq.from(word.getMeanings()).foreach(m->vo.meanings.add(m));
		
		wordsVO.add(vo);
	}
	
	private static class WordVO{
		public long id;
		public String value;
		public List<String> syllables = new ArrayList<>();
		public List<String> meanings = new ArrayList<>();
	}
	
}
