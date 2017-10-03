package core.controller;

import org.hibernate.ejb.criteria.Renderable;

public abstract class HtmlControllerBase extends ControllerBase{

	protected static void render(String name){
		renderTemplate("application/"+name+".html");
	}
	
}
