package logic.characters;

import java.util.ArrayList;
import java.util.List;

import base.core.StaticClass;
import models.Character;
import po.ICharacter;

public final class CharactersLogic extends StaticClass{

	public static List<ICharacter> findCharactersByIndex(String index){
		return Character.find("pinyin like ?", "|"+index+"|").fetch();
	}
	
}
