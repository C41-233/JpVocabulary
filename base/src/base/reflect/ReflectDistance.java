package base.reflect;

import java.util.ArrayList;
import java.util.List;

final class ReflectDistance {

	public static int getDistance(Class<?>[] types, Object[] args) {
		if(types.length != args.length) {
			return -1;
		}
		
		int distance = 0;
		int length = types.length;
		for(int i=0; i<length; i++) {
			if(args[i] == null) {
				distance += 1;
				continue;
			}
			int dis = getDistance(types[i], args[i].getClass());
			if(dis < 0) {
				return -1;
			}
			distance += dis*dis;
		}
		return distance;
	}

	private static class DistanceGroup{
		public final Class<?> cl;
		public final int distance;
		public DistanceGroup(Class<?> cl, int distance) {
			this.cl = cl;
			this.distance = distance;
		}
	}
	
	private static int getDistance(Class<?> parameterType, Class<?> objectType) {
		parameterType = Types.toBoxClass(parameterType); 
		if(parameterType.isAssignableFrom(objectType) == false) {
			return -1;
		}
		
		List<DistanceGroup> groups = new ArrayList<>();
		groups.add(new DistanceGroup(objectType, 0));
		
		while(!groups.isEmpty()) {
			List<DistanceGroup> backup = new ArrayList<>();
			for(DistanceGroup group : groups) {
				if(group.cl == parameterType) {
					return group.distance;
				}
				if(group.cl.isInterface()) {
					for(Class interfaze : group.cl.getInterfaces()) {
						backup.add(new DistanceGroup(interfaze, group.distance + 2));
					}
				}
				else {
					for(Class interfaze : group.cl.getInterfaces()) {
						backup.add(new DistanceGroup(interfaze, group.distance + 1));
					}
					Class base = group.cl.getSuperclass();
					if(base != null) {
						backup.add(new DistanceGroup(base, group.distance + 2));
					}
				}
			}
			groups = backup;
		}
		return -1;
	}

	
}
