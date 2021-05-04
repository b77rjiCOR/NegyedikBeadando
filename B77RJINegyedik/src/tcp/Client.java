package tcp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

	public static void main(String[] args) throws IOException {
		Socket s = new Socket("localhost",1234);
		System.out.println("[CLIENT] Your Client Started");
		DataInputStream msg = new DataInputStream(s.getInputStream());
		System.out.println(msg.readUTF());
		DataOutputStream dout = new DataOutputStream(s.getOutputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("[CLIENT] Just Type Away Your Message: ");
		
		DataInputStream inp = new DataInputStream(new BufferedInputStream(s.getInputStream()));
		new Thread(() -> {
			while(true) {
				String str;
				try {
					str = inp.readUTF();
					System.out.println(str);
				} catch (IOException e) {
					e.printStackTrace();
					break;
				}
			}
		}, "Client Reveiver.").start();
		
		while(true) 
		{
			
			String line = br.readLine();
			dout.writeUTF(line);
			if(line.equalsIgnoreCase("bye"))
			{
				System.out.println("[CLIENT] Disconnected!");
				break;
			}
		}
		s.close();
		
	}

}
