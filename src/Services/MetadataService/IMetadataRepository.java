package Services.MetadataService;

import Metadata.Common.IEntity;
import Utilities.Pattern;

public interface IMetadataRepository {

	IEntity Get(Pattern metadataRequest);

}
