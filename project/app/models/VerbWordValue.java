package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import core.model.ModelBase;

@Entity
@Table(name="verb_values")
public class VerbWordValue extends ModelBase{

	@Column(name="verb_id")
	private long refId;
	
	@Column(name="value")
	private String value;
	
	@Column(name="`index`")
	private String index;
	
}
