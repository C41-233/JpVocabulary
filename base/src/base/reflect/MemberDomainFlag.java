package base.reflect;

public interface MemberDomainFlag {

	/**
	 * 成员被修饰为public
	 */
	public static final int Public = 			0x00000001;
	
	/**
	 * 成员被修饰为protected
	 */
	public static final int Protected = 		0x00000002;
	
	/**
	 * 成员被修饰为internal
	 */
	public static final int Internal = 			0x00000004;
	
	/**
	 * 成员为修饰为private
	 */
	public static final int Private = 			0x00000008;
	/**
	 * 实例成员
	 */
	public static final int Instance = 			0x00000010;
	
	/**
	 * 静态成员
	 */
	public static final int Static = 			0x00000020;
	
	/**
	 * 非抽象（方法）
	 */
	public static final int Invoke = 			0x00000100;
	
	/**
	 * 抽象（方法）
	 */
	public static final int Abstract =			0x00000200;
	
	/**
	 * 在反射子类时，搜索父类型时被子类override（方法）
	 */
	public static final int OverrideHidden = 	0x00000400;
	
	/**
	 * 所有继承而来的成员，否则只搜索当前类型声明的成员
	 */
	public static final int Inherited = 		0x00010000;

}
