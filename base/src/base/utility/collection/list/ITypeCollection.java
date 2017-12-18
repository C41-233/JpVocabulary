package base.utility.collection.list;

public interface ITypeCollection<T> {

	public void add(T obj);
	
	public void addAll(T[] array);
	
	public T[] toArray();
}
