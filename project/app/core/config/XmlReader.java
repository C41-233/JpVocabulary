package core.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import c41.core.Core;

public final class XmlReader {

	@SuppressWarnings("unchecked")
	public static <T> T read(File file, Class<T> cl){
		try(InputStreamReader is = new InputStreamReader(new FileInputStream(file), "utf-8")) {
			JAXBContext ctx = JAXBContext.newInstance(cl);
			Unmarshaller unmarshaller = ctx.createUnmarshaller();
			return (T) unmarshaller.unmarshal(is);
		}
		catch (Exception e) {
			throw Core.throwException(e);
		}
	}
	
}
