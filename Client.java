

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

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
		}	
	
}

class LaminaMarcoClient extends JPanel{
	
	public LaminaMarcoClient(){

		nick = new JTextField(5);

		add(nick);
	
		JLabel texto=new JLabel("-CHAT-");
		
		add(texto);

		ip = new JTextField(8);

		add(ip);

		textarea = new JTextArea(12, 20);

		add(textarea);
	
		message=new JTextField(20);
	
		add(message);		
	
		sendButton=new JButton("Enviar");

		SendText event = new SendText();

		sendButton.addActionListener(event);
		
		add(sendButton);	
		
	}

	private class SendText implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			//System.out.println(message.getText());
			try {
				Socket s = new Socket("192.168.0.15", 9999);

				SendObject data = new SendObject();

				data.setNick(nick.getText());
				data.setIp(ip.getText());
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
		
	private JTextField message, nick, ip;
	
	private JButton sendButton;

	private JTextArea textarea;
	
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