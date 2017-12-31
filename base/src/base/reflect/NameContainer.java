package base.reflect;

import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.StringJoiner;

final class NameContainer {

	private final TypeInfo<?> type;
	
	public NameContainer(TypeInfo<?> type) {
		this.type = type;
	}

	public String getName() {
		TypeInfo<?> enclosing = type.getEnclosingType();
		if(enclosing == null) {
			return type.clazz.getCanonicalName();
		}
		String superName = enclosing.getName();
		String jname = type.clazz.getName();
		return superName + "$" + jname.substring(jname.lastIndexOf('$')+1);
	}
	
	private static final HashMap<Class, String> primitiveToSpecific = new HashMap<>();
	static {
		primitiveToSpecific.put(byte.class, "B");
		primitiveToSpecific.put(char.class, "C");
		primitiveToSpecific.put(short.class, "S");
		primitiveToSpecific.put(int.class, "I");
		primitiveToSpecific.put(long.class, "J");
		primitiveToSpecific.put(double.class, "D");
		primitiveToSpecific.put(float.class, "F");
		primitiveToSpecific.put(boolean.class, "Z");
		primitiveToSpecific.put(void.class, "V");
	}
	
	public String getVMSignatureName() {
		{
			//基本类型
			String name = primitiveToSpecific.get(type.clazz);
			if(name != null) {
				return name;
			}
		}
		{
			//数组类型
			if(type.clazz.isArray()) {
				TypeInfo compoment = type.getArrayComponentType();
				return "["+compoment.getVMSignatureName();
			}
		}
		return "L"+getName().replace('.', '/')+";";
	}

	public String getSimpleName() {
		String jname = type.clazz.getSimpleName();
		if(jname==null || jname.isEmpty()) {
			return null;
		}
		return jname;
	}
	
	private String getGenericPartName() {
		StringJoiner joiner = new StringJoiner(",","<",">");
		for (TypeVariable<?> variable : type.getGenericTypeParameters()) {
			joiner.add(variable.getName());
		}
		return joiner.toString();
	}

	public String getGenericSimpleName() {
		String simpleName = getSimpleName();
		if(simpleName == null) {
			return null;
		}
		return simpleName + getGenericPartName();
	}

	public String getGenericName() {
		return getName()+getGenericPartName();
	}

}
