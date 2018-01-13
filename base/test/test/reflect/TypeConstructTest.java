package test.reflect;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import c41.reflect.ConstructorInfo;
import c41.reflect.TypeInfo;
import c41.reflect.Types;

@SuppressWarnings("all")
public class TypeConstructTest {

	private static class TestType{
		public int value;
		
		public TestType() {
			value = 0;
		}
		public TestType(Object obj) throws IOException{
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
		
		@Override
		public int hashCode() {
			return super.hashCode();
		}
	}
	
	@Test
	public void test1() throws IOException {
		TypeInfo<TestType> type = Types.typeOf(TestType.class);
		assertEquals(new TestType(), type.newInstance());
		assertEquals(new TestType(1), type.newInstance(1));
		assertEquals(new TestType(""), type.newInstance(""));
		assertEquals(new TestType(true), type.newInstance(true));
	}

	@Test
	public void test2() {
		TypeInfo<TestType> type = Types.typeOf(TestType.class);
		ConstructorInfo<TestType> constructor = type.getConstructor(Object.class);
		assertEquals(1, constructor.getExceptionCount());
		assertEquals(Types.typeOf(IOException.class), constructor.getExceptionTypes()[0]);
	}

}
