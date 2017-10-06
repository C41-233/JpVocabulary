package base.utility.generator;

public interface ILongGenerator extends IGenerator<Long>{

	public long nextLong();
	
	@Override
	default public Long nextValue() {
		return nextLong();
	}
	
}
