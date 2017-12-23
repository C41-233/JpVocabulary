package base.reflect;

final class MemberDomainFlags{

	private final int flags;
	
	public MemberDomainFlags(int flags) {
		this.flags = flags;
	}
	
	private boolean is(int flag) {
		return (flags & flag) != 0;
	}
	
	public boolean isPublic() {
		return is(MemberDomainFlag.Public);
	}

	public boolean isProtected() {
		return is(MemberDomainFlag.Protected);
	}

	public boolean isInternal() {
		return is(MemberDomainFlag.Internal);
	}
	
	public boolean isPrivate() {
		return is(MemberDomainFlag.Private);
	}
	
	public boolean isStatic() {
		return is(MemberDomainFlag.Static);
	}

	public boolean isInstance() {
		return is(MemberDomainFlag.Instance);
	}

	public boolean isInterited() {
		return is(MemberDomainFlag.Inherited);
	}
	
}
