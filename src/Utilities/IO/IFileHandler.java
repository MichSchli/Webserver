package Utilities.IO;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import Metadata.Common.IEntity;
import Utilities.Serialization.ISerializableEntity;

public interface IFileHandler {

	public List<String> readLines(String filename) throws FileNotFoundException;

	public List<List<String>> readSegments(String filename) throws FileNotFoundException;

	public <T extends ISerializableEntity> T readJson(String filename, Class<T> type) throws FileNotFoundException;
}
