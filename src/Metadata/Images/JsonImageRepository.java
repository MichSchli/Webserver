package Metadata.Images;

import java.io.FileNotFoundException;

import Metadata.Common.IEntity;
import Utilities.Pattern;
import Utilities.IO.IFileHandler;

public class JsonImageRepository implements IImageRepository {

	private IFileHandler fileHandler;

	public JsonImageRepository(IFileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}
	
	@Override
	public Image Get(Pattern metadataRequest) {
		try {
			System.out.println("Get metadata: "+metadataRequest.next.toString());
			return fileHandler.<Image>readJson("C:/Users/Michael/Desktop/"+metadataRequest.next.toString(), Image.class);
		} catch (FileNotFoundException e) {
			return null;
		}
	}

}
