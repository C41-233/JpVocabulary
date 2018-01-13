package test.core;

import static org.junit.Assert.*;

import org.junit.Test;

import c41.core.Core;

public class TestObjects {

	@Test
	public void testAs() {
		{
			Object src = "123";
			String dst = Core.as(String.class, src);
			assertEquals(src, dst);
		}
		
		{
			Object src = 123;
			String dst = Core.as(String.class, src);
			assertEquals(null, dst);
		}
	}

}
