package test.reflect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static test.base.AssertEx.assertThrow;

import java.lang.reflect.Method;

import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.PortableInterceptor.ObjectReferenceFactory;

import c41.reflect.AmbigousMethodException;
import c41.reflect.MethodInfo;
import c41.reflect.Modifiers;
import c41.reflect.TypeInfo;
import c41.reflect.Types;

@SuppressWarnings("unused")
public class TypeMethodTest {

	private static class TestTypeSuper{
		public void func1() {}
		public void func2() {}
		
		public Object func4() {return null;}
		public void func4(String s) {}
		
		public Object func5() {return null; }
		
		public String func6() {return null;}
	}
	
	private static class TestType extends TestTypeSuper{
		@Override
		public void func1() {}
		public void func3() {}
		
		@Override
		public String func4() {return null;}
		@Override
		public String func5() {return null;}
		public String func6(int i) {return null;}
	}
	
	private static final int ObjectMethodsCount;
	
	static {
		int count = 0;
		for(Method method : Object.class.getDeclaredMethods()) {
			if(Modifiers.isPublic(method) || Modifiers.isProtected(method)) {
				count++;
			}
		}
		ObjectMethodsCount = count;
	}
	
	private static TypeInfo<TestTypeSuper> typeSuper;
	private static TypeInfo<TestType> type;
	
	@BeforeClass
	public static void before() {
		typeSuper = Types.typeOf(TestTypeSuper.class);
		type = Types.typeOf(TestType.class);
	}
	
	@Test
	public void test1() {
		assertEquals(6, typeSuper.getDeclaredMethods().length);
		assertEquals(6 + ObjectMethodsCount, typeSuper.getMethods().length);
		
		assertEquals(0, typeSuper.getMethods("func_nop").length);
		assertEquals(1, typeSuper.getMethods("getClass").length);
		assertNull(typeSuper.getDeclaredMethod("getClass"));
		
		assertEquals(1, typeSuper.getMethods("func1").length);
		assertEquals(2, typeSuper.getMethods("func4").length);
	}
	
	@Test
	public void test2() {
		assertEquals(7, type.getDeclaredMethods().length);
		assertEquals(10 + ObjectMethodsCount, type.getMethods().length);
		
		assertEquals(0, type.getMethods("func_nop").length);
		assertNull(type.getDeclaredMethod("getClass"));
		assertEquals(1, type.getMethods("getClass").length);

		assertEquals(1, type.getMethods("func1").length);
		assertEquals(3, type.getMethods("func4").length);
		
		assertEquals(Types.typeOf(String.class), type.getMethod("func5").getReturnType());
		assertThrow(AmbigousMethodException.class, ()->{type.getMethod("func6");});
	}

	@Test
	public void test3() {
		assertTrue(typeSuper.getMethod("getClass") == type.getMethod("getClass"));
		assertFalse(typeSuper.getMethod("func1") == type.getMethod("func2"));
	}
}
