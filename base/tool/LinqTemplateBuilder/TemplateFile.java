package LinqTemplateBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TemplateFile{

	private static class Fragment{
		
		public boolean isParameter;
		public String value;
		
		public Fragment(boolean isParameter, String value) {
			this.isParameter = isParameter;
			this.value = value;
		}
	}
	
	private final List<Fragment> fragments = new ArrayList<>();
	private final StringBuilder sb = new StringBuilder();
	
	public TemplateFile(File file) throws IOException{
		try(FileInputStream is = new FileInputStream(file)) {
			InputStreamReader reader = new InputStreamReader(is, "utf8");
			readTemplate(reader);
		} catch (UnsupportedEncodingException e) {
			//
		}
	}

	private void readTemplate(InputStreamReader reader) throws IOException {
		int step = 0;
		int nread;
		while((nread = reader.read()) != -1) {
			char ch = (char)nread;
			switch (step) {
			//read text
			case 0:
				if(ch == '$') {
					step = 2;
					addFragment(false);
				}
				else {
					sb.append(ch);
				}
				break;
			//read parameter
			case 1:
				if(ch == '}') {
					step = 0;
					addFragment(true);
				}
				else {
					sb.append(ch);
				}
				break;
			//read $
			case 2:
				if(ch == '{') {
					step = 1;
				}
				else {
					step = 0;
				}
				break;
			default:
				break;
			}
		}
		if(sb.length() > 0) {
			switch (step) {
			case 0:
				addFragment(false);
				break;
			default:
				break;
			}
		}
	}
	
	private void addFragment(boolean isParameter) {
		if(sb.length() > 0) {
			fragments.add(new Fragment(isParameter, sb.toString()));
		}
		sb.delete(0, sb.length());
	}
	
	public String render(Map<String, String> pairs) {
		StringBuilder sb = new StringBuilder();
		for (Fragment fragment : fragments) {
			if(fragment.isParameter) {
				String value = pairs.get(fragment.value);
				if(value != null) {
					sb.append(value);
				}
			}
			else {
				sb.append(fragment.value);
			}
		}
		return sb.toString();
	}
	
}
