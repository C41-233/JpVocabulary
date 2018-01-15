package c41.utility.algorithm;

import java.util.Objects;

import c41.lambda.predicate.IPredicate;
import c41.reflect.StaticClassException;
import c41.utility.assertion.Arguments;

public final class LinearSearch {

	private LinearSearch() {
		throw new StaticClassException();
	}

	public static boolean isExist(int[] array, int value) {
		Arguments.isNotNull(array);
		
		for (int i : array) {
			if(i == value) {
				return true;
			}
		}
		return false;
	}

	public static <T> boolean isExist(T[] array, IPredicate<? super T> predicate) {
		Arguments.isNotNull(array);
		Arguments.isNotNull(predicate);
		
		for (T obj : array) {
			if(predicate.is(obj)) {
				return true;
			}
		}
		return false;
	}
	
	public static <T> boolean isExist(T[] array, T value) {
		Arguments.isNotNull(array);
		
		for (T obj : array) {
			if(Objects.equals(obj, value)) {
				return true;
			}
		}
		return false;
	}
	
}
