package controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import base.utility.linq.Linq;
import controllers.adjectives.AdjectiveWordDetail;
import controllers.characters.CharacterDetail;
import controllers.katakanas.KatakanaWordEdit;
import controllers.notionals.NotionalWordEdit;
import controllers.verbs.VerbWordDetail;
import core.controller.HtmlControllerBase;
import core.controller.Route;
import core.controller.RouteArgs;
import core.controller.validation.annotation.Required;
import core.controller.validation.annotation.StringValue;
import logic.characters.CharactersQueryLogic;
import logic.convert.JpConvert;
import logic.pinyins.Pinyins;
import logic.words.AdjectiveWordsLogic;
import logic.words.KatakanaWordsLogic;
import logic.words.NotionalWordsQueryLogic;
import logic.words.VerbWordsLogic;
import po.IAdjectiveWord;
import po.IAdjectiveWordValue;
import po.ICharacter;
import po.ICharacterSyllable;
import po.IKatakanaWord;
import po.INotionalWord;
import po.INotionalWordValue;
import po.IVerbWord;
import po.IVerbWordValue;
import po.WordFixword;
import po.WordPair;

public final class MainIndex extends HtmlControllerBase {

    public static void index() {
        render("index");
    }

    public static void search(@Required @StringValue String q) {
    	renderArgs.put("query", q);
    	
    	if(q.length() == 0) {
    		index();
    	}
    	
    	//汉字
    	processCharacter(q);
    	
    	List<String> queries = JpConvert.toJpCharacter(q);
    	for(int i=0; i<queries.size(); i++) {
    		String query = queries.get(i);
    		queries.set(i, JpConvert.toHiragana(query));
    	}
    	
    	processNotional(queries);
    	processVerb(queries);
    	processAdj(queries);
    	
    	processKatakana(q);
    	
    	render("query");
    }

    private static void processCharacter(String query) {
		List<ICharacter> characters = CharactersQueryLogic.findCharacterBySearch(query);
		List<CharacterVO> charactersVO = new ArrayList<>();
		for(ICharacter character : characters) {
			CharacterVO vo = new CharacterVO();
			vo.href = Route.get(CharacterDetail.class, "index", new RouteArgs().put("id", character.getId()).put("refer", request.url));
			vo.jp = character.getJpValue();
			Linq.from(character.getCnValue()).foreach(c->vo.cns.add(c));
			Linq.from(character.getPinyins()).select(c->Pinyins.toPinyin(c)).foreach(c->vo.pinyins.add(c));
			for(ICharacterSyllable syllable : character.getSyllables()) {
				CharacterSyllableVO syllableVO = new CharacterSyllableVO();
				syllableVO.value = syllable.getValue();
				for(WordPair word : syllable.getWords()) {
					WordSyllableVO wordVO = new WordSyllableVO();
					wordVO.word = word.getWord();
					wordVO.syllable = word.getSyllable();
					syllableVO.words.add(wordVO);
				}
				if(syllable.isMain()) {
					WordSyllableVO wordVO = new WordSyllableVO();
					wordVO.word = character.getJpValue();
					wordVO.syllable = syllable.getValue();
					syllableVO.words.add(wordVO);
				}
				vo.syllables.add(syllableVO);
			}
			for(WordPair word : character.getFixwords()) {
				WordSyllableVO wordVO = new WordSyllableVO();
				wordVO.word = word.getWord();
				wordVO.syllable = word.getSyllable();
				vo.fixwords.add(wordVO);
			}
			charactersVO.add(vo);
		}
		renderArgs.put("characters", charactersVO);
    }
    
    private static void processNotional(List<String> queries) {
    	List<WordVO> wordsVO = new ArrayList<>();
    	for(String query : queries) {
    		List<INotionalWordValue> values = NotionalWordsQueryLogic.findNotionalWordValuesBySearch(query);
    		for(INotionalWordValue value : values) {
    			INotionalWord word = value.getWord();
    			WordVO wordVO = new WordVO();

    			wordVO.href = Route.get(NotionalWordEdit.class, "index", new RouteArgs().put("id", word.getId()).put("refer", request.url));
    			wordVO.value = value.getValue();
    			Linq.from(word.getValues()).select(v->v.getValue()).where(s->s.equals(wordVO.value)==false).foreach(v->wordVO.alias.add(v));
    			Linq.from(word.getMeanings()).foreach(m->wordVO.meanings.add(m));
    			Linq.from(word.getTypes()).foreach(t->wordVO.types.add(t.toString()));
    			
    			wordsVO.add(wordVO);
    		}
    	}
    	renderArgs.put("notionals", wordsVO);
    }
    
