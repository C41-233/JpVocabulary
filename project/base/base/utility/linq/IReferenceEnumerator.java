package base.utility.linq;

public interface IReferenceEnumerator<T> extends IEnumerator<T>{

	@Override
	public boolean hasNext();
	
	@Override
	public default T next() {
		moveNext();
		return current();
	}
	
	@Override
	public void moveNext();
	
	public T current();
	
}
