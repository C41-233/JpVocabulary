package base.reflect;

public enum MemberDomain {

	/**
	 * 所有可访问的public的成员
	 */
	Public,
	
	/**
	 * 所有由该类型定义的成员
	 */
	Declared,
	
	/**
	 * 所有可访问的public成员，以及所有由该类型定义的成员
	 */
	PublicOrDeclared,
	
	/**
	 * 所有该类型及其所有父类型或接口定义的成员
	 */
	AllInherited,
	
}
