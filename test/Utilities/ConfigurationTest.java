package Utilities;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;


import org.junit.Assert;
import org.junit.Test;

import Utilities.Configuration.ConfigurationReader;
import Utilities.Configuration.IConfiguration;
import Utilities.Configuration.IConfigurationMapper;
import Utilities.Configuration.ConfigurationException;
import Utilities.IO.IFileHandler;
import Utilities.Cast.CastException;
import Utilities.Cast.ICastHandler;

import org.mockito.Mockito;

public class ConfigurationTest {


	public class TestConfig implements IConfiguration{
		public String TestField;
		public int TestIntField;
	}
	
	@Test
	public void Read_EmptyConfigurationFile() throws ConfigurationException{
		IFileHandler f = Mockito.mock(IFileHandler.class);
		IConfigurationMapper m = Mockito.mock(IConfigurationMapper.class);
		ICastHandler c = Mockito.mock(ICastHandler.class);
		ConfigurationReader reader = new ConfigurationReader(f,m,c);
		
		String testConfigPath = "testpath";
		Mockito.when(f.readSegments(testConfigPath)).thenReturn(new ArrayList<List<String>>());
		
		List<IConfiguration> configurations = reader.readConfigurationFile(testConfigPath);
		
		Assert.assertNotNull(configurations);
		Assert.assertEquals(0, configurations.size());
	}
	
	@Test
	public void Read_HeaderMapped() throws ConfigurationException{
		IFileHandler f = Mockito.mock(IFileHandler.class);
		IConfigurationMapper m = Mockito.mock(IConfigurationMapper.class);
		ICastHandler c = Mockito.mock(ICastHandler.class);
		ConfigurationReader reader = new ConfigurationReader(f,m,c);
		
		String testConfigPath = "testpath";
		ArrayList<List<String>> configs = new ArrayList<List<String>>();
		ArrayList<String> config = new ArrayList<String>();
		configs.add(config);
		
		config.add("[TestConfig]");

		Mockito.when(m.mapHeader("TestConfig")).thenReturn(new TestConfig());
		Mockito.when(f.readSegments(testConfigPath)).thenReturn(configs);
		
		List<IConfiguration> configurations = reader.readConfigurationFile(testConfigPath);

		Assert.assertNotNull(configurations);
		Assert.assertEquals(1, configurations.size());
		Assert.assertNotNull(configurations.get(0));
		Assert.assertEquals(new TestConfig().getClass(), configurations.get(0).getClass());
	}
	
	@Test
	public void Read_MultipleHeaders() throws ConfigurationException{
		IFileHandler f = Mockito.mock(IFileHandler.class);
		IConfigurationMapper m = Mockito.mock(IConfigurationMapper.class);
		ICastHandler c = Mockito.mock(ICastHandler.class);
		ConfigurationReader reader = new ConfigurationReader(f,m,c);
		
		String testConfigPath = "testpath";
		ArrayList<List<String>> configs = new ArrayList<List<String>>();
		ArrayList<String> config = new ArrayList<String>();
		ArrayList<String> config2 = new ArrayList<String>();

		config.add("[TestConfig]");
		config2.add("[TestConfig]");
		
		configs.add(config);
		configs.add(config2);
		
		Mockito.when(m.mapHeader("TestConfig")).thenReturn(new TestConfig());
		Mockito.when(f.readSegments(testConfigPath)).thenReturn(configs);
		
		List<IConfiguration> configurations = reader.readConfigurationFile(testConfigPath);

		Assert.assertNotNull(configurations);
		Assert.assertEquals(2, configurations.size());
		Assert.assertNotNull(configurations.get(0));
		Assert.assertNotNull(configurations.get(1));
		Assert.assertEquals(new TestConfig().getClass(), configurations.get(0).getClass());
		Assert.assertEquals(new TestConfig().getClass(), configurations.get(1).getClass());
	}
	
	@Test
	public void Read_FillsOutFields() throws ConfigurationException{
		IFileHandler f = Mockito.mock(IFileHandler.class);
		IConfigurationMapper m = Mockito.mock(IConfigurationMapper.class);
		ICastHandler c = Mockito.mock(ICastHandler.class);
		ConfigurationReader reader = new ConfigurationReader(f,m,c);
		
		String testConfigPath = "testpath";
		ArrayList<List<String>> configs = new ArrayList<List<String>>();
		ArrayList<String> config = new ArrayList<String>();
		configs.add(config);
		
		config.add("[TestConfig]");
		config.add("TestField	foo");

		Mockito.when(m.mapHeader("TestConfig")).thenReturn(new TestConfig());
		Mockito.when(f.readSegments(testConfigPath)).thenReturn(configs);
		
		List<IConfiguration> configurations = reader.readConfigurationFile(testConfigPath);

		Assert.assertNotNull(configurations);
		Assert.assertEquals(1, configurations.size());
		Assert.assertNotNull(configurations.get(0));
		Assert.assertEquals(new TestConfig().getClass(), configurations.get(0).getClass());
		Assert.assertEquals("foo", ((TestConfig)configurations.get(0)).TestField);
	}
	
