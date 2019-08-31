package controllers.basics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.thoughtworks.xstream.io.path.Path;

import c41.utility.comparator.Comparators;
import core.controller.HtmlControllerBase;
import core.controller.Route;
import core.controller.RouteArgs;

public class Grammar extends HtmlControllerBase{

	public static void index(String path) throws IOException {
		File base = new File("../data/grammar");
		FileNode root = new FileNode();
		root.path = "";
		makeNode(root, base);
		
		if(path == null) {
			path = "/敬语/敬体动词.html";
		}
		
		renderArgs.put("root", root);
		renderArgs.put("path", path);
		File content = new File(base, path);
		if(content.isFile()) {
			renderArgs.put("title", cname(content.getName()));
			renderArgs.put("content", FileUtils.readFileToString(content, "utf-8"));
		}
		else {
			renderArgs.put("content", path);
		}
		
		render("other/grammar");
	}

	private static void makeNode(FileNode root, File dir) {
		File[] files = dir.listFiles();
		Arrays.sort(files, (f1, f2) -> {
			boolean file1 = f1.isFile();
			boolean file2 = f2.isFile();
			if(file1 && !file2) {
				return -1;
			}
			if(!file1 && file2) {
				return 1;
			}
			return Comparators.compare(cname(f1.getName()), cname(f2.getName()));
		});
		for(File file : files) {
			FileNode node = new FileNode();
			String name = file.getName();
			node.path = root.path + "/" + name;
			
			if(file.isDirectory()) {
				node.name = name;
				makeNode(node, file);
			}
			else {
				node.name = cname(name);
				node.href = Route.get(Grammar.class, "index", new RouteArgs().put("path", node.path));
			}
			root.nodes.add(node);
		}
	}
	
	private static class FileNode{
		public String name;
		public List<FileNode> nodes = new ArrayList<>();
		public String href;
		public String path;
	}

	private static String cname(String name) {
		if(name.endsWith(".html")) {
			return name.substring(0, name.length() - 5);
		}
		else {
			return name;
		}
	}
	
}
