package c41.utility.linq;

public interface IIntEnumerator extends IEnumerator<Integer>{

	@Override
	public default Integer next() {
		moveNext();
		return current();
	}
	
	public default int nextInt() {
		moveNext();
		return currentInt();
	}
	
	@Override
	public default Integer current() {
		return currentInt();
	}
	
	public int currentInt();
	
}
