package core.controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import c41.reflect.Types;
import c41.utility.Strings;
import c41.utility.generator.SequenceLongGenerator;
import core.controller.validation.NoSuchValidationException;
import core.controller.validation.ProcessorManager;
import core.controller.validation.ValidationFailException;
import core.logger.Logs;
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
		
		@SuppressWarnings("unchecked")
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
		String[] names = Java.parameterNames(method);
		Object[] args = ActionInvoker.getActionMethodArgs(method, request.controllerInstance);
		
		for(int i=0; i<parameters.length; i++) {
			Parameter parameter = parameters[i];
			Class parameterType = Types.toBoxClass(parameter.getType());
			String name = names[i];
			String[] raw; 
			if(parameterType.isArray()) {
				raw = request.params.getAll(name+"[]");
			}
			else {
				raw = request.params.getAll(name);
			}
			
			if(args[i] != null && args[i].getClass() != parameterType) {
				args[i] = null;
			}
			
			for(Annotation annotation : parameter.getAnnotations()) {
				Class annotationType = annotation.getClass().getInterfaces()[0];
				try {
					args[i] = validation.process(annotationType, parameterType, annotation, args[i], raw);
				}
				catch (ValidationFailException e) {
					String msg;
					if(raw!=null && raw.length == 1) {
						msg = Strings.format("%s=%s %s",  name, raw[0], e.getMessage());
					}
					else {
						msg = Strings.format("%s=%s %s",  name, Arrays.toString(raw), e.getMessage());
					}
					Logs.Http.warn("%s: %s", request.url, msg);
					throw new ValidationFailException(msg);
				}
				catch (NoSuchValidationException e) {
					Logs.Http.error("No validation %s", e.getMessage());
				}
			}
		}
	}
	
}
