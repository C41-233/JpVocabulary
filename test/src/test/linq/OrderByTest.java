package test.linq;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import base.utility.comparator.Comparators;
import base.utility.linq.Linq;

public class OrderByTest {

	private List<Integer> list;
	
	@Before
	public void before() {
		list = new ArrayList<>();
		for(int i=9999; i>=0; i--) {
			list.add(i);
		}
	}

	@Test
	public void test1() {
		List<Integer> out = Linq.from(list).orderBy((t1,t2)->Comparators.compare(t1, t2)).toList();
		for(int i=0; i<10000; i++) {
			assertEquals((Integer)i, out.get(i));
		}
	}

	@Test
	public void test2() {
		List<Integer> out = Linq.from(list)
			.orderByCondition(t->t%2==0)
			.thenBySelf()
			.toList();
		for(int i=0, j=0; i<5000; i++, j+=2) {
			assertEquals((Integer)j, out.get(i));
		}
		for(int i=5000, j=1; i<10000; i++, j+=2) {
			assertEquals((Integer)j, out.get(i));
		}
	}

	@Test
	public void test3() {
		List<Integer> out = Linq.from(list).orderBySelf().toList();
		for(int i=0; i<10000; i++) {
			assertEquals((Integer)i, out.get(i));
		}
	}

	@Test
	public void test4() {
		List<Integer> out = 
			Linq.from(list)
			.select(t->String.format("%05d", t))
			.orderBy(t->t.charAt(0))
			.thenBy(t->t.charAt(1))
			.thenBy(t->t.charAt(2))
			.thenBy(t->t.charAt(3))
			.thenBy(t->t.charAt(4))
			.select(t->Integer.valueOf(t))
			.toList();
		for(int i=0; i<10000; i++) {
			assertEquals((Integer)i, out.get(i));
		}
	}
	
}
