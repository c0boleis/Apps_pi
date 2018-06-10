package apps.pi;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient implements Runnable{

	String name = "";
	long sleepTime = 1000;
	int port = 1882;

	public UDPClient(String pName, long sleep){
		name = pName;
		sleepTime = sleep;
	}



	public void run(){
		int nbre = 0;
		while(true){
			String envoi = name + "-" + (++nbre);
			byte[] buffer = envoi.getBytes();

			try {

				//On initialise la connexion côté client
				DatagramSocket client = new DatagramSocket();

				//On crée notre datagramme
				InetAddress adresse = InetAddress.getByName("192.169.0.26");
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length, adresse, port);


				//On lui affecte les données à envoyer
				packet.setData(buffer);

				//On envoie au serveur
				client.send(packet);

//				//Et on récupère la réponse du serveur
//				byte[] buffer2 = new byte[8196];
//				DatagramPacket packet2 = new DatagramPacket(buffer2, buffer2.length, adresse, port);
//
//				client.receive(packet2);
//				print(envoi + " a reçu une réponse du serveur : ");
//				println(new String(packet2.getData()));


				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}



			} catch (SocketException e) {
				e.printStackTrace();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}     
	public static synchronized void print(String str){
		System.out.print(str);
	}

	public static synchronized void println(String str){
		System.err.println(str);
	}

}   