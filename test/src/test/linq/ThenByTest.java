package test.linq;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

import base.utility.linq.Linq;

public class ThenByTest {

	private static class Group{
		
		public int a;
		public int b;
		
		public Group(int a, int b) {
			this.a = a;
			this.b = b;
		}
		
		@Override
		public String toString() {
			return "("+a+","+b+")";
		}
	}
	
	@Test
	public void test() {
		ArrayList<Group> list = new ArrayList<>();
		list.add(new Group(0, 1));
		list.add(new Group(0, 1));
		list.add(new Group(1, 0));
		list.add(new Group(1, 1));
		list.add(new Group(1, 2));
		list.add(new Group(2, 5));
		
		Group[] target = list.toArray(new Group[0]);
		
		Collections.shuffle(list);
		
		Group[] result = Linq.from(list).orderBy((g1,g2)->g1.a-g2.a).thenBy((g1,g2)->g1.b-g2.b).toArray(new Group[0]);
		assertEquals(target.length, result.length);
		for(int i=0; i<target.length; i++) {
			assertEquals(target[i].a, result[i].a);
			assertEquals(target[i].b, result[i].b);
		}
	}

}
