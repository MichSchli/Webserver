package ServerStack.Response;

import java.io.OutputStream;

public interface IResponse {

	public void WriteToStream(OutputStream stream);
}
