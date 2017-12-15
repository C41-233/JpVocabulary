package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import base.utility.assertion.Assert;
import base.utility.linq.Linq;
import core.model.ModelBase;
import po.IVerbWordValue;

@Entity
@Table(name="verb_values")
public class VerbWordValue extends ModelBase implements IVerbWordValue{

	@Column(name="verb_id")
	private long refId;
	
	public void setVerbWordId(long id) {
		this.refId = id;
	}
	
	@Override
	public VerbWord getWord() {
		return VerbWord.findById(this.refId);
	}
	
	@Column(name="value")
	private String value;
	
	public void setValue(String value) {
		Assert.require(value);
		this.value = value;
	}
	
	@Override
	public String getValue() {
		return this.value;
	}
	
	@Column(name="`index`")
	private String index = ModelConstant.EmptyToken;
	
	public void setIndexes(Iterable<String> indexes) {
		Assert.require(indexes);
		this.index = ConcatSplit.concatTokens(Linq.from(indexes).orderBySelf());
	}
	
	@Override
	public Iterable<String> getIndexes(){
		return ConcatSplit.splitAsTokens(this.index);
	}
	
}
