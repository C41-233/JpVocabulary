package core.controller;

import java.util.Map.Entry;

import com.google.gson.JsonObject;

public abstract class HtmlControllerBase extends ControllerBase{

	protected static final ArgProxy jsArgs = new ArgProxy();
	
	protected static void render(String name){
		renderJsArgs();
		renderTemplate("application/"+name+".html");
	}
	
	private static void renderJsArgs() {
		JsonObject jobj = new JsonObject();
		renderArgs.put("jsArgs", jobj);
		for(Entry<String, Object> entry : jsArgs) {
			String key = entry.getKey();
			Object arg = entry.getValue();
			if(arg instanceof String) {
				jobj.addProperty(key, (String)arg);
			}
			else if(arg instanceof Number) {
				jobj.addProperty(key, (Number)arg);
			}
		}
		
	}
	
}
