package models;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import base.utility.comparator.Comparators;
import base.utility.linq.Linq;
import po.WordFixword;

class Fixwords{
	
	private final List<WordFixword> fixwords;
	
	public Fixwords(String json) {
		JsonArray jfixwords = new JsonParser().parse(json).getAsJsonArray();
		this.fixwords = Linq.from(jfixwords)
							.select(j->j.getAsJsonObject())
							.select(j->new WordFixword(j))
							.toList();
		reorder();
	}
	
	public void add(WordFixword fixword) {
		this.fixwords.add(fixword);
		reorder();
	}

	public void delete(String value) {
		int index = Linq.from(fixwords).findFirstIndex(v->v.getValue().equals(value));
		if(index >= 0) {
			fixwords.remove(index);
		}
	}
	
	public Iterable<WordFixword> getFixwords(){
		return this.fixwords;
	}
	
	private void reorder() {
		this.fixwords.sort((v1, v2)->Comparators.compare(v1.getValue(), v2.getValue()));
	}
	
	@Override
	public String toString() {
		JsonArray jarray = new JsonArray();
		for(WordFixword fixword : fixwords) {
			jarray.add(fixword.toJsonObject());
		}
		return jarray.toString();
	}
	
}