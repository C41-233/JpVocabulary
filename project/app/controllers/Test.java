package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import core.controller.AjaxControllerBase;
import logic.words.KatakanaWordsLogic;
import po.KatakanaWordContext;
import po.KatakanaWordType;

public class Test extends AjaxControllerBase{

	public static void test() throws IOException {
		File outFile = new File("E:/Downloads/katakana.txt");
		Scanner scanner = new Scanner(outFile, "utf-8");
		
		while(scanner.hasNext()) {
			String value = scanner.nextLine();
			List<String> meanings = new ArrayList<>();
			{
				int size = Integer.parseInt(scanner.nextLine());
				for(int i=0; i<size; i++) {
					meanings.add(scanner.nextLine());
				}
			}
			List<KatakanaWordType> types = new ArrayList<>();
			{
				int size = Integer.parseInt(scanner.nextLine());
				for(int i=0; i<size; i++) {
					types.add(KatakanaWordType.valueOf(scanner.nextLine()));
				}
			}
			String alias = scanner.nextLine();
			KatakanaWordContext context = KatakanaWordContext.valueOf(scanner.nextLine());
			
			scanner.nextLine();
			
			KatakanaWordsLogic.create(value, meanings, types, alias, context);
		}
		
		scanner.close();
	}
	
	
}