	@Test
	public void Read_FillsOutNonStringFields() throws ConfigurationException, CastException{
		IFileHandler f = Mockito.mock(IFileHandler.class);
		IConfigurationMapper m = Mockito.mock(IConfigurationMapper.class);
		ICastHandler c = Mockito.mock(ICastHandler.class);
		ConfigurationReader reader = new ConfigurationReader(f,m,c);
		
		String testConfigPath = "testpath";
		ArrayList<List<String>> configs = new ArrayList<List<String>>();
		ArrayList<String> config = new ArrayList<String>();
		configs.add(config);
		
		config.add("[TestConfig]");
		config.add("TestIntField	27");

		Mockito.when(m.mapHeader("TestConfig")).thenReturn(new TestConfig());
		Mockito.when(f.readSegments(testConfigPath)).thenReturn(configs);
		Mockito.when(c.cast("27", int.class)).thenReturn(27);
		
		List<IConfiguration> configurations = reader.readConfigurationFile(testConfigPath);

		Assert.assertNotNull(configurations);
		Assert.assertEquals(1, configurations.size());
		Assert.assertNotNull(configurations.get(0));
		Assert.assertEquals(new TestConfig().getClass(), configurations.get(0).getClass());
		Assert.assertEquals(27, ((TestConfig)configurations.get(0)).TestIntField);
	}
	
	@Test
	public void Read_FillsOutMultipleFields() throws ConfigurationException, CastException{
		IFileHandler f = Mockito.mock(IFileHandler.class);
		IConfigurationMapper m = Mockito.mock(IConfigurationMapper.class);
		ICastHandler c = Mockito.mock(ICastHandler.class);
		ConfigurationReader reader = new ConfigurationReader(f,m,c);
		
		String testConfigPath = "testpath";
		ArrayList<List<String>> configs = new ArrayList<List<String>>();
		ArrayList<String> config = new ArrayList<String>();
		configs.add(config);
		
		config.add("[TestConfig]");
		config.add("TestField	foo");
		config.add("TestIntField	27");

		Mockito.when(m.mapHeader("TestConfig")).thenReturn(new TestConfig());
		Mockito.when(f.readSegments(testConfigPath)).thenReturn(configs);
		Mockito.when(c.cast("27", int.class)).thenReturn(27);
		
		List<IConfiguration> configurations = reader.readConfigurationFile(testConfigPath);

		Assert.assertNotNull(configurations);
		Assert.assertEquals(1, configurations.size());
		Assert.assertNotNull(configurations.get(0));
		Assert.assertEquals(new TestConfig().getClass(), configurations.get(0).getClass());
		Assert.assertEquals(27, ((TestConfig)configurations.get(0)).TestIntField);
		Assert.assertEquals("foo", ((TestConfig)configurations.get(0)).TestField);
	}
	
	@Test
	public void Error_HeaderWrongful(){
		IFileHandler f = Mockito.mock(IFileHandler.class);
		IConfigurationMapper m = Mockito.mock(IConfigurationMapper.class);
		ICastHandler c = Mockito.mock(ICastHandler.class);
		ConfigurationReader reader = new ConfigurationReader(f,m,c);
		
		String testConfigPath = "testpath";
		ArrayList<List<String>> configs = new ArrayList<List<String>>();
		ArrayList<String> config = new ArrayList<String>();
		configs.add(config);
		
		config.add("TestConfig]");

		Mockito.when(f.readSegments(testConfigPath)).thenReturn(configs);
		
		try {
			List<IConfiguration> configurations = reader.readConfigurationFile(testConfigPath);
			fail("Exception not thrown.");
		} catch (ConfigurationException e) {
			Assert.assertEquals("Wrongful configuration syntax", e.getMessage());
		}
	}
	
	@Test
	public void Error_TwoHeadersInSegment() throws ConfigurationException{
		IFileHandler f = Mockito.mock(IFileHandler.class);
		IConfigurationMapper m = Mockito.mock(IConfigurationMapper.class);
		ICastHandler c = Mockito.mock(ICastHandler.class);
		ConfigurationReader reader = new ConfigurationReader(f,m,c);
		
		String testConfigPath = "testpath";
		ArrayList<List<String>> configs = new ArrayList<List<String>>();
		ArrayList<String> config = new ArrayList<String>();
		configs.add(config);
		
		config.add("[TestConfig]");
		config.add("[TestConfig2]");

		Mockito.when(f.readSegments(testConfigPath)).thenReturn(configs);
		Mockito.when(m.mapHeader("TestConfig")).thenReturn(new TestConfig());
		
		try {
			List<IConfiguration> configurations = reader.readConfigurationFile(testConfigPath);
			fail("Exception not thrown.");
		} catch (ConfigurationException e) {
			Assert.assertEquals("Wrongful configuration syntax", e.getMessage());
		}
	}
	
