package Utilities.Configuration;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import Services.Redirection.RedirectionServiceConfiguration;
import Utilities.Configuration.ConfigurationReaderTest.TestConfig;

public class ConfigurationMapperTest {

	@Test
	public void Error_UnknownConfiguration(){
		ConfigurationMapper mapper = new ConfigurationMapper();
		try {
			mapper.mapHeader("NoSuchConfig");
			fail("No exception thrown");
		} catch (ConfigurationException e) {
			Assert.assertEquals("Configuration not known: NoSuchConfig", e.getMessage());
		}
	}
	
	@Test
	public void MapHeader_RedirectionService() throws ConfigurationException{
		ConfigurationMapper mapper = new ConfigurationMapper();
		IConfiguration config = mapper.mapHeader("RedirectionService");
		Assert.assertEquals(new RedirectionServiceConfiguration().getClass(), config.getClass());
	}
}
