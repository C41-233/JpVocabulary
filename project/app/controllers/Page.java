package controllers;

import core.controller.HtmlControllerBase;

public final class Page extends HtmlControllerBase{

	public static void of(String file) {
		render("pages/"+file);
	}
	
}
