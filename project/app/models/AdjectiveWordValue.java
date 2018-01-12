package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import c41.utility.assertion.Arguments;
import c41.utility.linq.Linq;
import core.model.ModelBase;
import po.IAdjectiveWordValue;

@Entity
@Table(name="adjective_values")
public class AdjectiveWordValue extends ModelBase implements IAdjectiveWordValue{

	@Column(name="adjective_id")
	private long refId;
	
	public void setAdjectiveWordId(long id) {
		this.refId = id;
	}
	
	@Override
	public AdjectiveWord getWord() {
		return AdjectiveWord.findById(this.refId);
	}
	
	@Column(name="value")
	private String value;
	
	public void setValue(String value) {
		Arguments.isNotEmpty(value);
		this.value = value;
	}
	
	@Override
	public String getValue() {
		return this.value;
	}
	
	@Column(name="`index`")
	private String index = ModelConstant.EmptyToken;
	
	public void setIndexes(Iterable<String> indexes) {
		Arguments.isAllNotEmpty(indexes);
		this.index = ConcatSplit.concatTokens(Linq.from(indexes).orderBySelf());
	}
	
	@Override
	public Iterable<String> getIndexes(){
		return ConcatSplit.splitAsTokens(this.index);
	}
	
}
