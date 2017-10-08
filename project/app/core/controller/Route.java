package core.controller;

import play.mvc.Router;

public final class Route{

	private Route() {}
	
	public static String get(Class<? extends ControllerBase> controller, String method, RouteArgs args) {
		return Router.getFullUrl(controller.getName()+"."+method, args.hashMap);
	}
	
}
