package test.core;

import static org.junit.Assert.*;

import org.junit.Test;

import base.core.Objects;

public class TestObjects {

	@Test
	public void testAs() {
		{
			Object src = "123";
			String dst = Objects.as(String.class, src);
			assertEquals(src, dst);
		}
		
		{
			Object src = 123;
			String dst = Objects.as(String.class, src);
			assertEquals(null, dst);
		}
	}

}
