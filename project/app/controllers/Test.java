package controllers;

import core.controller.AjaxControllerBase;
import logic.LogicException;

public class Test extends AjaxControllerBase{

	public static void test() {
		throw new LogicException("444");
	}
	
}
