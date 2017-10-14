package base.utility.linq;

public interface ICharEnumerator extends IEnumerator<Character>{

	@Override
	public boolean hasNext();
	
	@Override
	public default Character next() {
		return nextChar();
	}
	
	public default char nextChar() {
		moveNext();
		return current();
	}
	
	@Override
	public void moveNext();
	
	public char current();
	
}
