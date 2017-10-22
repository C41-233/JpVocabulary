package core.model;

import play.db.jpa.Model;

public abstract class ModelBase extends Model implements IModel{

	@SuppressWarnings("unchecked")
	@Override
	public ModelBase save() {
		ModelBase entity = super.save();
		return entity;
	}
	
}
