package Utilities.Configuration;

import Services.ImageService.ImageServiceConfiguration;
import Services.MetadataService.MetadataServiceConfiguration;
import Services.Redirection.RedirectionServiceConfiguration;

public class ConfigurationMapper implements IConfigurationMapper {

	@Override
	public IConfiguration mapHeader(String string) throws ConfigurationException {
		switch (string) {
		case "RedirectionService":
			return new RedirectionServiceConfiguration();
		case "ImageService":
			return new ImageServiceConfiguration();
		case "MetadataService":
			return new MetadataServiceConfiguration();
		default:
			throw new ConfigurationException("Configuration not known: "+string);
		}
	}

}
