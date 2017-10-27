package models;

import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import base.utility.assertion.Assert;
import base.utility.comparator.Comparators;
import base.utility.linq.Linq;
import core.model.ConcatSplit;
import core.model.ModelBase;
import core.model.ModelConstant;
import po.INotionalWord;
import po.NotionalWordType;
import po.NotionalWordValueType;

@Entity
@Table(name="notionals")
public class NotionalWord extends ModelBase implements INotionalWord{

	@Column(name="meaning", columnDefinition="TEXT")
	private String meaning;
	@Override
	public Iterable<String> getMeanings(){
		if(meaning.isEmpty()) {
			return Collections.emptyList();
		}
		return Linq.from(meaning.split("\n"));
	}
	
	public void setMeanings(Iterable<String> meanings) {
		Assert.require(meanings);
		this.meaning = String.join("\n", meanings);
	}
	
	@Column(name="types")
	private String types = ModelConstant.EmptyToken;
	@Override
	public Iterable<NotionalWordType> getTypes(){
		return Linq.from(ConcatSplit.split(types)).select(t->NotionalWordType.valueOf(t));
	}
	
	//带排序
	public void setTypes(Iterable<NotionalWordType> types) {
		Assert.require(types);
		this.types = ConcatSplit.concat(
			Linq.from(types)
				.orderBy(
					(t1,t2)->Comparators.compare(t1.ordinal(), t2.ordinal())
				)
				.select(t->t.toString())
		);
	}
	
	@Override
	public Iterable<String> getSyllables() {
		List<NotionalWordValue> wordValue = NotionalWordValue.find("refId=?1 and type=?2 order by value", id, NotionalWordValueType.Syllable.value()).fetch();
		return Linq.from(wordValue).select(v->v.getValue());
	}

	@Override
	public Iterable<NotionalWordValue> getValues() {
		return NotionalWordValue.find("refId=?1 order by value", id).fetch();
	}

}
