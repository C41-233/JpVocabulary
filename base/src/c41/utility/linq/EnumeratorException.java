package c41.utility.linq;

import java.util.NoSuchElementException;

final class EnumeratorException{

	static NoSuchElementException throwBefore() {
		throw new NoSuchElementException("current before enumerator range");
	}

	static NoSuchElementException throwAfter() {
		throw new NoSuchElementException("move next out of enumerator range");
	}
	
}
