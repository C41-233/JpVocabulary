package c41.utility.lambda.action;

@FunctionalInterface
public interface ICharAction extends IAction1<Character>{

	public void invoke(char ch);
	
	@Override
	public default void invoke(Character ch) {
		invoke((char)ch);
	}
	
}
