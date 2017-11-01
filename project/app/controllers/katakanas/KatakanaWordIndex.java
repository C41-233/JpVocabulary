package controllers.katakanas;

import controllers.components.HTMLComponentsControllerBase;
import core.controller.validation.annotation.Required;
import logic.indexes.IndexManager;

public final class KatakanaWordIndex extends HTMLComponentsControllerBase{

	public static void index() {
		page(IndexManager.Katakana.getFirst());
	}
	
	public static void page(@Required String index) {
		
	}
	
}
