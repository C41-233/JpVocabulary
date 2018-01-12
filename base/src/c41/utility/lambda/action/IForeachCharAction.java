package c41.utility.lambda.action;

@FunctionalInterface
public interface IForeachCharAction extends IForeachAction<Character>{

	public void invoke(char ch, int i);
	
	@Override
	public default void invoke(Character ch, int i) {
		invoke((char)ch, i);
	}
	
}
