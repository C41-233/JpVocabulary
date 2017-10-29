package logic.indexes;

public final class IndexManager {

	public static final IIndexManager Character = new IndexManagerImplement("conf/index/characters.index"); 
	public static final IIndexManager Hiragana = new IndexManagerImplement("conf/index/hiraganas.index");
	public static final IIndexManager Adjective = new IndexManagerImplement("conf/index/adjectives.index");
	
}
