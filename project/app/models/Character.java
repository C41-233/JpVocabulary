package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.gson.JsonObject;

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

	@Column(name="body")
	private String body;
	
	@Column(name="pinyin")
	private String pinyin = "|";

	@Override
	public String getJpValue() {
		return this.jp;
	}

	@Override
	public String getCnValue() {
		return this.cn;
	}
	
	public List<String> getPinyins() {
		return ConcatSplit.split(pinyin);
	}
	
}
