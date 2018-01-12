package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import c41.utility.assertion.Arguments;
import c41.utility.linq.Linq;
import core.model.ModelBase;
import po.IKatakanaWord;
import po.KatakanaWordContext;
import po.KatakanaWordType;

@Entity
@Table(name="katakanas")
public class KatakanaWord extends ModelBase implements IKatakanaWord{

	@Column(name="value")
	private String value;

	@Override
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value) {
		Arguments.isNotEmpty(value);
		this.value = value;
	}

	@Column(name="meaning", columnDefinition="TEXT")
	private String meaning;

	@Override
	public Iterable<String> getMeanings(){
		return ConcatSplit.splitAsLines(this.meaning);
	}
	
	public void setMeanings(Iterable<String> meanings) {
		Arguments.isAllNotEmpty(meanings);
		this.meaning = ConcatSplit.concatLines(meanings);
	}
	
	@Column(name="alias")
	private String alias;
	
	@Override
	public String getAlias() {
		return this.alias;
	}
	
	public void setAlias(String alias) {
		Arguments.isNotNull(alias);
		this.alias = alias;
	}
	
	@Column(name="types")
	private String types = ModelConstant.EmptyToken;

	@Override
	public Iterable<KatakanaWordType> getTypes(){
		return Linq.from(ConcatSplit.splitAsTokens(types)).select(t->KatakanaWordType.valueOf(t));
	}
	
	//带排序
	public void setTypes(Iterable<KatakanaWordType> types) {
		Arguments.isNotNull(types);
		this.types = ConcatSplit.concatTokens(
			Linq.from(types)
				.orderBy(t->t.ordinal())
				.select(t->t.toString())
		);
	}
	
	@Column(name="context")
	private String context;
	
	@Override
	public KatakanaWordContext getContext() {
		return KatakanaWordContext.valueOf(this.context);
	}
	
	public void setContext(KatakanaWordContext context) {
		this.context = context.toString();
	}
	
}
