package Utilities.IO;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class ExtendedFileHandlerTest {
	
	@Test
	public void ReadSegments_ReadsCorrectly() throws FileNotFoundException{
		IFileHandler f = Mockito.mock(IFileHandler.class);
		ExtendedFileHandler handler = new ExtendedFileHandler(f);
		
		List<String> lines = new ArrayList<String>();

		lines.add("abc");
		lines.add("def");
		lines.add("");
		lines.add("qrst");
		lines.add(" ");
		lines.add("");
		lines.add("ty");
		
		Mockito.when(f.readLines("name")).thenReturn(lines);
		
		List<List<String>> expected = new ArrayList<List<String>>();
		expected.add(new ArrayList<String>());
		expected.add(new ArrayList<String>());
		expected.add(new ArrayList<String>());
		
		expected.get(0).add("abc");
		expected.get(0).add("def");
		expected.get(1).add("qrst");
		expected.get(2).add("ty");
		
		Assert.assertEquals(expected, handler.readSegments("name"));
	}
}
