package base.utility.generator;

import java.util.concurrent.atomic.AtomicLong;

public class SequenceLongGenerator implements ILongGenerator{

	private final AtomicLong value;
	
	public SequenceLongGenerator() {
		this.value = new AtomicLong();
	}
	
	public SequenceLongGenerator(long initValue) {
		this.value = new AtomicLong(initValue);
	}
	
	@Override
	public long nextLong() {
		return value.incrementAndGet();
	}

}
