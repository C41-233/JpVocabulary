package LinqTemplateBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public final class GroupFileProcess {

	public static ArrayList<HashMap<String, String>> readGroups(File configFile){
		ArrayList<HashMap<String, String>> groups = new ArrayList<>();
		
		try(Scanner scanner = new Scanner(configFile)){
			while(scanner.hasNext()) {
				String line = scanner.nextLine().trim();
				if(line.isEmpty()) {
					continue;
				}
				if(line.equals("[section]")) {
					HashMap<String, String> group = readGroup(scanner);
					groups.add(group);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return groups;
	}

	private static HashMap<String, String> readGroup(Scanner scanner) throws IOException {
		HashMap<String, String> group = new HashMap<>();
		while(scanner.hasNext()) {
			String line = scanner.nextLine().trim();
			if(line.isEmpty()) {
				continue;
			}
			if(line.equals("[/section]")) {
				break;
			}
			
			String[] pair = line.split("=", 2);
			if(pair.length != 2) {
				throw new IOException();
			}
			group.put(pair[0].trim(), pair[1].trim());
		}
		return group;
	}
	
}
