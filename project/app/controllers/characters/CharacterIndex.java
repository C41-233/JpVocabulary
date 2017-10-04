package controllers.characters;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import core.controller.HtmlControllerBase;

public final class CharacterIndex extends HtmlControllerBase{

	public static void index(){
		page("ai");
	}
	
	public static void page(String index){
		List<CharacterIndexVO> indexes = new ArrayList<CharacterIndexVO>();
		
		try(Scanner scanner = new Scanner(new File("conf/index/characters.index"))){
			CharacterIndexVO last = null;
			while(scanner.hasNext()){
				String line = scanner.nextLine().trim();
				if(line.isEmpty()){
					continue;
				}
				else if(line.startsWith("[") && line.endsWith("]")){
					last = new CharacterIndexVO();
					indexes.add(last);
					
					last.name = line.substring(1, line.length()-1);
				}
				else{
					last.items.add(line);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		renderArgs.put("indexes", indexes);
		render("characters/index");
	}

	@SuppressWarnings("unused")
	private static class CharacterIndexVO {
		String name;
		List<String> items = new ArrayList<String>();
	}
	
}
