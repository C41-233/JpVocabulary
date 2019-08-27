package controllers.basics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import c41.utility.linq.Linq;
import core.config.XmlReader;
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
		
		File file = new File("../data/formnouns/"+content.value+".xml");
		if(file.exists()) {
			FormNounType data = XmlReader.read(file, FormNounType.class);
			renderArgs.put("data", data);
		}
		render("other/formnoun");
	}
	
	private static List<Content> processContents() {
		File file = new File("data/formnouns/contents.xml");
		Contents contents = XmlReader.read(file, Contents.class);
		renderArgs.put("contents", contents.contents);
		return contents.contents;
	}
	
	@XmlRootElement(name="contents")
	private static class Contents{
		
		@XmlElement(name="content")
		public List<Content> contents = new ArrayList<>();
		
	}
	
	private static class Content{
		@XmlAttribute
		public String name;
		@XmlAttribute
		public String align;
		@XmlAttribute
		public String value;
	}

	@XmlRootElement(name="formnoun")
	private static class FormNounType{
		
		@XmlElement(name="value")
		public List<ValueType> values = new ArrayList<>();
		
		private static class ValueType{
			
			public String usage;
			
			public String meaning;
			
			@XmlElement(name="example")
			public List<ExampleType> examples = new ArrayList<>();
		}
		
		private static class ExampleType{
			public String jp;
			public String cn;
		}
	}
	
}
