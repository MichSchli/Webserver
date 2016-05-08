import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import Server.WebserverApplicationInstaller;
import Server.RequestProcessor;
import Configuration.ConfigurationException;

public class Listener {
	
	public static void main(String[] args) throws IOException, ConfigurationException {
		ServerSocket welcomeSocket = new ServerSocket(9001);
		
		WebserverApplicationInstaller installer = new WebserverApplicationInstaller(args[0]);
						
		while(true){
			Socket connectionSocket = welcomeSocket.accept();
			
			System.out.println("accepted");
			
			RequestProcessor requestProcessor = new RequestProcessor(connectionSocket, installer.GetNewRequestHandler());
			Thread thread = new Thread(requestProcessor);
			thread.start();
			
			System.out.println("started thread, listening again...");
		}
	}
}
