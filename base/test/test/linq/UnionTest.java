package test.linq;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import c41.utility.linq.Linq;

public class UnionTest {

	@Test
	public void union1() {
		List<Integer> list1 = new ArrayList<>();
		for(int i=0; i<100; i++) {
			list1.add(i);
		}
		List<Integer> list2 = new ArrayList<>();
		for(int i=100; i<200; i++) {
			list2.add(i);
		}
		
		List<Integer> out = Linq.from(list1).union(list2).toList();
		for(int i=0; i<200; i++) {
			assertEquals((Integer)i, out.get(i));
		}
	}

	@Test
	public void union2() {
		List<Integer> list1 = new ArrayList<>();
		List<Integer> list2 = new ArrayList<>();
		for(int i=0; i<200; i++) {
			list2.add(i);
		}
		
		List<Integer> out = Linq.from(list1).union(list2).toList();
		for(int i=0; i<200; i++) {
			assertEquals((Integer)i, out.get(i));
		}
	}

	@Test
	public void union3() {
		List<Integer> list1 = new ArrayList<>();
		for(int i=0; i<200; i++) {
			list1.add(i);
		}
		List<Integer> list2 = new ArrayList<>();
		
		List<Integer> out = Linq.from(list1).union(list2).toList();
		for(int i=0; i<200; i++) {
			assertEquals((Integer)i, out.get(i));
		}
	}
	
	@Test
	public void union4() {
		List<Integer> list1 = new ArrayList<>();
		List<Integer> list2 = new ArrayList<>();
		
		List<Integer> out = Linq.from(list1).union(list2).toList();
		assertEquals(0, out.size());
	}
}
