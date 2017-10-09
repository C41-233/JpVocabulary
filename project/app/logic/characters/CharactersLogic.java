package logic.characters;

import java.util.List;

import base.utility.Chars;
import core.model.hql.HQL;
import core.model.hql.HQLResult;
import core.model.hql.Like;
import logic.LogicBase;
import models.Character;
import po.ICharacter;

public final class CharactersLogic extends LogicBase{

	private CharactersLogic() {}
	
	public static List<ICharacter> findCharactersByIndex(String index){
		HQL hql = HQL.begin();
		hql.where(Like.contains("pinyins", "|"+index+"|"));
		HQLResult result = hql.end();
		return Character.find(result.select, result.params).fetch();
	}

	public static ICharacter getCharacer(long id) {
		return Character.findById(id);
	}
	
	public static ICharacter findCharacter(String jp) {
		return Character.find("jp=?1", jp).first();
	}
	
	public static boolean hasCharacter(String jp) {
		return Character.find("jp=?1", jp).first() != null;
	}
	
	public static ICharacter createCharacter(String jp, String cn, List<String> pinyins) {
		if(hasCharacter(jp)) {
			raise("汉字已存在：%s", jp);
		}
		
		if(!Chars.isCJKCharacter(jp.codePointAt(0))) {
			raise("不是汉字：%s", jp.charAt(0));
		}

		if(!Chars.isCJKCharacter(cn.codePointAt(0))) {
			raise("不是汉字：%s", cn.charAt(0));
		}
		
		Character character = new Character();
		character.setJpValue(jp);
		character.setCnValue(cn);
		character.setPinyins(pinyins);
		character.save();
		return character;
	}

	public static void deleteCharacater(long id) {
		Character character = Character.findById(id);
		if(character == null) {
			raise("汉字不存在: id=%d", id);
		}
		character.delete();
	}

}
