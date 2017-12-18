package controllers.basics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import base.utility.linq.Linq;
import base.xml.XmlListClass;
import base.xml.XmlReader;
import base.xml.XmlTag;
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
			XmlReader reader = new XmlReader();
			BasicType basic = reader.read(dataFile, BasicType.class);
			if(Objects.equals(basic.name, thisContent.name) == false) {
				Logs.Logic.error("basic not match of %s and %s", basic.name, thisContent.name);
			}
			renderArgs.put("data", basic);
		}
		
		render("other/basic");
	}

	private static List<ContentGroup> processContent() {
		XmlReader reader = new XmlReader();
		List<ContentGroup> groups = reader.readList(new File("data/basics/contents.xml"), ContentGroup.class, "group");
		renderArgs.put("groups", groups);
		return groups;
	}
	
	
	private static class ContentGroup{
		public String name;
		
		@XmlListClass(Content.class)
		public List<Content> contents = new ArrayList<>();

		private static class Content{
			public String name;
			public String value;
		}
		
	}
	
	private static class BasicType{
		public String name;
		@XmlTag("name-hence")
		public String hence;
		@XmlTag("name-hint")
		public String hint;
		
		@XmlListClass(ValueType.class)
		public List<ValueType> values = new ArrayList<>();
		
		private static class ValueType{
			public String type;
			public String meaning;
			
			@XmlListClass(ExampleType.class)
			public List<ExampleType> examples = new ArrayList<>();
			
			@XmlListClass(UsageType.class)
			public List<UsageType> usages = new ArrayList<>();
		}
		
		private static class ExampleType{
			public String jp;
			public String cn;
		}
		
		private static class UsageType{
			public String value;
			public String meaning;
			@XmlListClass(ExampleType.class)
			public List<ExampleType> examples = new ArrayList<>();
		}
	}
	
}
