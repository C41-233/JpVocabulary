package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.gson.JsonObject;

import base.utility.assertion.Assert;
import core.model.ConcatSplit;
import core.model.ModelBase;
import groovy.json.JsonBuilder;
import play.db.jpa.Model;
import po.ICharacter;

@Entity
@Table(name="characters")
public class Character extends ModelBase implements ICharacter{

	@Column(name="jp")
	private String jp;

	@Column(name="cn")
	private String cn;

	@Column(name="pinyins")
	private String pinyins = "|";

	@Column(name="syllables", columnDefinition="TEXT")
	private String syllables;

	@Column(name="fixwords", columnDefinition="TEXT")
	private String fixwords;
	
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
	public List<String> getPinyins() {
		return ConcatSplit.split(pinyins);
	}
	public void setPinyins(List<String> pinyins) {
		this.pinyins = ConcatSplit.concat(pinyins);
	}
	
}
