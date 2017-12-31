package base.reflect;

import java.util.ArrayList;
import java.util.List;

final class ReflectHelper {

	public static int getDistance(Class<?>[] types, Object[] args) {
		if(types.length != args.length) {
			return -1;
		}
		
		int distance = 0;
		int length = types.length;
		for(int i=0; i<length; i++) {
			int dis = getDistance(types[i], args[i].getClass());
			if(dis < 0) {
				return -1;
			}
			distance += dis*dis;
		}
		return distance;
	}

	private static int getDistance(Class<?> parameterType, Class<?> objectType) {
		parameterType = Types.toBoxClass(parameterType); 
		if(parameterType.isAssignableFrom(objectType) == false) {
			return -1;
		}
		List<Class> interfaces = new ArrayList<>();
		interfaces.add(objectType);
		
		int dis = 0;
		while(interfaces.isEmpty() == false) {
			List<Class> other = new ArrayList<>();
			for(Class type : interfaces) {
				if(type == parameterType) {
					return dis;
				}
				for(Class it : type.getInterfaces()) {
					other.add(it);
				}
				if(type.isInterface() == false) {
					Class base = type.getSuperclass();
					if(base != null) {
						other.add(base);
					}
				}
			}
			interfaces = other;
			dis++;
		}
		return -1;
	}
	
}
