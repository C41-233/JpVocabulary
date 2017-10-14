package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import base.utility.assertion.Assert;
import base.utility.linq.Linq;
import core.model.ConcatSplit;
import core.model.ModelBase;
import core.model.ModelConstant;
import po.CharacterWord;
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
	 * 		words:[{
	 * 			syllable:string, 
	 * 			word:string
	 * 		}]
	 * }]
	 */
	@Column(name="syllables", columnDefinition="TEXT")
	private String syllables = ModelConstant.EmptyJsonArray;

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
		return ConcatSplit.split(pinyins);
	}
	public void setPinyins(List<String> pinyins) {
		pinyins.forEach(token->{
			Assert.require(token);
		});
		
		Collections.sort(pinyins);
		this.pinyins = ConcatSplit.concat(pinyins);
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

	public void setSyllableWords(String syllable, List<CharacterWord> words) {
		Assert.require(syllable);
		Assert.require(words);
		
		Syllables syllables = new Syllables(this.syllables);
		syllables.setSyllable(syllable, words);
		
		this.syllables = syllables.toString();
	}
	
	@Override
	public Iterable<CharacterWord> getFixwords() {
		ArrayList<CharacterWord> words = new ArrayList<>();
		return words;
	}

}

