package Metadata.Images;

import Metadata.Common.IEntity;

public class Image implements IEntity {

	public String Filepath;
	public String Name;
	public String Description;
	
	@Override
	public String serialize() {
		return "{\n"
				+ "\t\"Filepath\": \""+Filepath+"\",\n"
				+ "\t\"Name\": \""+Name+"\",\n"
				+ "\t\"Description\": \""+Description+"\"\n"
				+ "}";
	}

}
