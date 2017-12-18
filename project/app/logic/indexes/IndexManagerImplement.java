package logic.indexes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;

import base.core.Core;
import base.io.LineReader;

class IndexManagerImplement implements IIndexManager{

	public IndexManagerImplement(String filename) {
		this.file = new File(filename);
		if(file.exists() == false || file.isFile() == false) {
			Core.throwException(new FileNotFoundException());
		}
	}
	
	private final File file;
	
	private final ArrayList<IIndexGroup> cache = new ArrayList<>();
	private final HashSet<String> indexSet = new HashSet<>();
	private final ArrayList<String> indexList = new ArrayList<>();
	
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
		return indexList.get(0);
	}
	
	private void tryLoad(){
		long modified = file.lastModified();
		if(modified <= lastTime){
			return;
		}
		lastTime = modified;
		
		cache.clear();
		indexSet.clear();
		indexList.clear();
		
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
					if(last != null) {
						last.items.add(line);
					}
					indexSet.add(line);
					indexList.add(line);
				}
			}
		}
	}

	@Override
	public Iterable<String> all() {
		tryLoad();
		return indexList;
	}

}
