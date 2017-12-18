package test.reflect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import base.reflect.MemberDomain;
import base.reflect.Type;
import base.reflect.Types;

public class TypeTest {

	@Test
	public void test1() {
		Type<String> type1 = Types.typeOf(String.class);
		Type<?> type2 = Types.typeOf("java.lang.String");
		assertTrue(type1 == type2);
		assertTrue(type1.equals(type2));
	}

	@Test
	public void test2() {
		Type<TestChild> type = Types.typeOf(TestChild.class);
		Type<TestSuper> typeSuper = Types.typeOf(TestSuper.class);

		assertEquals("test.reflect.TypeTest$TestChild", type.toString());
		
		assertTrue(typeSuper == type.getSuperType());
		
		assertEquals(1, type.getDeclaredInterfaces().length);
		assertEquals(1, typeSuper.getDeclaredInterfaces().length);
		assertEquals(3, type.getInterfaces().length);
		
		assertTrue(type.isPrivate());
		assertTrue(type.isStatic());
		assertTrue(type.isClass());
		
		assertEquals(4, type.getFields().length);
		assertEquals(3, type.getFields(MemberDomain.Declared).length);
		assertEquals(6, type.getFields(MemberDomain.PublicOrDeclared).length);
		assertEquals(7, type.getFields(MemberDomain.AllInherited).length);
		
		
	}
	
	public static interface TestInterface1 extends TestInterface3{
		public static final int value1 = 5;
	}
	
	public static interface TestInterface2 extends TestInterface3{
		public static final int value2 = 6;
	}
	
	public static interface TestInterface3{
		
	}
	
	@SuppressWarnings("unused")
	public static class TestSuper implements TestInterface2{

		private String field1;
		public String field2;
		
	}
	
	@SuppressWarnings("unused")
	private static class TestChild extends TestSuper implements TestInterface1{
		public String field3;
		private String field4;
		private String field5;
	}
	
}
