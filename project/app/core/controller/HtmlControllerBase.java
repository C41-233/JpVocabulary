package core.controller;

import java.util.Map.Entry;

import com.google.gson.JsonObject;

import core.controller.validation.ValidationFailException;
import play.mvc.Catch;

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

	@Catch(ValidationFailException.class)
	private static void onValidationFailException(ValidationFailException e) {
		response.status = 400;
		renderArgs.put("message", e.getMessage());
		renderTemplate("errors/400.html");
	}
	
}
