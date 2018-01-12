package c41.core;

public interface ICloneable<T extends ICloneable<T>> extends Cloneable {

	public T clone();
	
}
