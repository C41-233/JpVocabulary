package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import core.model.ModelBase;
import core.model.ModelConstant;

@Entity
@Table(name="national_values")
public class NationalWordValue extends ModelBase{

	@Column(name="national_id")
	private long refId;
	
	@Column(name="value")
	private String value;
	
	@Column(name="type")
	private int type;
	
	@Column(name="`index`")
	private String index = ModelConstant.EmptyToken;
}
