import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import java.util.Scanner;

public class Client {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket sock= new Socket("127.0.0.1",9999);
		System.out.println("Connected to server");
		
		System.out.println("Enter product name");
		Scanner sc = new Scanner(System.in);
		String product = sc.nextLine();
		
		
		InputStream in = sock.getInputStream();
		OutputStream out = sock.getOutputStream();
		
		out.write(product.getBytes());
		
		byte[] response = new byte[1024];
		in.read(response);
		System.out.println("Message from server: "+ new String(response).trim());
		
		sock.close();

	}

}
