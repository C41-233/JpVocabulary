package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import core.model.ModelBase;
import core.model.ModelConstant;
import po.INotionalWordValue;

@Entity
@Table(name="notional_values")
public class NotionalWordValue extends ModelBase implements INotionalWordValue{

	@Column(name="national_id")
	private long refId;
	
	@Column(name="value")
	private String value;
	
	@Column(name="type")
	private int type;
	
	@Column(name="`index`")
	private String index = ModelConstant.EmptyToken;
}
