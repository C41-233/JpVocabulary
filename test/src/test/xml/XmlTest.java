package test.xml;

import static org.junit.Assert.*;

import org.junit.Test;

import c41.xml.XmlReader;

public class XmlTest {

	@Test
	public void test1() {
		String xml = "<doc name='cat' value='41'></doc>";
		XmlReader reader = new XmlReader();
		Doc1 doc = reader.read(xml, Doc1.class);
		assertEquals("cat", doc.name);
		assertEquals(41, doc.value);
	}

	@Test
	public void test2() {
		String xml = "<doc name='cat'><value>41</value></doc>";
		XmlReader reader = new XmlReader();
		Doc1 doc = reader.read(xml, Doc1.class);
		assertEquals("cat", doc.name);
		assertEquals(41, doc.value);
	}

	private static class Doc1{
		public String name;
		public int value;
	}

	@Test
	public void test3() {
		String xml = "<doc><type><key>a</key><value>5</value></type><type><key>b</key><value>15</value></type><book name='haha' ref='lala'>raw</book></doc>";
		XmlReader reader = new XmlReader();
		reader.setDefaultTextField("text");
		
		Doc2 doc = reader.read(xml, Doc2.class);
		assertEquals(2, doc.types.length);
		assertEquals("a", doc.types[0].key);
		assertEquals(5, doc.types[0].value);
		assertEquals("b", doc.types[1].key);
		assertEquals(15, doc.types[1].value);
		assertEquals("haha", doc.book.name);
		assertEquals("lala", doc.book.ref);
		assertEquals("raw", doc.book.text);
	}
	
	private static class Doc2{
		
		public Node[] types;
		public Book book;
		
		public static class Node{
			public String key;
			public long value;
		}
		
		public static class Book{
			public String name;
			public String ref;
			public String text;
		}
		
	}

	@Test
	public void test4() {
		String xml = "<root><book><name>A</name></book><book><name>B</name></book></root>";
		XmlReader reader = new XmlReader();
		Doc3[] books = reader.readArray(xml, Doc3.class, "book");
		assertEquals(2, books.length);
		assertEquals("A", books[0].name);
		assertEquals("B", books[1].name);
	}

	private static class Doc3{
		public String name;
	}
	
}
