package models;

import java.util.Collections;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import base.utility.linq.Linq;
import core.model.ConcatSplit;
import core.model.ModelBase;
import core.model.ModelConstant;

@Entity
@Table(name="notionals")
public class NotionalWord extends ModelBase{

	@Column(name="meaning")
	private String meaning;
	public Iterable<String> getMeanings(){
		if(meaning.isEmpty()) {
			return Collections.emptyList();
		}
		return Linq.from(meaning.split("\n"));
	}
	
	@Column(name="types")
	private String types = ModelConstant.EmptyToken;
	public Iterable<String> getTypes(){
		return ConcatSplit.split(types);
	}
	
}
