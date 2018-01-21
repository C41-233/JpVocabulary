package c41.utility.linq;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import c41.reflect.StaticClassException;

/**
 * 用来操作Linq的各种方法。
 * 一个Linq查询通常以Linq.from开始。
 */
public final class Linq {

	private Linq() {
		throw new StaticClassException();
	}
	
	public static ICharEnumerable from(String string) {
		return new CharArrayEnumerable(string);
	}
	
	public static ICharEnumerable from(char[] array) {
		return new CharArrayEnumerable(array);
	}

	public static <T> IReferenceEnumerable<T> from(T[] array){
		return new ArrayEnumerable<>(array);
	}
	
	public static <T> IReferenceEnumerable<T> from(Iterable<T> iterable){
		if(iterable instanceof List) {
			return new ListEnumerable<>((List<T>)iterable);
		}
		
		return new IterableEnumerable<>(iterable);
	}
	
	public static <T> IReferenceEnumerable<T> from(Iterator<T> iterator){
		return new IteratorEnumerable<>(iterator);
	}
	
	public static <T> IReferenceEnumerable<T> from(Enumeration<T> enumeration){
		return new EnumerationEnumerable<>(enumeration);
	}

	public static IReferenceEnumerable<Node> from(NodeList nodes) {
		return new NodeListEnumerable(nodes);
	}
	
	@SafeVarargs
	public static <T> IReferenceEnumerable<T> fromElements(T...elements) {
		return new ArrayEnumerable<>(elements);
	}

}
