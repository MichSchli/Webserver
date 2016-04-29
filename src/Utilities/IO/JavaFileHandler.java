package Utilities.IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Utilities.Serialization.ISerializableEntity;

public class JavaFileHandler implements IFileHandler {

	@Override
	public List<String> readLines(String filename) throws FileNotFoundException {
		Scanner s = new Scanner(new File(filename));
		ArrayList<String> list = new ArrayList<String>();
		while (s.hasNextLine()){
		    list.add(s.nextLine());
		}
		s.close();
		return list;
	}

	@Override
	public List<List<String>> readSegments(String filename) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T extends ISerializableEntity> T readJson(String filename, Class<T> type) throws FileNotFoundException {
		throw new UnsupportedOperationException();
	}

}
