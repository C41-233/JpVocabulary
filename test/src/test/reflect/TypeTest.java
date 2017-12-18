package test.reflect;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import base.reflect.Type;
import base.reflect.Types;

public class TypeTest {

	@Test
	public void test() {
		Type<String> type1 = Types.typeOf(String.class);
		Type<?> type2 = Types.typeOf("java.lang.String");
		assertTrue(type1 == type2);
		assertTrue(type1.equals(type2));
	}

}
