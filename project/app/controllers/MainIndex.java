package controllers;

import java.util.ArrayList;
import java.util.List;

import base.core.Objects;
import base.utility.linq.Linq;
import controllers.characters.CharacterDetail;
import core.controller.HtmlControllerBase;
import core.controller.Route;
import core.controller.RouteArgs;
import logic.characters.CharactersConvert;
import logic.characters.CharactersLogic;
import logic.pinyins.Pinyins;
import logic.words.NotionalWordsQueryLogic;
import po.ICharacter;
import po.ICharacterSyllable;
import po.INotionalWord;
import po.INotionalWordValue;
import po.WordPair;

public final class MainIndex extends HtmlControllerBase {

    public static void index() {
        render("index");
    }

    public static void search(String q) {
    	renderArgs.put("query", q);
    	
    	//汉字
    	processCharacter(q);
    	
    	List<String> queries = CharactersConvert.convert(q);
    	
    	processNotional(queries);
    	
    	render("query");
    }

    private static void processCharacter(String query) {
		List<ICharacter> characters = CharactersLogic.findCharacterBySearch(query);
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

    			wordVO.value = value.getValue();
    			Linq.from(word.getValues()).select(v->v.getValue()).where(s->s.equals(wordVO.value)==false).foreach(v->wordVO.alias.add(v));
    			Linq.from(word.getMeanings()).foreach(m->wordVO.meanings.add(m));
    			Linq.from(word.getTypes()).foreach(t->wordVO.types.add(t.toString()));
    			
    			wordsVO.add(wordVO);
    		}
    	}
    	renderArgs.put("notionals", wordsVO);
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
    
    private static class WordVO{
    	public String href;
    	public String value;
    	public List<String> alias = new ArrayList<>();
    	public List<String> meanings = new ArrayList<>();
    	public List<String> types = new ArrayList<>();
    }
}