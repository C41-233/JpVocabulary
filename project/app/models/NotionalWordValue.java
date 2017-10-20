package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import base.utility.assertion.Assert;
import core.model.ConcatSplit;
import core.model.ModelBase;
import core.model.ModelConstant;
import po.INotionalWordValue;

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
	public void setValue(String value) {
		Assert.require(value);
		this.value = value;
	}
	
	@Column(name="type")
	private int type;
	public void setTypes(int type) {
		this.type = type;
	}
	
	@Column(name="`index`")
	private String index = ModelConstant.EmptyToken;
	public void setIndexes(List<String> indexes) {
		Assert.require(indexes);
		this.index = ConcatSplit.concat(indexes);
	}
	
}
