package logic.indexes;

public interface IIndexManager {

	public boolean isValidIndex(String index);

	public Iterable<IIndexGroup> getGroups();

	public String getFirst();

}
