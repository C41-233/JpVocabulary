package controllers.characters;

import java.util.ArrayList;
import java.util.List;

import base.utility.Strings;
import base.utility.linq.Linq;
import controllers.adjectives.AdjectiveWordDetail;
import controllers.notionals.NotionalWordEdit;
import controllers.verbs.VerbWordDetail;
import core.controller.HtmlControllerBase;
import core.controller.Route;
import core.controller.RouteArgs;
import core.controller.validation.annotation.Id;
import core.controller.validation.annotation.StringValue;
import logic.characters.CharactersQueryLogic;
import logic.pinyins.Pinyins;
import po.IAdjectiveWord;
import po.IAdjectiveWordValue;
import po.ICharacter;
import po.ICharacterSyllable;
import po.INotionalWord;
import po.INotionalWordValue;
import po.IVerbWord;
import po.IVerbWordValue;
import po.WordPair;

public class CharacterDetail extends HtmlControllerBase{

	public static void index(
		@Id long id,
		@StringValue String refer
	) {
		if(refer == null) {
			refer = Route.get(CharacterIndex.class, "index");
		}
		renderArgs.put("refer", refer);
		
		//汉字属性
		ICharacter character = CharactersQueryLogic.getCharacer(id);
		if(character == null) {
			notFound(Strings.format("汉字不存在: id=%d", id));
		}
		
		CharacterVO vo = new CharacterVO();
		vo.id = character.getId();
		vo.jp = character.getJpValue();
		Linq.from(character.getCnValue()).foreach(c->vo.cns.add(c));
		
		for(String pinyin : character.getPinyins()) {
			vo.pinyins.add(Pinyins.toPinyin(pinyin));
		}

		for(ICharacterSyllable syllable : character.getSyllables()) {
			SyllableVO syllableVO = new SyllableVO();
			vo.syllables.add(syllableVO);
			
			syllableVO.value = syllable.getValue();
			
			if(syllable.isMain()) {
				FixwordVO wordVO = new FixwordVO();
				wordVO.word = character.getJpValue();
				wordVO.syllable = syllable.getValue();
				syllableVO.words.add(wordVO);
			}
			
			for(WordPair word : syllable.getWords()) {
				FixwordVO wordVO = new FixwordVO();
				syllableVO.words.add(wordVO);
				
				wordVO.word = word.getWord();
				wordVO.syllable = word.getSyllable();
			}
		}
		
		for(WordPair word : character.getFixwords()) {
			FixwordVO wordVO = new FixwordVO();
			wordVO.syllable = word.getSyllable();
			wordVO.word = word.getWord();
			vo.fixwords.add(wordVO);
		}
		
		renderArgs.put("character", vo);
		
		//汉字相关单词查询
		List<WordVO> wordsVO = new ArrayList<>();
		{
			List<INotionalWordValue> values = CharactersQueryLogic.findNotionalWordsByCharacter(character.getJpValue());
			for(INotionalWordValue value : values) {
				WordVO wordVO = new WordVO();
				INotionalWord word = value.getWord();
				wordVO.value = value.getValue();
				wordVO.href = Route.get(NotionalWordEdit.class, "index", new RouteArgs().put("id", word.getId()).put("refer", request.url));
				Linq.from(word.getSyllables()).foreach(s->wordVO.alias.add(s));
				Linq.from(word.getMeanings()).foreach(m->wordVO.meanings.add(m));
				Linq.from(word.getTypes()).select(t->t.toString()).foreach(t->wordVO.types.add(t));
				wordsVO.add(wordVO);
			}
		}
		{
			List<IVerbWordValue> values = CharactersQueryLogic.findVerbWordsByCharacter(character.getJpValue());
			for(IVerbWordValue value : values) {
				WordVO wordVO = new WordVO();
				IVerbWord word = value.getWord();
				wordVO.value = value.getValue();
				wordVO.href = Route.get(VerbWordDetail.class, "index", new RouteArgs().put("id", word.getId()).put("refer", request.url));
				Linq.from(word.getSyllables()).foreach(s->wordVO.alias.add(s));
				Linq.from(word.getMeanings()).foreach(m->wordVO.meanings.add(m));
				Linq.from(word.getTypes()).select(t->t.toString()).foreach(t->wordVO.types.add(t));
				wordsVO.add(wordVO);
			}
		}
		{
			List<IAdjectiveWordValue> values = CharactersQueryLogic.findAdjWordsByCharacter(character.getJpValue());
			for(IAdjectiveWordValue value : values) {
				WordVO wordVO = new WordVO();
				IAdjectiveWord word = value.getWord();
				wordVO.value = value.getValue();
				wordVO.href = Route.get(AdjectiveWordDetail.class, "index", new RouteArgs().put("id", word.getId()).put("refer", request.url));
				Linq.from(word.getSyllables()).foreach(s->wordVO.alias.add(s));
				Linq.from(word.getMeanings()).foreach(m->wordVO.meanings.add(m));
				wordVO.types.add("形容词");
				Linq.from(word.getTypes()).select(t->t.toString()).foreach(t->wordVO.types.add(t));
				wordsVO.add(wordVO);
			}
		}
		
		wordsVO = Linq.from(wordsVO).orderBy(w->!w.value.startsWith(character.getJpValue())).thenBy(w->w.value).toList();
		renderArgs.put("words", wordsVO);
		
		render("characters/character-detail");
	}
	
	private static class CharacterVO{
		long id;
		String jp;
		List<Character> cns = new ArrayList<>();
		List<String> pinyins = new ArrayList<>();
		List<SyllableVO> syllables = new ArrayList<>();
		List<FixwordVO> fixwords = new ArrayList<>();
	}
	
	private static class SyllableVO{
		String value;
		List<FixwordVO> words = new ArrayList<>();
	}
	
	private static class FixwordVO{
		String word;
		String syllable;
	}
	
	private static class WordVO{
		String href;
		String value;
		List<String> alias = new ArrayList<>();
		List<String> meanings = new ArrayList<>();
		List<String> types = new ArrayList<>();
	}
	
}
