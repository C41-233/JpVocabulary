package core.config;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import c41.core.Core;

public final class XmlReader {

	@SuppressWarnings("unchecked")
	public static <T> T read(File file, Class<T> cl){
		try {
			JAXBContext ctx = JAXBContext.newInstance(cl);
			Unmarshaller unmarshaller = ctx.createUnmarshaller();
			return (T) unmarshaller.unmarshal(file);
		}
		catch (Exception e) {
			throw Core.throwException(e);
		}
	}
	
}
