package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import core.model.ModelBase;
import core.model.ModelConstant;

@Entity
@Table(name="verbs")
public class VerbWord extends ModelBase{

	@Column(name="meaning")
	private String meaning;
	
	@Column(name="types")
	private String types = ModelConstant.EmptyToken;
	
	
	
	
}
