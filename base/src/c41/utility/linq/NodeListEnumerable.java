package c41.utility.linq;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class NodeListEnumerable implements IReferenceEnumerable<Node>{

	private final NodeList nodes;
	
	public NodeListEnumerable(NodeList nodes) {
		this.nodes = nodes;
	}

	@Override
	public IEnumerator<Node> iterator() {
		return new Enumerator();
	}

	private final class Enumerator extends EnumeratorBase<Node>
	{

		private int index = -1;
		
		@Override
		public boolean hasNext() {
			return index + 1 < nodes.getLength();
		}

		@Override
		public void doMoveNext() {
			index++;
		}

		@Override
		public Node doCurrent() {
			return nodes.item(index);
		}
		
	}
	
}
