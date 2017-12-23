package test.reflect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import base.reflect.ConstructorInfo;
import base.reflect.MemberDomains;
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
		
		
		assertTrue(type.isPrivate());
		assertTrue(type.isStatic());
		assertTrue(type.isClass());
		
		assertEquals(1, type.getDeclaredInterfaces().length);
		assertEquals(1, typeSuper.getDeclaredInterfaces().length);
		assertEquals(3, type.getInterfaces().length);
		assertEquals(6, type.getExportTypes().length);
		assertEquals(4, type.getFields().length);
		
		assertEquals(3, type.getFields(MemberDomains.Public | MemberDomains.NonPublic | MemberDomains.Instance | MemberDomains.Static).length);
		assertEquals(2, type.getFields(MemberDomains.Public | MemberDomains.NonPublic | MemberDomains.Static | MemberDomains.Inherited).length);
		assertEquals(7, type.getFields(MemberDomains.All).length);
	}

	@Test
	public void test3() {
		Type<TestChild> type = Types.typeOf(TestChild.class);
		TestChild obj1 = type.newInstance();
		assertEquals("1", obj1.field3);
		
		ConstructorInfo<TestChild> constructor1 = type.getConstructor();
		TestChild obj2 = constructor1.newInstance();
		assertEquals("1", obj2.field3);
		
		ConstructorInfo<TestChild> constructor2 = type.getConstructor(String.class);
		TestChild obj3 = constructor2.newInstance("b");
		assertEquals("2", obj3.field3);
		assertEquals("b", obj3.field4);
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
		public TestSuper() {}
		private String field1;
		public String field2;
		
	}
	
	@SuppressWarnings("unused")
	private static class TestChild extends TestSuper implements TestInterface1{
		private TestChild() {
			field3 = "1";
		}
		public TestChild(String field) {
			field3 = "2";
			field4 = field;
		}
		public String field3;
		private String field4;
		private String field5;
	}
	
}
