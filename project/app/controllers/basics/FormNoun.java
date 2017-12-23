package controllers.basics;

import core.controller.HtmlControllerBase;

public class FormNoun extends HtmlControllerBase{

	public static void index() {
		detail("1");
	}
	
	public static void detail(String index) {

		render("other/formnoun");
	}
	
}
