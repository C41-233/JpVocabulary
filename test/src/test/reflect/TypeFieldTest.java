package test.reflect;

import static org.junit.Assert.*;

import org.junit.Test;

import base.reflect.Type;
import base.reflect.Types;

public class TypeFieldTest {

	private static class TestType{
		public String value1;
		private int value2;
	}
	
	@Test
	public void test() {
		TestType object = new TestType();
		Type<TestType> type = Types.typeOf(TestType.class);
		type.setFieldValue(object, "value1", "123");
		assertEquals("123", object.value1); 
		type.setFieldValue(object, "value2", 321);
		assertEquals(321, object.value2); 
	}

}
