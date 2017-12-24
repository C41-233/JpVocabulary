package test.reflect;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import base.reflect.Type;
import base.reflect.Types;

public class TypeNameTest {

	@Test
	public void test1() {
		Type<Integer> type = Types.typeOf(int.class);
		assertEquals("int", type.getName());
		assertEquals("I", type.getVMSignatureName());
		assertEquals("int", type.getSimpleName());
	}

	@Test
	public void test2() {
		Type<String> type = Types.typeOf(String.class);
		assertEquals("java.lang.String", type.getName());
		assertEquals("Ljava/lang/String;", type.getVMSignatureName());
		assertEquals("String", type.getSimpleName());
	}

	@Test
	public void test3() {
		Type<int[]> type = Types.typeOf(int[].class);
		assertEquals("int[]", type.getName());
		assertEquals("[I", type.getVMSignatureName());
		assertEquals("int[]", type.getSimpleName());
	}

	@Test
	public void test4() {
		Type<String[]> type = Types.typeOf(String[].class);
		assertEquals("java.lang.String[]", type.getName());
		assertEquals("[Ljava/lang/String;", type.getVMSignatureName());
		assertEquals("String[]", type.getSimpleName());
	}

	@Test
	public void test5() {
		Type<int[][]> type = Types.typeOf(int[][].class);
		assertEquals("int[][]", type.getName());
		assertEquals("[[I", type.getVMSignatureName());
		assertEquals("int[][]", type.getSimpleName());
	}

	@Test
	public void test6() {
		Type<String[][]> type = Types.typeOf(String[][].class);
		assertEquals("java.lang.String[][]", type.getName());
		assertEquals("[[Ljava/lang/String;", type.getVMSignatureName());
		assertEquals("String[][]", type.getSimpleName());
	}

	@Test
	public void test7() {
		@SuppressWarnings("rawtypes")
		Type<Map.Entry> type = Types.typeOf(Map.Entry.class);
		assertEquals("java.util.Map.Entry", type.getName());
		assertEquals("Ljava/util/Map/Entry;", type.getVMSignatureName());
		assertEquals("Entry", type.getSimpleName());
		
		System.err.println(Map.Entry.class.getName());
	}

}
