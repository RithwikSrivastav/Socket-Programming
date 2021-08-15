import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

class QuoteService{
	Map<String,String> productInfo = new HashMap<String,String>();
	public QuoteService() {
		productInfo.put("a", "100");
		productInfo.put("b", "200");
	}
	public String getQuote(String product) {
		return productInfo.get(product);
	}
}
class ServiceThread extends Thread{
	Socket sock;
	QuoteService quoteservice = new QuoteService();
	public ServiceThread(Socket sock) {
		this.sock=sock;
	}
	public void run() {
		try {
			InputStream in = sock.getInputStream();
			OutputStream out = sock.getOutputStream();
			
			System.out.println("Waiting for product information");
			
			byte[] request = new byte[1024];
			in.read(request);
			String product = new  String(request).trim();
			 
			System.out.println("The name of the product is: "+ product);
			
			String price = quoteservice.getQuote(product);
			if(price==null) {
			price= "Invalid product name";
			}
			out.write(price.getBytes());
			sock.close();
			
		}
		catch(Exception e) {}
	}
}
public class Server {

	public static void main(String[] args) throws IOException {
		ServerSocket serSocket =new ServerSocket(9999); //So this ServerSocket enables us to accept the client requests
		QuoteService quoteService = new QuoteService();
		
		System.out.println("Started listening to Port 9999");
		while(true) {// this is an infinite loop so that the server is always open for other clients
			System.out.println("Waiting for client");
			
			Socket sock = serSocket.accept(); //waiting for client
			new ServiceThread(sock).start();
			 
		}
	}
}
