package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import base.utility.assertion.Assert;
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
		ArrayList<ICharacterSyllable> syllables = new ArrayList<>();
		return syllables;
	}
	
	@Override
	public Iterable<CharacterWord> getFixwords() {
		ArrayList<CharacterWord> words = new ArrayList<>();
		return words;
	}
	
}
