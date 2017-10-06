package core.controller;

import com.google.gson.JsonObject;

import logic.LogicException;
import play.mvc.Catch;

public abstract class AjaxControllerBase extends ControllerBase{

	@Catch(LogicException.class)
	private static void onLogicException(LogicException e) {
		response.status = 200;
		response.contentType = "application/json";
		JsonObject jobj = new JsonObject();
		jobj.addProperty("result", 1);
		jobj.addProperty("message", e.getMessage());
		renderText(jobj);
	}
	
}
