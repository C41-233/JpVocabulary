package controllers.verbs;

import java.util.ArrayList;
import java.util.List;

import c41.utility.linq.Linq;
import core.controller.HtmlControllerBase;
import core.controller.Route;
import core.controller.validation.annotation.Id;
import logic.LogicValidate;
import logic.convert.verb.VerbConvert;
import logic.words.VerbWordsLogic;
import po.IVerbWord;
import po.VerbWordType;

public class VerbWordDetail extends HtmlControllerBase{

	public static void index(@Id long id, String refer) {
		if(refer == null) {
			refer = Route.get(VerbWordIndex.class, "index");
		}
		
		renderArgs.put("refer", refer);
		
		IVerbWord word = VerbWordsLogic.getVerbWordAndUpdate(id);
		if(word == null) {
			notFound("动词不存在：id=%d"+id);
		}
		
		WordVO wordVO = new WordVO();
		wordVO.id = id;
		
		List<ConvertVO> convertsVO = new ArrayList<>();
		
		Linq.from(word.getValues())
			.select(w->w.getValue())
			.orderBy(VerbWordsLogic.ValueComparator)
			.foreach(value->{
				if(LogicValidate.isValidSyllable(value)) {
					wordVO.values2.add(value);
				}
				else {
					wordVO.values1.add(value);
				}
				ConvertVO vo = new ConvertVO();
				VerbWordType mainType = word.getMainType();
				vo.基本型 = VerbConvert.基本型.convert(value, mainType);
				vo.终止型 = VerbConvert.终止型.convert(value, mainType);
				vo.连体型 = VerbConvert.连体型.convert(value, mainType);
				vo.连用型1 = VerbConvert.连用型1.convert(value, mainType);
				vo.连用型2 = VerbConvert.连用型2.convert(value, mainType);
				vo.连用型M = VerbConvert.连用型M.convert(value, mainType);
				vo.连用型T = VerbConvert.连用型T.convert(value, mainType);
				vo.未然型1 = VerbConvert.未然型1.convert(value, mainType);
				vo.未然型2 = VerbConvert.未然型2.convert(value, mainType);
				vo.假定型 = VerbConvert.假定型.convert(value, mainType);
				vo.命令型1 = VerbConvert.命令型1.convert(value, mainType);
				vo.命令型2 = VerbConvert.命令型2.convert(value, mainType);
				vo.意志型 = VerbConvert.意志型.convert(value, mainType);
				vo.推量型 = VerbConvert.推量型.convert(value, mainType);
				vo.可能态1 = VerbConvert.可能态1.convert(value, mainType);
				vo.可能态2 = VerbConvert.可能态2.convert(value, mainType);
				vo.被动态1 = VerbConvert.被动态1.convert(value, mainType);
				vo.被动态2 = VerbConvert.被动态2.convert(value, mainType);
				vo.自发态1 = VerbConvert.自发态1.convert(value, mainType);
				vo.自发态2 = VerbConvert.自发态2.convert(value, mainType);
				vo.使役态1 = VerbConvert.使役态1.convert(value, mainType);
				vo.使役态2 = VerbConvert.使役态2.convert(value, mainType);
				vo.被役态1 = VerbConvert.被役态1.convert(value, mainType);
				vo.被役态2 = VerbConvert.被役态2.convert(value, mainType);
				vo.连用型CJ = VerbConvert.连用型CJ.convert(value, mainType);
				vo.连用型D = VerbConvert.连用型D.convert(value, mainType);
				vo.未然型N = VerbConvert.未然型N.convert(value, mainType);
				vo.未然型Z = VerbConvert.未然型Z.convert(value, mainType);
				vo.假定型B = VerbConvert.假定型B.convert(value, mainType);
				convertsVO.add(vo);
			});
			
		Linq.from(word.getTypes()).foreachEx(t->wordVO.types.add(t.toFull()));
		Linq.from(word.getMeanings()).select(m->m+"。").foreachEx(m->wordVO.meanings.add(m));
		Linq.from(word.getFixwords())
			.select(f->{
				FixwordVO vo = new FixwordVO();
				vo.value = f.getValue();
				vo.meaning = f.getMeaning();
				return vo;
			}).foreachEx(m->wordVO.fixwords.add(m));
		
		renderArgs.put("word", wordVO);
		renderArgs.put("converts", convertsVO);
		
		render("verbs/verb-detail");
	}
	
	private static class WordVO{
		public long id;
		public List<String> values1 = new ArrayList<>();
		public List<String> values2 = new ArrayList<>();
		public List<String> types = new ArrayList<>();
		public List<String> meanings = new ArrayList<>();
		public List<FixwordVO> fixwords = new ArrayList<>();
	}
	
	private static class FixwordVO{
		public String value;
		public String meaning;
	}
	
	private static class ConvertVO{
		public String 基本型;
		public String 终止型;
		public String 连体型;
		public String 连用型1;
		public String 连用型2;
		public String 连用型M;
		public String 连用型T;
		public String 未然型1;
		public String 未然型2;
		public String 假定型;
		public String 命令型1;
		public String 命令型2;
		public String 意志型;
		public String 推量型;
		public String 可能态1;
		public String 可能态2;
		public String 被动态1;
		public String 被动态2;
		public String 自发态1;
		public String 自发态2;
		public String 使役态1;
		public String 使役态2;
		public String 被役态1;
		public String 被役态2;
		public String 连用型CJ;
		public String 连用型D;
		public String 未然型N;
		public String 未然型Z;
		public String 假定型B;
	}
	
}
