package po;

import core.model.IModel;

public interface IKatakanaWord extends IModel{

	public String getValue();
	
	public Iterable<String> getMeanings();
	
	public String getAlias();
	
	public Iterable<KatakanaWordType> getTypes();
	
	public KatakanaWordContext getContext();
}
