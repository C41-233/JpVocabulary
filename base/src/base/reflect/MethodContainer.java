package base.reflect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import base.utility.collection.list.TypeArrayList;
import base.utility.linq.Linq;

final class MethodContainer {

	private static class MethodData{
		
		public final MethodInfo method;
		public final boolean isDeclared;
		
		public MethodData(MethodInfo method, boolean isDeclared) {
			this.method = method;
			this.isDeclared = isDeclared;
		}
	}
	
	private static class MethodGroup{
		public final MethodInfo[] declared;
		public final MethodInfo[] export;
		
		public MethodGroup(ArrayList<MethodData> list) {
			this.declared = Linq.from(list).where(m->m.isDeclared).select(m->m.method).toArray(MethodInfo.class);
			this.export = Linq.from(list).select(m->m.method).toArray(MethodInfo.class);
		}
	}
	
	private final TypeInfo type;
	
	private MethodInfo[] cachedDeclaredMethods;
	private MethodInfo[] cachedExportMethods;
	private final HashMap<String, MethodGroup> nameToMethods = new HashMap<>();
	
	public MethodContainer(TypeInfo type) {
		this.type = type;
	}
	
	public MethodInfo[] getDeclaredMethods() {
		return getCachedDeclaredMethodsInner().clone();
	}
	
	public MethodInfo[] getMethods() {
		return getCachedExportMethodsInner();
	}
	
	public MethodInfo[] getDeclaredMethods(String name) {
		 MethodGroup group = getCachedMethodsInner().get(name);
		 if(group == null) {
			 return new MethodInfo[0];
		 }
		 return group.declared.clone();
	}
	
	public MethodInfo[] getMethods(String name) {
		 MethodGroup group = getCachedMethodsInner().get(name);
		 if(group == null) {
			 return new MethodInfo[0];
		 }
		 return group.export.clone();
	}

	public MethodInfo getDeclaredMethod(String name) {
		 MethodGroup group = getCachedMethodsInner().get(name);
		 if(group == null) {
			 return null;
		 }
		 if(group.declared.length == 0) {
			 return null;
		 }
		 if(group.declared.length > 1) {
			 //throw new AmbigousMethodException();
		 }
		 return group.declared[0];
	}
	
	public MethodInfo getMethod(String name) {
		 MethodGroup group = getCachedMethodsInner().get(name);
		 if(group == null) {
			 return null;
		 }
		 if(group.export.length == 0) {
			 return null;
		 }
		 if(group.export.length > 1) {
			// throw new AmbigousMethodException();
		 }
		 return group.export[0];
	}
	
	private MethodInfo[] getCachedDeclaredMethodsInner() {
		if(cachedDeclaredMethods == null) {
			cachedDeclaredMethods = Linq.from(type.clazz.getDeclaredMethods())
					.select(m->ReflectCache.wrap(m))
					.toArray(MethodInfo.class);
		}
		return cachedDeclaredMethods;
	}
	
	private MethodInfo[] getCachedExportMethodsInner() {
		tryInitMethods();
		return cachedExportMethods;
	}
	
	private HashMap<String, MethodGroup> getCachedMethodsInner(){
		tryInitMethods();
		return nameToMethods;
	}
	
	private void tryInitMethods() {
		if(cachedExportMethods != null) {
			return;
		}
		
		TypeArrayList<MethodInfo> list = new TypeArrayList<>(MethodInfo.class);
		HashMap<String, ArrayList<MethodData>> nameToExportMethods = new HashMap<>();
		
		for(MethodInfo method : getCachedDeclaredMethodsInner()) {
			list.add(method);
			tryAddMethodToGroup(nameToExportMethods, method, true);
		}
		
		for(TypeInfo base : type.getAssignableSuperTypes()) {
			for(MethodInfo method : base.methods.getCachedDeclaredMethodsInner()) {
				if(!method.isPublic() && !method.isProtected()) {
					continue;
				}
				if(tryAddMethodToGroup(nameToExportMethods, method, false)) {
					list.add(method);
				}
			}
		}
		
		for(Entry<String, ArrayList<MethodData>> kv : nameToExportMethods.entrySet()) {
			nameToMethods.put(kv.getKey(), new MethodGroup(kv.getValue()));
		}
		
		cachedExportMethods = list.toArray();
	}
	
	private boolean tryAddMethodToGroup(HashMap<String, ArrayList<MethodData>> nameToExportMethods, MethodInfo method, boolean isDeclared) {
		String name = method.getName();
		ArrayList<MethodData> list = nameToExportMethods.get(name);
		if(list == null) {
			list = new ArrayList<>();
			nameToExportMethods.put(name, list);
		}
		
		if(isDeclared) {
			list.add(new MethodData(method, isDeclared));
			return true;
		}
		
		for(MethodData data : list) {
			if(data.method.isOverrideOf(method)) {
				return false;
			}
		}
		
		list.add(new MethodData(method, isDeclared));
		return true;
	}

}
