package test.linq;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import c41.utility.linq.Linq;

public class JoinTest {

	@Test
	public void test1() {
		String[] arr = new String[] {"","1",null};
		assertEquals(9, Linq.from(arr).join(arr, (x,y)->x+y).count());
		assertEquals(27, Linq.from(arr).join(arr, (x,y)->x+y).join(arr, (x,y)->x+y).count());
	}

	@Test
	public void test2() {
		String[] arr = new String[] {"","1",null};
		assertEquals(0, Linq.from(arr).join(new String[] {}, (x,y)->x+y).count());
		assertEquals(0, Linq.from(new String[] {}).join(arr, (x,y)->x+y).count());
		assertEquals(0, Linq.from(new String[] {}).join(arr, (x,y)->x+y).join(arr, (x,y)->x+y).count());
		assertEquals(0, Linq.from(new String[] {}).join(arr, (x,y)->x+y).join(new String[] {}, (x,y)->x+y).count());
		assertEquals(0, Linq.from(new String[] {}).join(new String[] {}, (x,y)->x+y).count());
		assertEquals(0, Linq.from(new String[] {}).join(new String[] {}, (x,y)->x+y).join(arr, (x,y)->x+y).count());
	}
}
