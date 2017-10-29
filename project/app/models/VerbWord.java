package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import base.utility.assertion.Assert;
import base.utility.comparator.Comparators;
import base.utility.linq.Linq;
import core.model.ConcatSplit;
import core.model.ModelBase;
import core.model.ModelConstant;
import logic.LogicValidate;
import po.IVerbWord;
import po.VerbFixword;
import po.VerbWordType;

@Entity
@Table(name="verbs")
public class VerbWord extends ModelBase implements IVerbWord{

	@Column(name="meaning")
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
	public Iterable<VerbFixword> getFixwords(){
		Fixwords fixwords = new Fixwords(this.fixwords);
		return fixwords.getFixwords();
	}

	public void addFixword(VerbFixword fixword) {
		Fixwords fixwords = new Fixwords(this.fixwords);
		fixwords.add(fixword);
		this.fixwords = fixwords.toString();
	}
	
	public void deleteFixword(String value) {
		Fixwords fixwords = new Fixwords(this.fixwords);
		fixwords.delete(value);
		this.fixwords = fixwords.toString();
	}
	
	private static class Fixwords{
		
		private final List<VerbFixword> fixwords;
		
		public Fixwords(String json) {
			JsonArray jfixwords = new JsonParser().parse(json).getAsJsonArray();
			this.fixwords = Linq.from(jfixwords)
								.select(j->j.getAsJsonObject())
								.select(j->new VerbFixword(j))
								.toList();
			reorder();
		}
		
		public void add(VerbFixword fixword) {
			this.fixwords.add(fixword);
			reorder();
		}

		public void delete(String value) {
			int index = Linq.from(fixwords).findFirstIndex(v->v.getValue().equals(value));
			if(index >= 0) {
				fixwords.remove(index);
			}
		}
		
		public Iterable<VerbFixword> getFixwords(){
			return this.fixwords;
		}
		
		private void reorder() {
			this.fixwords.sort((v1, v2)->Comparators.compare(v1.getValue(), v2.getValue()));
		}
		
		@Override
		public String toString() {
			JsonArray jarray = new JsonArray();
			for(VerbFixword fixword : fixwords) {
				jarray.add(fixword.toJsonObject());
			}
			return jarray.toString();
		}
		
	}

}
