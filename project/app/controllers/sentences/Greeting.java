package controllers.sentences;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import c41.utility.linq.Linq;
import core.config.XmlReader;
import core.controller.HtmlControllerBase;

public class Greeting extends HtmlControllerBase{

	private static final File BaseFile = new File("data/greetings");
	private static final File ContentFile = new File(BaseFile, "contents.xml");
	
	public static void index() {
		List<Content> contents = readContents();
		page(contents.get(0).name);
	}
	
	public static void page(String name) {
		List<Content> contents = readContents();
		Content current = Linq.from(contents).findFirst(c->c.name.equals(name));
		if(current == null) {
			notFound();
		}
		renderArgs.put("current", current);
		
		File file = new File(BaseFile, name+".xml");
		if(file.exists()) {
		}
		
		render("sentences/greetings");
	}
	
	private static List<Content> readContents() {
		Contents contents = XmlReader.read(ContentFile, Contents.class);
		renderArgs.put("contents", contents.contents);
		return contents.contents;
	}
	
	private static class Contents{
		
		@XmlElement(name="content")
		public List<Content> contents = new ArrayList<>();
		
	}
	
	private static class Content{
		public String name;
		public String value;
	}
	
}
