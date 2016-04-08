package Services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import Utilities.Configuration.ConfigurationReader;
import Utilities.Configuration.IConfiguration;
import Utilities.Configuration.IConfigurationMapper;
import Utilities.IO.IFileHandler;
import Utilities.Cast.ICastHandler;

import org.mockito.Mockito;

public class ConfigurationServiceTest {


	public class TestConfig implements IConfiguration{
		public String TestField;
		public int TestIntField;
	}
	
	@Test
	public void Read_EmptyConfigurationFile(){
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
	public void Read_HeaderMapped(){
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
	public void Read_MultipleHeaders(){
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
	public void Read_FillsOutFields(){
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
	public void Read_FillsOutNonStringFields(){
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
	public void Read_FillsOutMultipleFields(){
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
}
