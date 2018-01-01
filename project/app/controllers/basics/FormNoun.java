package controllers.basics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.validator.PublicClassValidator;

import base.utility.linq.Linq;
import base.xml.XmlListClass;
import base.xml.XmlReader;
import core.controller.HtmlControllerBase;

public class FormNoun extends HtmlControllerBase{

	public static void index() {
		List<Content> contents = processContents();
		detail(contents.get(0).value);
	}
	
	public static void detail(String index) {
		List<Content> contents = processContents();
		Content content = Linq.from(contents).findFirst(c->c.value.equals(index));
		if(content == null) {
			notFound();
		}
		renderArgs.put("index", content);
		
		File file = new File("data/formnouns/"+content.value+".xml");
		if(file.exists()) {
			XmlReader reader = new XmlReader();
			FormNounType data = reader.read(file, FormNounType.class);
			renderArgs.put("data", data);
		}
		render("other/formnoun");
	}
	
	private static List<Content> processContents() {
		File file = new File("data/formnouns/contents.xml");
		XmlReader reader = new XmlReader();
		List<Content> contents = reader.readList(file, Content.class, "content");
		renderArgs.put("contents", contents);
		return contents;
	}
	
	private static class Content{
		public String name;
		public String align;
		public String value;
	}

	private static class FormNounType{
		
		@XmlListClass(ValueType.class)
		public List<ValueType> values = new ArrayList<>();
		
		private static class ValueType{
			public String usage;
			public String meaning;
			@XmlListClass(ExampleType.class)
			public List<ExampleType> examples = new ArrayList<>();
		}
		
		private static class ExampleType{
			public String jp;
			public String cn;
		}
	}
	
}
