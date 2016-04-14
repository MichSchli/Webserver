package Utilities.Response;

import java.io.OutputStream;
import java.io.PrintWriter;

import Utilities.Serialization.ISerializableEntity;

public class JsonResponse implements IResponse{

	private ISerializableEntity entity;

	public JsonResponse(ISerializableEntity entity) {
		this.entity = entity;
	}

	@Override
	public void WriteToStream(OutputStream stream) {
		PrintWriter out = new PrintWriter(stream);

		out.println("HTTP/1.1 200"); // Version & status code
        out.println("Content-Type: text/plain"); // The type of data
        out.println(); // End of headers

        out.print(entity.serialize());
                
        out.flush();
	}

}
