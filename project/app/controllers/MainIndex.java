package controllers;

import java.util.ArrayList;
import java.util.List;

import org.junit.validator.PublicClassValidator;

import base.utility.linq.Linq;
import core.controller.HtmlControllerBase;
import logic.characters.CharactersLogic;
import logic.pinyins.Pinyins;
import po.ICharacter;
import po.ICharacterSyllable;
import po.WordPair;

public final class MainIndex extends HtmlControllerBase {

    public static void index() {
        render("index");
    }

    public static void search(String q) {
    	renderArgs.put("query", q);
    	
    	//汉字
    	{
    		List<ICharacter> characters = CharactersLogic.findCharacterBySearch(q);
    		List<CharacterVO> charactersVO = new ArrayList<>();
    		for(ICharacter character : characters) {
    			CharacterVO vo = new CharacterVO();
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
    	
    	render("query");
    }

    private static class CharacterVO{
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
    
    public static class WordSyllableVO{
    	public String word;
    	public String syllable;
    }
    
}