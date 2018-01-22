package LinqTemplateBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	public static void main(String[] args) {
		if(args.length < 2) {
			System.err.println("usage: [template] [config]");
			return;
		}
		File templateFile = new File(args[0]);
		File configFile = new File(args[1]);
		
		if(!templateFile.isFile()) {
			System.err.println("template not exist" + templateFile);
			return;
		}
		if(!configFile.isFile()) {
			System.err.println("config not exist" + configFile);
			return;
		}
	
		System.out.println("generate start.");
		try {
			ArrayList<HashMap<String, String>> groups = GroupFileProcess.readGroups(configFile);
			TemplateFile tf = new TemplateFile(templateFile);
			for (HashMap<String, String> group : groups) {
				String filename= group.get("File");
				System.out.println("gen "+ filename);
				
				File target = new File(templateFile.getParent(), filename);
				String text = tf.render(group);
				try(FileOutputStream fos = new FileOutputStream(target)){
					fos.write(text.getBytes("utf8"));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
