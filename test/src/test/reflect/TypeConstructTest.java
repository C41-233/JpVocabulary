package test.reflect;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import base.reflect.Type;
import base.reflect.Types;

public class TypeConstructTest {

	private static class TestType{
		public int value;
		
		public TestType() {
			value = 0;
		}
		public TestType(Object obj) {
			value = -100;
		}
		public TestType(CharSequence c) {
			value = -200;
		}
		public TestType(int i) {
			value = i;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof TestType) {
				TestType other = (TestType)obj;
				return other.value == value;
			}
			return false;
		}
		
		@Override
		public String toString() {
			return String.valueOf(value);
		}
	}
	
	@Test
	public void test() {
		Type<TestType> type = Types.typeOf(TestType.class);
		assertEquals(new TestType(), type.newInstance());
		assertEquals(new TestType(1), type.newInstance(1));
		assertEquals(new TestType(""), type.newInstance(""));
		assertEquals(new TestType(true), type.newInstance(true));
	}

}
