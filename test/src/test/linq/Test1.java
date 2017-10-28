package test.linq;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import base.utility.comparator.Comparators;
import base.utility.linq.IReferenceEnumerable;
import base.utility.linq.Linq;

public class Test1 {

	@Test
	public void count() {
		assertEquals(10, Linq.from("1234567890").count());
		assertEquals(5, Linq.from("1234567890").skip(5).count());
	}

	@Test
	public void array() {
		HashSet<String> set = new HashSet<>();
		set.add("1");
		set.add("2");
		set.add("3");
		
		String[] array = Linq.from(set).toArray(String.class);
		assertEquals(3, array.length);
		for(String s : array) {
			assertTrue(set.contains(s));
		}
	}
	
	@Test
	public void select() {
		List<Integer> list = new ArrayList<>();
		for(int i=0; i<10000; i++) {
			list.add(i);
		}
		
		List<String> out = Linq.from(list).select(i->i.toString()).toList();
		for(int i=0; i<10000; i++) {
			assertEquals(String.valueOf(i), out.get(i));
		}
	}
	
	@Test
	public void orderBy() {
		List<Integer> list = new ArrayList<>();
		for(int i=10000; i>=0; i--) {
			list.add(i);
		}
		
		List<Integer> out = Linq.from(list).orderBy((t1,t2)->Comparators.compare(t1, t2)).toList();
		for(int i=0; i<=10000; i++) {
			assertEquals((Integer)i, out.get(i));
		}
	}

	@Test
	public void sort() {
		List<Integer> list = new ArrayList<>();
		for(int i=10000; i>=0; i--) {
			list.add(i);
		}
		
		List<Integer> out = Linq.from(list).orderBySelf().toList();
		for(int i=0; i<=10000; i++) {
			assertEquals((Integer)i, out.get(i));
		}
	}

	@Test
	public void toMap() {
		List<Integer> list = new ArrayList<>();
		for(int i=0; i<10000; i++) {
			list.add(i);
		}
		
		Map<Boolean, IReferenceEnumerable<Integer>> map = Linq.from(list).toMap(v->v%2==0);
		assertEquals(2, map.size());
		
		List<Integer> even = map.get(true).toList();
		assertEquals(5000, even.size());
		for(int i=0, j=0; i<10000; i+=2, j++) {
			assertEquals((Integer)i, even.get(j));
		}
		List<Integer> odd = map.get(false).toList();
		assertEquals(5000, odd.size());
		for(int i=1, j=0; i<10000; i+=2, j++) {
			assertEquals((Integer)i, odd.get(j));
		}
	}

}
