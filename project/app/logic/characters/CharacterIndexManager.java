package logic.characters;

import java.io.File;
import java.util.ArrayList;

import base.io.LineReader;

public final class CharacterIndexManager{

	private CharacterIndexManager() {}
	
	private static final File file = new File("conf/index/characters.index");
	
	private static ArrayList<ICharacterIndexGroup> cache = new ArrayList<>();
	private static long lastTime = 0;
	
	public static Iterable<ICharacterIndexGroup> getCache(){
		long modified = file.lastModified();
		if(modified > lastTime){
			lastTime = modified;
			load();
		}
		return cache;
	}
	
	private static void load(){
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
