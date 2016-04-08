package Utilities.IO;

import java.util.ArrayList;
import java.util.List;

public interface IFileHandler {

	public List<String> readLines(String filename);

	public List<List<String>> readSegments(String filename);

}
