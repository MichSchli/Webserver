package Server;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import Services.Common.IRequestHandler;
import Utilities.Request.Request;
import Utilities.Request.RequestFactory;
import Utilities.Response.IResponse;

public class RequestProcessor implements Runnable {
 
	private Socket socket;
	private IRequestHandler requestHandler;

	public RequestProcessor(Socket socket, IRequestHandler requestHandler) {
		this.socket = socket;
		this.requestHandler = requestHandler;
	}

	@Override
	public void run() {
		try {
			processRequest();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processRequest() throws Exception {
		InputStream inStream = socket.getInputStream();
		OutputStream outStream = socket.getOutputStream();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
		
		Request request = RequestFactory.ReadFromBufferedReader(br);
		
		IResponse response = requestHandler.Process(request);
		
		response.WriteToStream(outStream);
        br.close(); // Close the input stream
        socket.close(); // Close the socket itself
	}
	
}