	@Test
	public void Error_NoValueForField() throws ConfigurationException{
		IFileHandler f = Mockito.mock(IFileHandler.class);
		IConfigurationMapper m = Mockito.mock(IConfigurationMapper.class);
		ICastHandler c = Mockito.mock(ICastHandler.class);
		ConfigurationReader reader = new ConfigurationReader(f,m,c);
		
		String testConfigPath = "testpath";
		ArrayList<List<String>> configs = new ArrayList<List<String>>();
		ArrayList<String> config = new ArrayList<String>();
		configs.add(config);
		
		config.add("[TestConfig]");
		config.add("TestField");

		Mockito.when(f.readSegments(testConfigPath)).thenReturn(configs);
		Mockito.when(m.mapHeader("TestConfig")).thenReturn(new TestConfig());
		
		try {
			List<IConfiguration> configurations = reader.readConfigurationFile(testConfigPath);
			fail("Exception not thrown.");
		} catch (ConfigurationException e) {
			Assert.assertEquals("Wrongful configuration syntax", e.getMessage());
		}
	}
	
	@Test
	public void Error_CannotCast() throws CastException, ConfigurationException{
		IFileHandler f = Mockito.mock(IFileHandler.class);
		IConfigurationMapper m = Mockito.mock(IConfigurationMapper.class);
		ICastHandler c = Mockito.mock(ICastHandler.class);
		ConfigurationReader reader = new ConfigurationReader(f,m,c);
		
		String testConfigPath = "testpath";
		ArrayList<List<String>> configs = new ArrayList<List<String>>();
		ArrayList<String> config = new ArrayList<String>();
		configs.add(config);
		
		config.add("[TestConfig]");
		config.add("TestIntField	foo");

		Mockito.when(f.readSegments(testConfigPath)).thenReturn(configs);
		Mockito.when(m.mapHeader("TestConfig")).thenReturn(new TestConfig());
		Mockito.when(c.cast("foo", int.class)).thenThrow(new CastException());
		
		try {
			List<IConfiguration> configurations = reader.readConfigurationFile(testConfigPath);
			fail("Exception not thrown.");
		} catch (ConfigurationException e) {
			Assert.assertEquals("Wrongful cast in configuration", e.getMessage());
		}
	}
	
	@Test
	public void Error_NoSuchConfiguration() throws ConfigurationException{
		IFileHandler f = Mockito.mock(IFileHandler.class);
		IConfigurationMapper m = Mockito.mock(IConfigurationMapper.class);
		ICastHandler c = Mockito.mock(ICastHandler.class);
		ConfigurationReader reader = new ConfigurationReader(f,m,c);
		
		String testConfigPath = "testpath";
		ArrayList<List<String>> configs = new ArrayList<List<String>>();
		ArrayList<String> config = new ArrayList<String>();
		configs.add(config);
		
		config.add("[TestConfig]");
		config.add("TestIntField	foo");

		Mockito.when(f.readSegments(testConfigPath)).thenReturn(configs);
		Mockito.when(m.mapHeader("TestConfig")).thenThrow(new ConfigurationException("Configuration not known: TestConfig"));
		
		try {
			List<IConfiguration> configurations = reader.readConfigurationFile(testConfigPath);
			fail("Exception not thrown.");
		} catch (ConfigurationException e) {
			Assert.assertEquals("Configuration not known: TestConfig", e.getMessage());
		}
	}
	
	@Test
	public void Error_NoSuchField() throws ConfigurationException{
		IFileHandler f = Mockito.mock(IFileHandler.class);
		IConfigurationMapper m = Mockito.mock(IConfigurationMapper.class);
		ICastHandler c = Mockito.mock(ICastHandler.class);
		ConfigurationReader reader = new ConfigurationReader(f,m,c);
		
		String testConfigPath = "testpath";
		ArrayList<List<String>> configs = new ArrayList<List<String>>();
		ArrayList<String> config = new ArrayList<String>();
		configs.add(config);
		
		config.add("[TestConfig]");
		config.add("NonexistingField	foo");

		Mockito.when(f.readSegments(testConfigPath)).thenReturn(configs);
		Mockito.when(m.mapHeader("TestConfig")).thenReturn(new TestConfig());
		
		try {
			List<IConfiguration> configurations = reader.readConfigurationFile(testConfigPath);
			fail("Exception not thrown.");
		} catch (ConfigurationException e) {
			Assert.assertEquals("Field not defined for TestConfig: NonexistingField", e.getMessage());
		}
	}
}
