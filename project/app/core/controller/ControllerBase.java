package core.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.hibernate.dialect.function.VarArgsSQLFunction;

import com.sun.org.apache.bcel.internal.generic.NEW;

import base.utility.generator.SequenceLongGenerator;
import play.mvc.Before;
import play.mvc.Controller;

public abstract class ControllerBase extends Controller{

	protected static final class ArgProxy implements Iterable<Entry<String, Object>>{
		
		private static SequenceLongGenerator idGenerator = new SequenceLongGenerator(System.currentTimeMillis());
		
		private final String name;
		
		public ArgProxy() {
			this.name = "_arg_proxy_id_" + idGenerator.nextLong();
		}
		
		public void put(String key, Object value) {
			map().put(key, value);
		}
		
		public boolean contains(String key) {
			return map().containsKey(key);
		}
		
		public Object get(String key) {
			return map().get(key);
		}
		
		private HashMap<String, Object> map(){
			Object dic = request.args.get(name);
			if(dic == null) {
				dic = new HashMap<String, Object>();
				request.args.put(name, dic);
			}
			return (HashMap<String, Object>) dic;
		}

		@Override
		public Iterator<Entry<String, Object>> iterator() {
			return map().entrySet().iterator();
		}
		
	}
	
}
