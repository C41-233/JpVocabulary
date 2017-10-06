package logic.characters;

import java.util.ArrayList;
import java.util.List;

import com.sun.imageio.spi.RAFImageInputStreamSpi;

import base.core.StaticClass;
import logic.LogicBase;
import models.Character;
import po.ICharacter;

public final class CharactersLogic extends LogicBase{

	public static List<ICharacter> findCharactersByIndex(String index){
		return Character.find("pinyin like ?1", "|"+index+"|").fetch();
	}

	public static ICharacter findCharacter(String jp) {
		return Character.find("jp=?1", jp).first();
	}
	
	public static boolean hasCharacter(String jp) {
		return Character.find("jp=?1", jp).first() != null;
	}
	
	public static ICharacter createCharacter(String jp, String cn, List<String> pinyins) {
		if(hasCharacter(jp)) {
			raise("汉字%s已存在", jp);
		}
		
		Character character = new Character();
		character.setJpValue(jp);
		character.setCnValue(cn);
		character.setPinyins(pinyins);
		character.save();
		return character;
	}
	
}
