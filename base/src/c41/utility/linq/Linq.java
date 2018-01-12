package c41.utility.linq;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import c41.reflect.StaticClassException;
import c41.utility.assertion.Arguments;

/**
 * 用来操作Linq的各种方法。
 * 一个Linq查询通常以Linq.from开始。
 */
public final class Linq {

	private Linq() {
		throw new StaticClassException();
	}
	
	public static ICharEnumerable from(String string) {
		Arguments.isNotNull(string);
		return new CharArrayEnumerable(string);
	}
	
	public static ICharEnumerable from(char[] array) {
		Arguments.isNotNull(array);
		return new CharArrayEnumerable(array);
	}
	
	public static <T> IReferenceEnumerable<T> from(Iterable<T> iterable){
		Arguments.isNotNull(iterable);
		return new IterableEnumerable<T>(iterable);
	}
	
	public static <T> IReferenceEnumerable<T> from(Iterator<T> iterator){
		Arguments.isNotNull(iterator);
		ArrayList<T> list = new ArrayList<>();
		while(iterator.hasNext()) {
			list.add(iterator.next());
		}
		return from(list);
	}
	
	public static <T> IReferenceEnumerable<T> from(T[] array){
		Arguments.isNotNull(array);
		return new ArrayEnumerable<T>(array);
	}
	
	public static <T> IReferenceEnumerable<T> from(Enumeration<T> enumeration){
		Arguments.isNotNull(enumeration);
		ArrayList<T> list = new ArrayList<>();
		while(enumeration.hasMoreElements()) {
			list.add(enumeration.nextElement());
		}
		return from(list);
	}

	public static IReferenceEnumerable<Node> from(NodeList nodes) {
		Arguments.isNotNull(nodes);
		return new NodeListEnumerable(nodes);
	}

}
