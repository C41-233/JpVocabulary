package c41.utility.linq;

public interface ICharEnumerator extends IEnumerator<Character>{

	@Override
	public default Character next() {
		moveNext();
		return current();
	}
	
	public default char nextChar() {
		moveNext();
		return currentChar();
	}
	
	@Override
	public default Character current() {
		return currentChar();
	}
	
	public char currentChar();
	
}
