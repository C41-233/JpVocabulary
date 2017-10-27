package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import core.model.ConcatSplit;
import core.model.ModelBase;
import core.model.ModelConstant;

@Entity
@Table(name="verbs")
public class VerbWord extends ModelBase{

	@Column(name="meaning")
	private String meaning;
	
	public Iterable<String> getMeanings(){
		return ConcatSplit.splitAsLines(this.meaning);
	}
	
	public void setMeanings(Iterable<String> meanings) {
		this.meaning = ConcatSplit.concatLines(meanings);
	}
	
	@Column(name="types")
	private String types = ModelConstant.EmptyToken;
	
}
