package controllers.sentences;

import java.io.File;
import java.util.List;

import c41.utility.linq.Linq;
import c41.xml.XmlReader;
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
		XmlReader reader = new XmlReader();
		List<Content> contents = reader.readList(ContentFile, Content.class, "content");
		renderArgs.put("contents", contents);
		return contents;
	}
	
	private static class Content{
		public String name;
		public String value;
	}
	
}
