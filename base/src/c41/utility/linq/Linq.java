package c41.utility.linq;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class Linq {

	private Linq() {}
	
	public static ICharEnumerable from(String string) {
		return new CharArrayEnumerable(string);
	}
	
	public static ICharEnumerable from(char[] array) {
		return new CharArrayEnumerable(array);
	}
	
	public static <T> IReferenceEnumerable<T> from(Iterable<T> iterable){
		return new IterableEnumerable<T>(iterable);
	}
	
	public static <T> IReferenceEnumerable<T> from(Iterator<T> iterator){
		ArrayList<T> list = new ArrayList<>();
		while(iterator.hasNext()) {
			list.add(iterator.next());
		}
		return from(list);
	}
	
	public static <T> IReferenceEnumerable<T> from(T[] array){
		return new ArrayEnumerable<T>(array);
	}
	
	public static <T> IReferenceEnumerable<T> from(Enumeration<T> enumeration){
		ArrayList<T> list = new ArrayList<>();
		while(enumeration.hasMoreElements()) {
			list.add(enumeration.nextElement());
		}
		return from(list);
	}

	public static IReferenceEnumerable<Node> from(NodeList nodes) {
		return new NodeListEnumerable(nodes);
	}

}
