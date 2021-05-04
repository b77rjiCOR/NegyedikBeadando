package tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TCPThread implements Runnable {
	
	private Socket s;
	private int id;
	private static List<Socket> socketlist = new CopyOnWriteArrayList<>();
	
	public TCPThread(Socket s,int id) 
	{
		this.s = s;
		this.id = id;
		socketlist.add(s);
	}

	@Override
	public void run() {
		
		DataInputStream dout;
		try {
			System.out.println("[SERVER] Client("+id+") Connected");
			DataOutputStream msg = new DataOutputStream(s.getOutputStream());
			msg.writeUTF("[CLIENT] Your Client Number is "+id);
			dout = new DataInputStream(s.getInputStream());
			while(true) 
			{
				 String line = dout.readUTF();
				 System.out.println("client("+id+"):"+line);
				 MessageSender(line);
				 if(line.equalsIgnoreCase("bye"))
				 {
					 System.out.println("[SERVER] Client("+id+") Disconnected");
					 break;
				 }
			}
			dout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void MessageSender(String line) throws IOException 
	{
		for (Socket other : socketlist) 
		{
			if(other == s) 
			{
				continue;
			}
			DataOutputStream output = new DataOutputStream(other.getOutputStream());
			output.writeUTF("client("+id+"): "+line);
		}
	}

}
