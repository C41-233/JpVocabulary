package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import base.utility.assertion.Assert;
import base.utility.comparator.Comparators;
import base.utility.linq.Linq;
import core.model.ConcatSplit;
import core.model.ModelBase;
import core.model.ModelConstant;
import po.WordPair;
import po.ICharacter;
import po.ICharacterSyllable;

@Entity
@Table(name="characters")
public class Character extends ModelBase implements ICharacter{

	@Column(name="jp")
	private String jp;

	@Column(name="cn")
	private String cn;

	@Column(name="pinyins")
	private String pinyins = ModelConstant.EmptyToken;

	/**
	 * [{
	 * 		value:string, 
	 * 		isMain:boolean
	 * 		words:[{
	 * 			syllable:string, 
	 * 			word:string
	 * 		}]
	 * }]
	 */
	@Column(name="syllables", columnDefinition="TEXT")
	private String syllables = ModelConstant.EmptyJsonArray;

	/**
	 * [{
	 * 		syllable:string,
	 * 		word:string
	 * }]
	 */
	@Column(name="fixwords", columnDefinition="TEXT")
	private String fixwords = ModelConstant.EmptyJsonArray;
	
	@Override
	public String getJpValue() {
		return this.jp;
	}
	public void setJpValue(String jp) {
		Assert.require(jp);
		this.jp = jp;
	}

	@Override
	public String getCnValue() {
		return this.cn;
	}
	public void setCnValue(String cn) {
		Assert.require(cn);
		this.cn = cn;
	}
	
	@Override
	public Iterable<String> getPinyins() {
		return ConcatSplit.splitAsTokens(pinyins);
	}
	public void setPinyins(List<String> pinyins) {
		Assert.require(pinyins);
		
		Collections.sort(pinyins);
		this.pinyins = ConcatSplit.concatTokens(pinyins);
	}
	
	@Override
	public Iterable<ICharacterSyllable> getSyllables() {
		Syllables syllables = new Syllables(this.syllables);
		return Linq.from(syllables.getSyllables()).cast();
	}

	public void addSyllable(String syllable) {
		Assert.require(syllable);
		
		Syllables syllables = new Syllables(this.syllables);
		syllables.addSyllable(syllable);
		
		this.syllables = syllables.toString();
	}
	
	public void deleteSyllable(String syllable) {
		Assert.require(syllable);

		Syllables syllables = new Syllables(this.syllables);
		syllables.removeSyllable(syllable);
		
		this.syllables = syllables.toString();
	}

	public void updateSyllable(String from, String to) {
		Assert.require(from);
		Assert.require(to);
	
		Syllables syllables = new Syllables(this.syllables);
		syllables.updateSyllable(from, to);
		
		this.syllables = syllables.toString();
	}

	public void updateSyllableMain(String syllable, boolean value) {
		Assert.require(syllable);

		Syllables syllables = new Syllables(this.syllables);
		syllables.updateMain(syllable, value);
		
		this.syllables = syllables.toString();
	}

	public void setSyllableWords(String syllable, List<WordPair> words) {
		Assert.require(syllable);
		Assert.require(words);
		
		Syllables syllables = new Syllables(this.syllables);
		syllables.setSyllable(syllable, words);
		
		this.syllables = syllables.toString();
	}
	
	@Override
	public Iterable<WordPair> getFixwords() {
		Fixwords fixwords = new Fixwords(this.fixwords);
		return fixwords.getFixwords();
	}
	
	public void addFixword(WordPair characterWord) {
		Fixwords fixwords = new Fixwords(this.fixwords);
		fixwords.add(characterWord);
		this.fixwords = fixwords.toString();
	}
	
	public void deleteFixword(String word, String syllable) {
		Fixwords fixwords = new Fixwords(this.fixwords);
		fixwords.delete(word, syllable);
		this.fixwords = fixwords.toString();
	}

	////////////////////////////////////////////////////////////////////////////////////
	
	private static class CharacterSyllable implements ICharacterSyllable{
	
		private String value;
		private final List<WordPair> words;
		private boolean isMain;
		
		public CharacterSyllable(String syllable) {
			this.value = syllable;
			this.words = new ArrayList<>();
			this.isMain = false;
		}
		
		public CharacterSyllable(JsonObject json) {
			this.value = json.get("value").getAsString();
			this.words = Linq.from(json.get("words").getAsJsonArray()).select(j->new WordPair(j.getAsJsonObject())).toList();
			this.isMain = json.get("isMain").getAsBoolean();
		}
	
