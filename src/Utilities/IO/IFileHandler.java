package Utilities.IO;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public interface IFileHandler {

	public List<String> readLines(String filename) throws FileNotFoundException;

	public List<List<String>> readSegments(String filename) throws FileNotFoundException;

}
