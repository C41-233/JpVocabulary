package base.reflect;

public interface MemberDomains extends MemberDomainFlag{

	public static final int NonPublic = Protected | Internal | Private;
	
	public static final int AllAccess = Public | NonPublic;

	public static final int DeclaredOnly = AllAccess | Instance | Static | Invoke | Abstract;
	
	public static final int Default = Public | Inherited | Instance | Static | Invoke | Abstract | OverrideHidden;
	
	public static final int All = 0xFFFFFFFF;
	
}
