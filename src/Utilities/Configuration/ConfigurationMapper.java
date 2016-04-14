package Utilities.Configuration;

import Services.ImageService.ImageServiceConfiguration;
import Services.Redirection.RedirectionServiceConfiguration;

public class ConfigurationMapper implements IConfigurationMapper {

	@Override
	public IConfiguration mapHeader(String string) throws ConfigurationException {
		switch (string) {
		case "RedirectionService":
			return new RedirectionServiceConfiguration();
		case "ImageService":
			return new ImageServiceConfiguration();
		default:
			throw new ConfigurationException("Configuration not known: "+string);
		}
	}

}
