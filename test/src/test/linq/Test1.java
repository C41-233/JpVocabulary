package test.linq;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;

import base.utility.linq.Linq;

public class Test1 {

	@Test
	public void test1() {
		assertEquals(10, Linq.from("1234567890").count());
		assertEquals(5, Linq.from("1234567890").skip(5).count());
	}

	@Test
	public void test2() {
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
	
}