		public void setValue(String value) {
			this.value = value;
		}
		
		@Override
		public String getValue() {
			return this.value;
		}
	
		@Override
		public Iterable<WordPair> getWords() {
			return this.words;
		}
	
		public void addWord(WordPair word) {
			this.words.add(word);
		}
	
		public void setMain(boolean b) {
			this.isMain = b;
		}
		
		@Override
		public boolean isMain() {
			return this.isMain;
		}
		
		public JsonObject toJsonObject() {
			JsonObject jobj = new JsonObject();
			jobj.addProperty("value", this.value);
			
			JsonArray jwords = new JsonArray();
			jobj.add("words", jwords);
			
			words.forEach(word->jwords.add(word.toJsonObject()));
			
			jobj.addProperty("isMain", this.isMain);
			
			return jobj;
		}
	
	}

	////////////////////////////////////////////////////////////////////////////////////
	
	private static class Syllables{
		
		private final List<CharacterSyllable> syllables;
		
		public Syllables(String string) {
			JsonArray jsyllables = new JsonParser().parse(string).getAsJsonArray();
			this.syllables = Linq.from(jsyllables).select(jsyllable->new CharacterSyllable(jsyllable.getAsJsonObject())).toList();
			onChange();
		}
		
		@Override
		public String toString() {
			JsonArray jarray = new JsonArray();
			for(CharacterSyllable syllable : syllables) {
				jarray.add(syllable.toJsonObject());
			}
			return jarray.toString();
		}
		
		public Iterable<CharacterSyllable> getSyllables(){
			return Linq.from(syllables);
		}
	
		public void addSyllable(String syllable) {
			this.syllables.add(new CharacterSyllable(syllable));
			onChange();
		}
	
		public void removeSyllable(String syllable) {
			int index = Linq.from(syllables).findFirstIndex(s->s.getValue().equals(syllable));
			if(index >= 0) {
				syllables.remove(index);
			}
			onChange();
		}
	
		private void onChange() {
			this.syllables.sort((c1,c2)->Comparators.compare(c1.getValue(), c2.getValue()));
		}
		
		public void setSyllable(String syllable, List<WordPair> words) {
			removeSyllable(syllable);
			CharacterSyllable obj = new CharacterSyllable(syllable);
			for(WordPair word : words) {
				obj.addWord(word);
			}
			this.syllables.add(obj);
			onChange();
		}
	
		public void updateSyllable(String from, String to) {
			CharacterSyllable syllable = findSyllable(from);
			if(syllable != null) {
				syllable.setValue(to);
			}
			onChange();
		}
	
		public void updateMain(String value, boolean isMain) {
			CharacterSyllable syllable = findSyllable(value);
			if(syllable != null) {
				syllable.setMain(isMain);
			}
		}
		
		public CharacterSyllable findSyllable(String syllable) {
			return Linq.from(syllables).findFirst(s->s.getValue().equals(syllable));
		}
		
	}

	////////////////////////////////////////////////////////////////////////////////////
	
	private static class Fixwords {
	
		private final List<WordPair> words;
		
		public Fixwords(String json) {
			JsonArray array = new JsonParser().parse(json).getAsJsonArray();
			this.words = Linq.from(array).select(j->j.getAsJsonObject()).select(word->new WordPair(word)).toList();
			onChange();
		}
		
		@Override
		public String toString() {
			JsonArray array = new JsonArray();
			for(WordPair word : words) {
				array.add(word.toJsonObject());
			}
			return array.toString();
		}
	
		public Iterable<WordPair> getFixwords() {
			return words;
		}
	
		private void onChange() {
			this.words.sort(
				Comparators.<WordPair>chain(
					(w1, w2)->Comparators.compare(w1.getWord(), w2.getWord())
				).chain(
					(w1, w2)->Comparators.compare(w1.getSyllable(), w2.getSyllable())
				)
			);
		}
		
		public void add(WordPair word) {
			this.words.add(word);
			onChange();
		}
	
		public void delete(String word, String syllable) {
			int index = Linq.from(words).findFirstIndex(w->w.getWord().equals(word)&&w.getSyllable().equals(syllable));
			if(index >= 0) {
				this.words.remove(index);
			}
			onChange();
		}
		
	}

}