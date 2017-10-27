package core.controller;

import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import base.utility.Strings;
import core.controller.validation.ValidationFailException;
import logic.LogicException;
import play.mvc.After;
import play.mvc.Catch;
import play.mvc.Http;

public abstract class AjaxControllerBase extends ControllerBase{

	protected static final ArgProxy jsonResult = new ArgProxy();
	
	@After
	private static void after() {
		if(response.status != Http.StatusCode.OK) {
			return;
		}
		
		if(response.contentType == null) {
			response.contentType = "application/json";
		}
		
		JsonObject jobj = new JsonObject();
		for(Entry<String, Object> entry : jsonResult) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if(value instanceof String) {
				jobj.addProperty(key, (String) value);
			}
			else if(value instanceof Number) {
				jobj.addProperty(key, (Number)value);
			}
			else if(value instanceof Boolean) {
				jobj.addProperty(key, (Boolean)value);
			}
			else if(value instanceof Character) {
				jobj.addProperty(key, (Character)value);
			}
			else {
				jobj.add(key, (JsonElement)value);
			}
		}
		
		if(jobj.has("result") == false) {
			jobj.addProperty("result", 0);
		}
		
		if(jobj.has("message") == false) {
			JsonElement element = jobj.get("result");
			if(element.isJsonPrimitive() && element.getAsNumber().equals(0)) {
				jobj.addProperty("message", "success");
			}
			else {
				jobj.addProperty("message", "");
			}
		}
		
		renderText(jobj);
	}
	
	@Catch(LogicException.class)
	private static void onLogicException(LogicException e) {
		response.contentType = "application/json";
		
		JsonObject jobj = new JsonObject();
		jobj.addProperty("result", 400);
		jobj.addProperty("message", e.getMessage());
		
		renderText(jobj);
	}
	
	@Catch(ValidationFailException.class)
	private static void onValidationFailException(ValidationFailException e) {
		badRequest(e.getMessage());
	}

	protected static void badRequest() {
		badRequest("Bad Request");
	}
	
	protected static void badRequest(String format, Object...args) {
		response.status = Http.StatusCode.BAD_REQUEST;
		renderText(Strings.format(format, args));
	}

	protected static void renderJsonError(String format, Object...args) {
		throw new LogicException(Strings.format(format, args));
	}

}
