package controllers.basics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import c41.utility.linq.Linq;
import core.config.XmlReader;
import core.controller.HtmlControllerBase;
import core.logger.Logs;

public class Basic extends HtmlControllerBase{

	public static void index() {
		List<ContentGroup> groups = processContent();
		detail(groups.get(0).contents.get(0).value);
	}
	
	public static void detail(String index) {
		List<ContentGroup> groups = processContent();
		ContentGroup.Content thisContent = Linq.from(groups).selectMany(group->group.contents).findFirst(content->content.value.equals(index));
		if(thisContent == null) {
			notFound();
		}
		renderArgs.put("index", thisContent);
		
		File dataFile = new File("data/basics/"+thisContent.value+".xml");
		if(dataFile.isFile()) {
			BasicType basic = XmlReader.read(dataFile, BasicType.class);
			if(Objects.equals(basic.name, thisContent.name) == false) {
				Logs.Logic.error("basic not match of %s and %s", basic.name, thisContent.name);
			}
			renderArgs.put("data", basic);
		}
		
		render("other/basic");
	}

	private static List<ContentGroup> processContent() {
		Contents contents = XmlReader.read(new File("data/basics/contents.xml"), Contents.class);
		renderArgs.put("groups", contents.groups);
		return contents.groups;
	}
	
	@XmlRootElement
	private static class Contents{
		
		@XmlElement(name="group")
		public List<ContentGroup> groups;
		
	}
	
	private static class ContentGroup{
		
		@XmlAttribute
		public String name;
		
		@XmlElement(name="content")
		public List<Content> contents = new ArrayList<>();

		private static class Content{
			@XmlAttribute
			public String name;
			@XmlAttribute
			public String value;
		}
		
	}
	
	@XmlRootElement
	private static class BasicType{
		
		@XmlAttribute
		public String name;
		
		@XmlElement(name="name-hence")
		public String hence;
		
		@XmlElement(name="name-hint")
		public String hint;
		
		@XmlElement(name="usage")
		public List<String> usages = new ArrayList<>();
		
		@XmlElement(name="value")
		public List<ValueType> values = new ArrayList<>();
		
		private static class ValueType{
			
			@XmlAttribute
			public String type;
			
			public String meaning;
			
			@XmlElement(name="example")
			public List<ExampleType> examples = new ArrayList<>();
			
			@XmlElement(name="usage")
			public List<UsageType> usages = new ArrayList<>();
		}
		
		private static class ExampleType{
			public String jp;
			public String cn;
		}
		
		private static class UsageType{
			@XmlElement(name="value")
			public List<String> values = new ArrayList<>();
			
			public String meaning;
			
			@XmlElement(name="example")
			public List<ExampleType> examples = new ArrayList<>();
		}
	}
	
}
