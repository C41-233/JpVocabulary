package controllers.notionals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import c41.utility.collection.map.HashArrayListMultiMap;
import c41.utility.linq.Linq;
import controllers.katakanas.KatakanaWordEdit;
import core.controller.HtmlControllerBase;
import core.controller.Route;
import core.controller.RouteArgs;
import logic.words.KatakanaWordsLogic;
import logic.words.NotionalWordsQueryLogic;
import po.IKatakanaWord;
import po.INotionalWord;
import po.INotionalWordValue;
import po.NotionalWordValueType;

public class QuantifierWordIndex extends HtmlControllerBase{

	public static void index() {
		List<NotionalWordVO> notionalsVO = new ArrayList<>();
		{
			List<INotionalWord> words = NotionalWordsQueryLogic.findQuantifierWords();
			for(INotionalWord word : words) {
				HashArrayListMultiMap<NotionalWordValueType, String> valuesMap = new HashArrayListMultiMap<>();
				for(INotionalWordValue value : word.getValues()) {
					valuesMap.put(value.getType(), value.getValue());
				}
				
				if(valuesMap.count(NotionalWordValueType.Character) > 0) {
					for(String value : valuesMap.getAll(NotionalWordValueType.Character)) {
						AddWord(notionalsVO, word, value, valuesMap.getAll(NotionalWordValueType.Syllable));
					}
				}
				else if(valuesMap.count(NotionalWordValueType.Mixed)> 0) {
					for(String value : valuesMap.getAll(NotionalWordValueType.Mixed)) {
						AddWord(notionalsVO, word, value, valuesMap.getAll(NotionalWordValueType.Syllable));
					}
				}
				else {
					for(String value : valuesMap.getAll(NotionalWordValueType.Syllable)) {
						AddWord(notionalsVO, word, value, Collections.emptyList());
					}
				}
			}
		}
		renderArgs.put("notionals", notionalsVO);
		
		List<KatakanaWordVO> katakanasVO = new ArrayList<>();
		{
			List<IKatakanaWord> words = KatakanaWordsLogic.findQuantifierKatakanaWords();
			for(IKatakanaWord word : words) {
				KatakanaWordVO vo = new KatakanaWordVO();
				vo.href = Route.get(KatakanaWordEdit.class, "index", new RouteArgs().put("id", word.getId()).put("refer", request.url));
				vo.value = word.getValue();
				vo.alias = word.getAlias();
				vo.context = word.getContext().toString();
				
				Linq.from(word.getMeanings()).foreach(m->vo.meanings.add(m));

				katakanasVO.add(vo);
			}
		}
		renderArgs.put("katakanas", katakanasVO);
		
		render("notionals/quantifier-index");
	}
	
	private static void AddWord(List<NotionalWordVO> wordsVO, INotionalWord word, String value, Iterable<String> alias) {
		NotionalWordVO vo = new NotionalWordVO();
		vo.href = Route.get(NotionalWordEdit.class, "index", new RouteArgs().put("id", word.getId()).put("refer", request.url));
		vo.value = value;
		
		Linq.from(alias).foreach(s->vo.syllables.add(s));
		Linq.from(word.getMeanings()).foreach(m->vo.meanings.add(m));
		
		wordsVO.add(vo);
	}
	
	private static class NotionalWordVO{
		public String href;
		public String value;
		public List<String> syllables = new ArrayList<>();
		public List<String> meanings = new ArrayList<>();
	}

	private static class KatakanaWordVO{
		public String href;
		public String value;
		public String context;
		public String alias;
		public List<String> meanings = new ArrayList<>();
	}
	
}
