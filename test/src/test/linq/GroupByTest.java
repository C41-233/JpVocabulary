package test.linq;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import base.utility.linq.IReferenceGroup;
import base.utility.linq.Linq;

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
		List<Value> list = new ArrayList<Value>();
		list.add(new Value(1, 11));
		list.add(new Value(1, 12));
		list.add(new Value(1, 13));
		list.add(new Value(2, 21));
		list.add(new Value(2, 22));
		list.add(new Value(2, 23));
		list.add(new Value(3, 31));
		
		List<IReferenceGroup<Integer, Value>> out = Linq.from(list).groupBy(v->v.key).toList();
		assertEquals(3, out.size());
		assertEquals(11, (int) out.get(0).at(0).value);
		assertEquals(12, (int) out.get(0).at(1).value);
		assertEquals(13, (int) out.get(0).at(2).value);
		assertEquals(21, (int) out.get(1).at(0).value);
		assertEquals(22, (int) out.get(1).at(1).value);
		assertEquals(23, (int) out.get(1).at(2).value);
		assertEquals(31, (int) out.get(2).at(0).value);
	}

}
