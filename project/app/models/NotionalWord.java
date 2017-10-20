package models;

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
import po.INotionalWord;

@Entity
@Table(name="notionals")
public class NotionalWord extends ModelBase implements INotionalWord{

	@Column(name="meaning")
	private String meaning;
	public Iterable<String> getMeanings(){
		if(meaning.isEmpty()) {
			return Collections.emptyList();
		}
		return Linq.from(meaning.split("\n"));
	}
	
	public void setMeanings(List<String> meanings) {
		Assert.require(meanings);
		this.meaning = String.join("\n", meanings);
	}
	
	@Column(name="types")
	private String types = ModelConstant.EmptyToken;
	public Iterable<String> getTypes(){
		return ConcatSplit.split(types);
	}
	
	public void setTypes(List<String> types) {
		Assert.require(types);
		this.types = ConcatSplit.concat(types);
	}
	
}
