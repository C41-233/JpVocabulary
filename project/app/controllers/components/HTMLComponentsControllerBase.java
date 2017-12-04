package controllers.components;

import java.util.ArrayList;
import java.util.List;

import base.utility.Strings;
import base.utility.linq.IReferenceEnumerable;
import base.utility.linq.Linq;
import core.controller.HtmlControllerBase;
import logic.indexes.IIndexGroup;
import logic.indexes.IIndexManager;

public abstract class HTMLComponentsControllerBase extends HtmlControllerBase{

	protected static class LeftIndexGroup{
		
		private String group;
		private String index;
		private List<GroupVO> groups = new ArrayList<>();
		private IUrlResolver resolver = s -> "";
		
		private LeftIndexGroup() {}
		
		public static LeftIndexGroup compile(String index, IIndexManager... managers) {
			LeftIndexGroup rst = new LeftIndexGroup();
			
			if(Linq.from(managers).isNotExist(manager->manager.isValidIndex(index))) {
				notFound(Strings.format("%s not found.", index));
			}
			
			rst.index = index;
			
			IReferenceEnumerable<IIndexGroup> enumerable = Linq.from(new ArrayList<>());
			for(IIndexManager manager: managers) {
				enumerable = enumerable.union(manager.getGroups());
			}
			enumerable.foreach((group, i)->{
				GroupVO vo = new GroupVO();
				rst.groups.add(vo);
				vo.name = group.getName();
				vo.seq = i;
				for(String item : group.getItems()) {
					vo.items.add(item);
					if(item.equals(index)) {
						rst.group = vo.name;
					}
				}
			});
			
			return rst;
		}
		
		public void url(IUrlResolver resolver) {
			this.resolver = resolver;
		}
		
		@FunctionalInterface
		public interface IUrlResolver{
			
			public String resolve(String index);
			
		}
		
		private static class GroupVO{
			String name;
			List<String> items = new ArrayList<>();
			int seq;
		}
		
	}

}
