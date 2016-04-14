package Utilities.IO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import com.google.gson.Gson;
import Utilities.Serialization.ISerializableEntity;

public class JsonFileHandler implements IFileHandler {

	private IFileHandler _fileHandler;
	private Gson gson;

	public JsonFileHandler(IFileHandler fileHandler) {
		this._fileHandler = fileHandler;
		gson = new Gson();
	}

	@Override
	public List<String> readLines(String filename) throws FileNotFoundException {
		return _fileHandler.readLines(filename);
	}

	@Override
	public List<List<String>> readSegments(String filename) throws FileNotFoundException {
		return _fileHandler.readSegments(filename);
	}

	@Override
	public <T extends ISerializableEntity> T readJson(String filename, Class<T> type) throws FileNotFoundException {
		BufferedReader reader = new BufferedReader(new FileReader(filename));

		T cms = gson.fromJson(reader, type);
		return cms;
	}

}
