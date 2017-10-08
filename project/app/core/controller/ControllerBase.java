package core.controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import base.reflect.TypeBox;
import base.utility.Strings;
import base.utility.generator.SequenceLongGenerator;
import core.controller.validation.ValidationFailException;
import core.controller.validation.processor.ProcessorManager;
import play.mvc.ActionInvoker;
import play.mvc.Before;
import play.mvc.Controller;
import play.utils.Java;

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
	
	private static ProcessorManager validation = new ProcessorManager();
	
	@Before
	private static void before() throws Exception{
		Method method = request.invokedMethod;
		Parameter[] parameters = method.getParameters();
		String[] names;
		Object[] args;
		try {
			names = Java.parameterNames(method);
			args = ActionInvoker.getActionMethodArgs(method, request.controllerInstance);
		} catch (Exception e) {
			throw e;
		}
		
		for(int i=0; i<parameters.length; i++) {
			Parameter parameter = parameters[i];
			Class parameterType = TypeBox.toBoxType(parameter.getType());
			String name = names[i];
			String raw = request.params.get(name);
			
			if(args[i] != null && args[i].getClass() != parameterType) {
				args[i] = null;
			}
			
			for(Annotation annotation : parameter.getAnnotations()) {
				Class annotationType = annotation.getClass().getInterfaces()[0];
				try {
					args[i] = validation.process(annotationType, parameterType, annotation, args[i], raw);
				}
				catch (ValidationFailException e) {
					throw new ValidationFailException(
						Strings.format("%s %s",  name, e.getMessage())
					);
				}
			}
		}
	}
	
}
