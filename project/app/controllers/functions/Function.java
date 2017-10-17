package controllers.functions;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;

import base.utility.Charsets;
import base.utility.linq.Linq;
import core.config.Config;
import core.controller.AjaxControllerBase;
import core.logger.Logs;

public final class Function extends AjaxControllerBase{

	public static void dump() throws IOException {
		ArrayList<String> cmds = new ArrayList<>();
		cmds.add("mysqldump");
		
		String name = Config.getString("db.default.name");
		String username = Config.getString("db.default.user");
		String password = Config.getString("db.default.pass");
		
		if(name == null) {
			Logs.Db.error("mysqldump缺少name");
			renderJsonError("mysqldump缺少name");
		}
		if(username == null) {
			Logs.Db.error("mysqldump缺少username");
			renderJsonError("mysqldump缺少username");
		}

		if(password == null) {
			Logs.Db.error("mysqldump缺少password");
			renderJsonError("mysqldump缺少password");
		}
		
		cmds.add("-u"+username);
		cmds.add("-p"+password);
		
		cmds.add("--add-drop-table");
		cmds.add("--hex-blob");
		
		cmds.add(name);

		Logs.Db.info(String.join(" ", cmds));
		
		Process process = Runtime.getRuntime().exec(cmds.toArray(new String[cmds.size()]));
		InputStream is = process.getInputStream();
		InputStream es = process.getErrorStream();
		
		boolean hasError = false;
		for(String line : Linq.from(IOUtils.readLines(es, Charsets.UTF_8))
				.where(line->line.equals("Warning: Using a password on the command line interface can be insecure.")==false)) {
			hasError = true;
			Logs.Db.error(line);
		}
		if(hasError) {
			renderJsonError("备份失败");
		}

		Path outputFolder = Config.getPath("dump.path.output");
		if(outputFolder == null) {
			Logs.Db.error("缺少备份输出目录配置");
			renderJsonError("备份失败");
		}
		if(Files.exists(outputFolder)==false || Files.isDirectory(outputFolder)==false) {
			Logs.Db.error("备份输出目录不存在: %s", outputFolder);
			renderJsonError("备份失败");
		}
		
		Path backupFolder = Config.getPath("dump.path.backup");
		if(backupFolder == null) {
			Logs.Db.error("缺少备份归档目录配置");
			renderJsonError("备份失败");
		}
		if(Files.exists(backupFolder)==false || Files.isDirectory(backupFolder)==false) {
			Logs.Db.error("备份归档目录不存在: %s", backupFolder);
			renderJsonError("备份失败");
		}
		
		Path outputFile = outputFolder.resolve("data.sql");
		Path backupFile = backupFolder.resolve(DateTime.now().toString("yyyy-MM-dd-HHmmss")+".sql");
		if(Files.exists(outputFile)) {
			Files.move(outputFile, backupFile);
		}
		Files.copy(is, outputFile);
		
	}
	
}
