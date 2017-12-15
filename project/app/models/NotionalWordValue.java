package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import base.utility.assertion.Assert;
import base.utility.linq.Linq;
import core.model.ModelBase;
import po.INotionalWordValue;
import po.NotionalWordValueType;

@Entity
@Table(name="notional_values")
public class NotionalWordValue extends ModelBase implements INotionalWordValue{

	@Column(name="national_id")
	private long refId;
	public void setNotionalWordId(long id){
		this.refId = id;
	}
	
	@Column(name="value")
	private String value;
	
	@Override
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value) {
		Assert.require(value);
		this.value = value;
	}
	
	@Column(name="type")
	private int type;
	public void setType(NotionalWordValueType type) {
		Assert.notNull(type);
		this.type = type.value();
	}

	@Override
	public NotionalWordValueType getType() {
		return NotionalWordValueType.valueOf(this.type);
	}
	
	@Column(name="`index`")
	private String index = ModelConstant.EmptyToken;
	public void setIndexes(Iterable<String> indexes) {
		Assert.require(indexes);
		this.index = ConcatSplit.concatTokens(
			Linq.from(indexes).orderBySelf()
		);
	}
	@Override
	public Iterable<String> getIndexes(){
		return ConcatSplit.splitAsTokens(this.index);
	}

	@Override
	public NotionalWord getWord() {
		return NotionalWord.findById(refId);
	}

	@Override
	public Iterable<String> getSyllables() {
		return getWord().getSyllables();
	}

}
