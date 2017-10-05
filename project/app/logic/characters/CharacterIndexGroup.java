package logic.characters;

import java.util.ArrayList;
import java.util.List;

class CharacterIndexGroup implements ICharacterIndexGroup{
	
	String name;
	
	List<String> items = new ArrayList<>();

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Iterable<String> getItems() {
		return items;
	}
	
}