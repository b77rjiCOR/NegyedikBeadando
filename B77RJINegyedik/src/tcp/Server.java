package tcp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException {
		
		ServerSocket ss = new ServerSocket(1234);
		int clientcount=0;
		System.out.println("[SERVER] The Server is Running");
		while (true) 
		{
			clientcount++;
			new Thread(new TCPThread(ss.accept(),clientcount)).start();
		}
	}

}
