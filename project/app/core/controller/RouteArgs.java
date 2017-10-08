package core.controller;

import java.util.HashMap;

public final class RouteArgs {

	final HashMap<String, Object> hashMap = new HashMap<>();
	
	public RouteArgs put(String key, Object value) {
		this.hashMap.put(key, value);
		return this;
	}
	
}
