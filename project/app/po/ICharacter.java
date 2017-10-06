package po;

import java.util.List;

import core.model.IModel;

public interface ICharacter extends IModel{

	public String getJpValue();
	
	public String getCnValue();
	
	public List<String> getPinyins();
}
