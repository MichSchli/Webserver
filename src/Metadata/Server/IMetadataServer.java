package Metadata.Server;

import Metadata.Common.IEntity;
import Utilities.Pattern;

public interface IMetadataServer {

	IEntity Get(Pattern metadataRequest);

}
