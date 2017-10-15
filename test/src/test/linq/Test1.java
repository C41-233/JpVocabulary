package test.linq;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import base.utility.function.Comparators;
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
		
		List<Integer> out = Linq.from(list).sort().toList();
		for(int i=0; i<=10000; i++) {
			assertEquals((Integer)i, out.get(i));
		}
	}
	
}