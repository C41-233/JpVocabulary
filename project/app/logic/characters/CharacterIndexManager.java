package logic.characters;

import java.io.File;
import java.util.ArrayList;

import base.io.LineReader;
import base.utility.linq.Linq;

public final class CharacterIndexManager{

	private CharacterIndexManager() {}
	
	private static final File file = new File("conf/index/characters.index");
	
	private static ArrayList<ICharacterIndexGroup> cache = new ArrayList<>();
	private static long lastTime = 0;
	
	public static Iterable<ICharacterIndexGroup> getCache(){
		tryLoad();
		return cache;
	}
	
	public static boolean isValidIndex(String index) {
		tryLoad();
		return Linq.from(cache).selectMany(group->group.getItems()).isExist(item->item.equals(index));
	}
	
	private static void tryLoad(){
		long modified = file.lastModified();
		if(modified <= lastTime){
			return;
		}
		lastTime = modified;
		tryLoad();
		
		cache.clear();
		
		try(LineReader scanner = new LineReader(file.toPath(), LineReader.Trim | LineReader.SkipEmpty)){
			CharacterIndexGroup last = null;
			String line;
			while((line = scanner.readLine())!=null){
				if(line.startsWith("[") && line.endsWith("]")){
					last = new CharacterIndexGroup();
					cache.add(last);
					
					last.name = line.substring(1, line.length()-1);
				}
				else{
					last.items.add(line);
				}
			}
		}
	}
	
}
