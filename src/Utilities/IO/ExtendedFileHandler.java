package Utilities.IO;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import Utilities.Serialization.ISerializableEntity;

public class ExtendedFileHandler implements IFileHandler {

	private IFileHandler _fileHandler;

	public ExtendedFileHandler(IFileHandler fileHandler) {
		this._fileHandler = fileHandler;
	}

	@Override
	public List<String> readLines(String filename) throws FileNotFoundException {
		return _fileHandler.readLines(filename);
	}

	@Override
	public List<List<String>> readSegments(String filename) throws FileNotFoundException {
		ArrayList<List<String>> list = new ArrayList<List<String>>();
		list.add(new ArrayList<String>());

		for (String line : _fileHandler.readLines(filename)) {
			String stripped = line.trim();

			List<String> latest = list.get(list.size() - 1);
			if (stripped.isEmpty()) {
				if (!latest.isEmpty()) {
					list.add(new ArrayList<String>());
				}
			} else {
				latest.add(stripped);
			}
		}

		return list;
	}

	@Override
	public <T extends ISerializableEntity> T readJson(String filename, Class<T> type) throws FileNotFoundException {
		throw new UnsupportedOperationException();
	}

}
