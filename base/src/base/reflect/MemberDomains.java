package base.reflect;

public interface MemberDomains extends MemberDomainFlag{

	public static final int NonPublic = Protected | Internal | Private;
	
	public static final int AllAccess = Public | NonPublic;

	public static final int AllDeclared = AllAccess | Instance | Static | Invoke | Abstract;
	
	public static final int AllPublic = Public | Inherited | Instance | Static | Invoke | Abstract;
	
	public static final int All = 0xFFFFFFFF;
	
}
