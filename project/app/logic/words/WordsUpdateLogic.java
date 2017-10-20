package logic.words;

import java.util.List;

import base.utility.function.Predicates;
import base.utility.linq.Linq;
import logic.LogicBase;
import logic.LogicValidate;
import logic.pinyins.WordQueryIndex;
import models.NotionalWord;
import models.NotionalWordValue;
import po.INotionalWord;
import po.NotionalWordType;
import po.NotionalWordValueType;

public final class WordsUpdateLogic extends LogicBase{

	public static INotionalWord create(List<String> values, List<String> meanings, List<String> types) {
		//必须至少有一个注音
		if(Linq.from(values).notExist(LogicValidate::isValidSyllable)) {
			raise("基本词必须至少有一个读音");
		}
		
		//词性必须全部合法
		if(Linq.from(types).notAll(NotionalWordType::isValidType)) {
			raise("不合法的词性：%s", Linq.from(types).find(Predicates.not(NotionalWordType::isValidType)));
		}
		
		for(String value : values) {
			//检查每个单词都存在索引
			if(WordQueryIndex.getWordQueryIndex(value).size() == 0) {
				raise("单词无法生成索引，需要先添加汉字：%s", value);
			}
			//检查非读音的单词是否重复
			if(LogicValidate.isValidSyllable(value)==false && NotionalWordsQueryLogic.hasNotionalWordValue(value)) {
				raise("单词已存在：%s", value);
			}
		}
		
		NotionalWord word = new NotionalWord();
		word.setMeanings(meanings);
		word.setTypes(types);
		word.save();
		
		for(String value : values) {
			NotionalWordValue notionalWordValue = new NotionalWordValue();
			notionalWordValue.setValue(value);
			notionalWordValue.setNotionalWordId(word.getId());
			notionalWordValue.setTypes(NotionalWordValueType.getWordValueType(value));
			notionalWordValue.setIndexes(WordQueryIndex.getWordQueryIndex(value));
			
			notionalWordValue.save();
		}
		
		return word;
	}
	
}