    private static void processVerb(List<String> queries) {
    	List<WordVO> verbsVO = new ArrayList<>();
    	for(String query : queries) {
    		List<IVerbWordValue> values = VerbWordsLogic.findVerbWordValuesBySearch(query);
    		for(IVerbWordValue value : values) {
    			IVerbWord word = value.getWord();
    			WordVO wordVO = new WordVO();
    			wordVO.value = value.getValue();
    			wordVO.href = Route.get(VerbWordDetail.class, "index", new RouteArgs().put("id", word.getId()).put("refer", request.url));
    			Linq.from(word.getValues()).select(w->w.getValue()).where(s->s.equals(wordVO.value)==false).foreach(s->wordVO.alias.add(s));
    			Linq.from(word.getMeanings()).foreach(m->wordVO.meanings.add(m));
    			Linq.from(word.getTypes()).foreach(t->wordVO.types.add(t.toFull()));
    			for(WordFixword fixword : word.getFixwords()) {
    				WordMeaningVO fixwordVO = new WordMeaningVO();
    				fixwordVO.word = fixword.getValue();
    				fixwordVO.meaning = fixword.getMeaning();
    				wordVO.fixwords.add(fixwordVO);
    			}
    			verbsVO.add(wordVO);
    		}
    	}
    	
    	renderArgs.put("verbs", verbsVO);
    }

    private static void processAdj(List<String> queries) {
    	List<WordVO> verbsVO = new ArrayList<>();
    	for(String query : queries) {
    		List<IAdjectiveWordValue> values = AdjectiveWordsLogic.findAdjectiveWordValuesBySearch(query);
    		for(IAdjectiveWordValue value : values) {
    			IAdjectiveWord word = value.getWord();
    			WordVO wordVO = new WordVO();
    			wordVO.value = value.getValue();
    			wordVO.href = Route.get(AdjectiveWordDetail.class, "index", new RouteArgs().put("id", word.getId()).put("refer", request.url));
    			Linq.from(word.getValues()).select(w->w.getValue()).where(s->s.equals(wordVO.value)==false).foreach(s->wordVO.alias.add(s));
    			Linq.from(word.getMeanings()).foreach(m->wordVO.meanings.add(m));
    			Linq.from(word.getTypes()).foreach(t->wordVO.types.add(t.toString()));
    			
    			for(WordFixword fixword : word.getFixwords()) {
    				WordMeaningVO fixwordVO = new WordMeaningVO();
    				fixwordVO.word = fixword.getValue();
    				fixwordVO.meaning = fixword.getMeaning();
    				wordVO.fixwords.add(fixwordVO);
    			}
    			verbsVO.add(wordVO);
    		}
    	}
    	
    	renderArgs.put("adjs", verbsVO);
    }
    
    private static void processKatakana(String query) {
    	List<IKatakanaWord> words = new ArrayList<>();
    	
    	//片假名直接查询
    	{
    		List<String> jpStrings = JpConvert.toJpCharacter(query);
    		for(String jpString : jpStrings) {
        		String katakanaString = JpConvert.toKatakana(jpString);
        		List<IKatakanaWord> rst = KatakanaWordsLogic.findKatakanaWordsBySearch(katakanaString);
        		for(IKatakanaWord word : rst){
        			words.add(word);
        		}
    		}
    	}
    	
    	//别名查询
    	{
    		HashSet<String> aliases = new HashSet<>();
    		aliases.add(query);
    		
    		for(String s : JpConvert.toJpCharacter(query)) {
    			aliases.add(s);
    		}
    		
    		for(String alias : aliases) {
        		IKatakanaWord word = KatakanaWordsLogic.findKatakanaWordsByAlias(alias);
        		if(word != null) {
        			words.add(word);
        		}
    		}
    	}
    	
    	List<KatakanaWordVO> katakanasVO = new ArrayList<>();
    	for(IKatakanaWord word : words) {
			KatakanaWordVO wordVO = new KatakanaWordVO();
			wordVO.value = word.getValue();
			wordVO.href = Route.get(KatakanaWordEdit.class, "index", new RouteArgs().put("id", word.getId()).put("refer", request.url));
			wordVO.context = word.getContext().toString();
			wordVO.alias = word.getAlias();
			
			Linq.from(word.getMeanings()).foreach(m->wordVO.meanings.add(m));
			Linq.from(word.getTypes()).foreach(t->wordVO.types.add(t.toString()));
			
			katakanasVO.add(wordVO);
    	}
    	
    	renderArgs.put("katakanas", katakanasVO);
    }
    
    private static class CharacterVO{
    	public String href;
    	public String jp;
    	public List<Character> cns = new ArrayList<>();
    	public List<String> pinyins = new ArrayList<>();
    	public List<CharacterSyllableVO> syllables = new ArrayList<>();
    	public List<WordSyllableVO> fixwords = new ArrayList<>();
    }
    
    private static class CharacterSyllableVO{
    	public String value;
    	public List<WordSyllableVO> words = new ArrayList<>();
    }
    
    private static class WordSyllableVO{
    	public String word;
    	public String syllable;
    }
    
    private static class WordMeaningVO{
    	public String word;
    	public String meaning;
    }
    
    private static class WordVO{
    	public String href;
    	public String value;
    	public List<String> alias = new ArrayList<>();
    	public List<String> meanings = new ArrayList<>();
    	public List<String> types = new ArrayList<>();
    	public List<WordMeaningVO> fixwords = new ArrayList<>();
    }
    
    private static class KatakanaWordVO{
    	public String href;
    	public String value;
    	public String alias;
    	public String context;
    	public List<String> meanings = new ArrayList<>();
    	public List<String> types = new ArrayList<>();
    }
    
}