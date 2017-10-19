package test.linq;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import base.utility.linq.Linq;

public class SelectManyTest {

	@Test
	public void test1() {
		List<Integer> list = new ArrayList<>();
		for(int i=0; i<10; i++) {
			list.add(i);
		}
		
		List<Integer> out = Linq.from(list).selectMany(value->{
			List<Integer> result= new ArrayList<>();
			return result;
		}).toList();
		
		assertEquals(0, out.size());
	}

	@Test
	public void test2() {
		List<Integer> list = new ArrayList<>();
		for(int i=0; i<10; i++) {
			list.add(i);
		}
		
		List<Integer> out = Linq.from(list).selectMany(value->{
			List<Integer> result= new ArrayList<>();
			for(int i=0; i<=9; i++) {
				result.add(value*10+i);
			}
			return result;
		}).toList();
		
		for(int i=0; i<100; i++) {
			assertEquals(i, (int)out.get(i));
		}
	}
	
}
