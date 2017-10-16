package controllers.functions;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import base.utility.Charsets;
import core.config.Config;
import core.controller.AjaxControllerBase;
import core.logger.Log;

public final class Function extends AjaxControllerBase{

	public static void dump() throws IOException {
		ArrayList<String> cmds = new ArrayList<>();
		cmds.add("mysqldump");
		
		String username = Config.getString("db.default.user");
		String password = Config.getString("db.default.pass");
		
		if(username == null) {
			Log.Function.error("mysqldump缺少username");
			return;
		}

		if(password == null) {
			Log.Function.error("mysqldump缺少password");
			return;
		}
		
		cmds.add("-u"+username);
		cmds.add("-p"+password);
		
		cmds.add("--add-drop-table");
		cmds.add("--hex-blob");
		
		Process process = Runtime.getRuntime().exec(cmds.toArray(new String[cmds.size()]));
		InputStream is = process.getInputStream();
		InputStream es = process.getErrorStream();
		List<String> inLines = IOUtils.readLines(is, Charsets.UTF_8);
		List<String> erLines = IOUtils.readLines(es, Charsets.UTF_8);
		
		for(String line : erLines) {
			Log.Function.error(line);
		}
		
	}
	
}
