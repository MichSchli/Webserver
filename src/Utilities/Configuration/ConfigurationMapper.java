package Utilities.Configuration;

import Services.Redirection.RedirectionServiceConfiguration;

public class ConfigurationMapper implements IConfigurationMapper {

	@Override
	public IConfiguration mapHeader(String string) throws ConfigurationException {
		switch (string) {
		case "RedirectionService":
			return new RedirectionServiceConfiguration();

		default:
			throw new ConfigurationException("Configuration not known: "+string);
		}
	}

}
