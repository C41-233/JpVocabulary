package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import base.utility.assertion.Assert;
import base.utility.linq.Linq;
import core.model.ModelBase;
import logic.LogicValidate;
import po.IVerbWord;
import po.WordFixword;
import po.VerbWordType;

@Entity
@Table(name="verbs")
public class VerbWord extends ModelBase implements IVerbWord{

	@Column(name="meaning", columnDefinition="TEXT")
	private String meaning;
	
	@Override
	public Iterable<String> getMeanings(){
		return ConcatSplit.splitAsLines(this.meaning);
	}
	
	public void setMeanings(Iterable<String> meanings) {
		Assert.require(meanings);
		this.meaning = ConcatSplit.concatLines(meanings);
	}
	
	@Column(name="types")
	private String types = ModelConstant.EmptyToken;
	
	@Override
	public Iterable<VerbWordType> getTypes(){
		return Linq.from(ConcatSplit.splitAsTokens(this.types)).select(t->VerbWordType.valueOf(t));
	}
	
	public void setTypes(Iterable<VerbWordType> types) {
		Assert.require(types);
		this.types = ConcatSplit.concatTokens(
			Linq.from(types)
				.orderBy(t->t.ordinal())
				.select(t->t.toString())
		);
	}

	@Override
	public boolean hasType(VerbWordType type) {
		String name = type.toString();
		return Linq.from(ConcatSplit.splitAsTokens(this.types)).isExist(name);
	}

	@Override
	public VerbWordType getMainType() {
		return Linq.from(ConcatSplit.splitAsTokens(this.types)).select(s->VerbWordType.valueOf(s)).first();
	}

	@Override
	public Iterable<VerbWordValue> getValues() {
		return VerbWordValue.find("refId=?1 order by value", id).fetch();
	}

	@Override
	public Iterable<String> getSyllables() {
		return Linq.from(getValues())
				.select(v->v.getValue())
				.where(s->LogicValidate.isValidSyllable(s));
	}
	
	@Column(name="fixwords", columnDefinition="TEXT")
	private String fixwords = ModelConstant.EmptyJsonArray;

	@Override
	public Iterable<WordFixword> getFixwords(){
		Fixwords fixwords = new Fixwords(this.fixwords);
		return fixwords.getFixwords();
	}

	public void addFixword(WordFixword fixword) {
		Fixwords fixwords = new Fixwords(this.fixwords);
		fixwords.add(fixword);
		this.fixwords = fixwords.toString();
	}
	
	public void deleteFixword(String value) {
		Fixwords fixwords = new Fixwords(this.fixwords);
		fixwords.delete(value);
		this.fixwords = fixwords.toString();
	}

}
