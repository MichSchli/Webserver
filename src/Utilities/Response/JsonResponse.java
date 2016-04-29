package Utilities.Response;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import Utilities.Serialization.ISerializableEntity;
import database.query.sort.IDbSort;

public class JsonResponse implements IResponse{

	private Stream<String> entities;

	public JsonResponse(Stream<String> entities) {
		this.entities = entities;
	}

	@Override
	public void WriteToStream(OutputStream stream) {
		PrintWriter out = new PrintWriter(stream);

		out.println("HTTP/1.1 200"); // Version & status code
        out.println("Content-Type: application/json"); // The type of data
        out.println(); // End of headers

        out.println("[");
        String jsonString = entities.collect(Collectors.joining(",\n"));
        out.println(jsonString);
        out.println("]");
                
        out.flush();
	}

}
