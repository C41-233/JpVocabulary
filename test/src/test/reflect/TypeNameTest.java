package test.reflect;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Predicate;

import org.junit.Test;

import base.core.ICloneable;
import base.reflect.ClassType;
import base.reflect.Types;

public class TypeNameTest {

	@Test
	public void test1() {
		ClassType<Integer> type = Types.typeOf(int.class);
		assertEquals("int", type.getName());
		assertEquals("I", type.getVMSignatureName());
		assertEquals("int", type.getSimpleName());
	}

	@Test
	public void test2() {
		ClassType<String> type = Types.typeOf(String.class);
		assertEquals("java.lang.String", type.getName());
		assertEquals("Ljava/lang/String;", type.getVMSignatureName());
		assertEquals("String", type.getSimpleName());
	}

	@Test
	public void test3() {
		ClassType<int[]> type = Types.typeOf(int[].class);
		assertEquals("int[]", type.getName());
		assertEquals("[I", type.getVMSignatureName());
		assertEquals("int[]", type.getSimpleName());
	}

	@Test
	public void test4() {
		ClassType<String[]> type = Types.typeOf(String[].class);
		assertEquals("java.lang.String[]", type.getName());
		assertEquals("[Ljava/lang/String;", type.getVMSignatureName());
		assertEquals("String[]", type.getSimpleName());
	}

	@Test
	public void test5() {
		ClassType<int[][]> type = Types.typeOf(int[][].class);
		assertEquals("int[][]", type.getName());
		assertEquals("[[I", type.getVMSignatureName());
		assertEquals("int[][]", type.getSimpleName());
	}

	@Test
	public void test6() {
		ClassType<String[][]> type = Types.typeOf(String[][].class);
		assertEquals("java.lang.String[][]", type.getName());
		assertEquals("[[Ljava/lang/String;", type.getVMSignatureName());
		assertEquals("String[][]", type.getSimpleName());
	}

	@Test
	public void test7() {
		@SuppressWarnings("rawtypes")
		ClassType<Map.Entry> type = Types.typeOf(Map.Entry.class);
		assertEquals("java.util.Map$Entry", type.getName());
		assertEquals("Ljava/util/Map$Entry;", type.getVMSignatureName());
		assertEquals("Entry", type.getSimpleName());
	}

	private Predicate<?> predicate = new Predicate<Object>(){
		@Override
		public boolean test(Object t) {
			return false;
		}
	};
	
	@Test
	public void test8() {
		@SuppressWarnings("rawtypes")
		ClassType<? extends Predicate> type = Types.typeOf(predicate.getClass());
		assertEquals("test.reflect.TypeNameTest$1", type.getName());
		assertEquals("Ltest/reflect/TypeNameTest$1;", type.getVMSignatureName());
		assertEquals(null, type.getSimpleName());
	}

	@Test
	public void test9() {
		@SuppressWarnings("rawtypes")
		ClassType<ArrayList> type = Types.typeOf(ArrayList.class);
		assertEquals("java.util.ArrayList", type.getName());
		assertEquals("Ljava/util/ArrayList;", type.getVMSignatureName());
		assertEquals("ArrayList", type.getSimpleName());
		assertEquals("java.util.ArrayList<E>", type.getGenericName());
	}

	@Test
	public void test10() {
		@SuppressWarnings("rawtypes")
		ClassType<ICloneable> type = Types.typeOf(ICloneable.class);
		assertEquals("base.core.ICloneable", type.getName());
		assertEquals("Lbase/core/ICloneable;", type.getVMSignatureName());
		assertEquals("ICloneable", type.getSimpleName());
		assertEquals("base.core.ICloneable<T>", type.getGenericName());
		assertEquals("ICloneable<T>", type.getGenericSimpleName());
	}

}
