

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.*;

import javax.swing.*;


public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoClient mimarco=new MarcoClient();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}

class MarcoClient extends JFrame{
	
	public MarcoClient(){
		
		setBounds(600,300,280,350);
				
		LaminaMarcoClient milamina=new LaminaMarcoClient();
		
		add(milamina);
		
		setVisible(true);

		addWindowListener(new SendFlag());
		}	
	
}

class SendFlag extends WindowAdapter{

	public void windowOpened(WindowEvent e){

		try {
			Socket s = new Socket("192.168.0.15", 9090);

			SendObject data = new SendObject();

			data.setMessage("online");

			ObjectOutputStream output_stream = new ObjectOutputStream(s.getOutputStream());

			output_stream.writeObject(data);

			s.close();

		} catch (Exception e1) {
			//TODO: handle exception
			e1.getStackTrace();
		}

	}

}

class LaminaMarcoClient extends JPanel implements Runnable{
	
	public LaminaMarcoClient(){

		String nick_user = JOptionPane.showInputDialog("Nick: ");

		nick = new JLabel();

		nick.setText(nick_user);

		add(nick);
	
		JLabel texto=new JLabel("-CHAT-");
		
		add(texto);

		ip = new JComboBox();

		add(ip);

		textarea = new JTextArea(12, 20);

		add(textarea);
	
		message=new JTextField(20);
	
		add(message);		
	
		sendButton=new JButton("Enviar");

		SendText event = new SendText();

		sendButton.addActionListener(event);
		
		add(sendButton);

		Thread h = new Thread(this);

		h.start();
		
	}

	private class SendText implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			textarea.append("\n" + message.getText());

			//System.out.println(message.getText());
			try {
				Socket s = new Socket("192.168.0.15", 9090);

				SendObject data = new SendObject();

				data.setNick(nick.getText());
				data.setIp(ip.getSelectedItem().toString());
				data.setMessage(message.getText());

				ObjectOutputStream outputStream = new ObjectOutputStream(s.getOutputStream());
				outputStream.writeObject(data);
				s.close();

			} catch (UnknownHostException e1) {
				//TODO: handle exception
				e1.printStackTrace();
			} catch (IOException e1){
				System.out.println(e1.getMessage());
				e1.printStackTrace();
			}
			

		}
	}		
		
	private JTextField message;

	private JComboBox ip;
	
	private JLabel nick;

	private JButton sendButton;

	private JTextArea textarea;
	
	public void run(){
		try {
			
			ServerSocket client_server = new ServerSocket(9090);

			Socket client;

			SendObject objectRetrieved;

			while(true){
				client = client_server.accept();

				ObjectInputStream input_stream = new ObjectInputStream(client.getInputStream());

				objectRetrieved = (SendObject) input_stream.readObject();

				textarea.append("\n" + objectRetrieved.getNick() + " : " + objectRetrieved.getMessage());
			}

		} catch (Exception e) {
			//TODO: handle exception
			e.getMessage();
		}
	}

}

class SendObject implements Serializable{
	private String nick, ip, message;

	public String getNick(){
		return nick;
	}

	public void setNick(String nick){
		this.nick = nick;
	}

	public String getIp(){
		return ip;
	}

	public void setIp(String ip){
		this.ip = ip;
	}

	public String getMessage(){
		return message;
	}

	public void setMessage(String message){
		this.message = message;
	}
}