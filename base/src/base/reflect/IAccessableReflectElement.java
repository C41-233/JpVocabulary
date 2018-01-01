package base.reflect;

/**
 * 可以标记可访问性的反射元素。
 */
public interface IAccessableReflectElement extends IModiferReflectElement{

	public boolean isPublic();
	public boolean isProtected();
	public boolean isInternal();
	public boolean isPrivate();
	
}
