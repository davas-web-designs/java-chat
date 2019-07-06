

import javax.swing.*;

import java.awt.*;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoServer mimarco=new MarcoServer();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	}	
}

class MarcoServer extends JFrame implements Runnable{
	
	public MarcoServer(){
		
		setBounds(1200,300,280,350);				
			
		JPanel milamina= new JPanel();
		
		milamina.setLayout(new BorderLayout());
		
		areatexto=new JTextArea();
		
		milamina.add(areatexto,BorderLayout.CENTER);
		
		add(milamina);
		
		setVisible(true);

		Thread t = new Thread(this);

		t.start();
		
		}
	
	private	JTextArea areatexto;

	public void run(){

		//System.out.println("Im listening");

		try {
			ServerSocket server = new ServerSocket(9999);

			String nick, ip, message;

			SendObject objectRetrieved;

			while(true){
				Socket s = server.accept();

				ObjectInputStream input_stream = new ObjectInputStream(s.getInputStream());
	
				objectRetrieved = (SendObject) input_stream.readObject();

				nick = objectRetrieved.getNick();
				ip = objectRetrieved.getIp();
				message = objectRetrieved.getMessage();

				areatexto.append("\n" + nick + " : " + message + " for: " + ip);

				s.close();
			}

			

		} catch (IOException | ClassNotFoundException e) {
			//TODO: handle exception
			e.printStackTrace();
		}
		

	}
}
