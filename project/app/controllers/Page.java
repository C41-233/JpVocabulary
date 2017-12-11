package controllers;

import core.controller.HtmlControllerBase;

public final class Page extends HtmlControllerBase{
	
	public static void _50() {
		of("50");
	}

	public static void verb() {
		of("verb");
	}

	public static void other() {
		of("other");
	}

	public static void numeral() {
		of("numeral");
	}
	
	public static void auxiliary() {
		of("auxiliary");
	}
	
	private static void of(String file) {
		render("pages/"+file);
	}
	
}
