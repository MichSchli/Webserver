package Metadata.Common;

import Utilities.Pattern;

public interface IRepository {
	public IEntity Get(Pattern metadataRequest);
}
