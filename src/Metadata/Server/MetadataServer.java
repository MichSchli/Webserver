package Metadata.Server;

import Metadata.Common.IEntity;
import Metadata.Common.IRepository;
import Metadata.Images.IImageRepository;
import Metadata.Images.JsonImageRepository;
import Utilities.Pattern;
import Utilities.IO.IFileHandler;

public class MetadataServer implements IMetadataServer, IRepository {

	private IImageRepository imageRepository;

	public MetadataServer(IFileHandler fileHandler) {
		imageRepository = new JsonImageRepository(fileHandler); 
	}
	
	@Override
	public IEntity Get(Pattern metadataRequest) {
		return imageRepository.Get(metadataRequest);
	}

}
