package logic.indexes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;

import base.io.LineReader;
import base.io.RuntimeIOException;
import base.utility.linq.Linq;

class IndexManagerImplement implements IIndexManager{

	public IndexManagerImplement(String filename) {
		this.file = new File(filename);
		if(file.exists() == false || file.isFile() == false) {
			throw new RuntimeIOException(new FileNotFoundException());
		}
	}
	
	private final File file;
	
	private final ArrayList<IIndexGroup> cache = new ArrayList<>();
	private final HashSet<String> indexSet = new HashSet<>();
	
	private long lastTime = 0;
	
	@Override
	public Iterable<IIndexGroup> getGroups(){
		tryLoad();
		return cache;
	}
	
	@Override
	public boolean isValidIndex(String index) {
		tryLoad();
		return indexSet.contains(index);
	}

	@Override
	public String getFirst() {
		tryLoad();
		return Linq.from(cache).selectMany(group->group.getItems()).first();
	}
	
	private void tryLoad(){
		long modified = file.lastModified();
		if(modified <= lastTime){
			return;
		}
		lastTime = modified;
		
		cache.clear();
		indexSet.clear();
		
		try(LineReader scanner = new LineReader(file.toPath(), LineReader.Trim | LineReader.SkipEmpty)){
			IndexGroup last = null;
			String line;
			while((line = scanner.readLine())!=null){
				if(line.startsWith("[") && line.endsWith("]")){
					last = new IndexGroup();
					cache.add(last);
					
					last.name = line.substring(1, line.length()-1);
				}
				else{
					last.items.add(line);
					indexSet.add(line);
				}
			}
		}
	}

}
