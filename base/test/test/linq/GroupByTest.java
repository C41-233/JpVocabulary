package test.linq;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import c41.utility.linq.IReferenceGroup;
import c41.utility.linq.Linq;

public class GroupByTest {

	private static class Value{
		
		public Integer key;
		public Integer value;
		
		public Value(Integer key, Integer value) {
			this.key = key;
			this.value = value;
		}
		
	}
	
	@Test
	public void test() {
		List<Value> list = new ArrayList<>();
		list.add(new Value(1, 11));
		list.add(new Value(1, 12));
		list.add(new Value(1, 13));
		list.add(new Value(2, 21));
		list.add(new Value(2, 22));
		list.add(new Value(2, 23));
		list.add(new Value(3, 31));
		
		List<IReferenceGroup<Integer, Value>> out = Linq.from(list).groupBy(v->v.key).toList();
		assertEquals(3, out.size());
		assertEquals(11, out.get(0).select(v->v.value).toInt().at(0));
		assertEquals(12, out.get(0).select(v->v.value).toInt().at(1));
		assertEquals(13, out.get(0).select(v->v.value).toInt().at(2));
		assertEquals(21, out.get(1).select(v->v.value).toInt().at(0));
		assertEquals(22, out.get(1).select(v->v.value).toInt().at(1));
		assertEquals(23, out.get(1).select(v->v.value).toInt().at(2));
		assertEquals(31, out.get(2).select(v->v.value).toInt().at(0));
	}

}
