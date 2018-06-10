package apps.pi;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class MainServer {

	public final static int port = 1882;

	public static void main(String[] args) {
		System.out.println("Hello world!");
		Thread t = new Thread(new Runnable(){

			public void run(){

				try {
					//Cr�ation de la connexion c�t� serveur, en sp�cifiant un port d'�coute
					DatagramSocket server = new DatagramSocket(port);

					while(true){
						//On s'occupe maintenant de l'objet paquet
						byte[] buffer = new byte[8192];
						DatagramPacket packet = new DatagramPacket(buffer, buffer.length);



						//Cette m�thode permet de r�cup�rer le datagramme envoy� par le client
						//Elle bloque le thread jusqu'� ce que celui-ci ait re�u quelque chose.
						server.receive(packet);



						//nous r�cup�rons le contenu de celui-ci et nous l'affichons

						String str = new String(packet.getData());
						System.out.print("Re�u de la part de " + packet.getAddress() 
						+ " sur le port " + packet.getPort() + " : ");
						System.out.println(str);



						//On r�initialise la taille du datagramme, pour les futures r�ceptions
						packet.setLength(buffer.length);

						//et nous allons r�pondre � notre client, donc m�me principe
						byte[] buffer2 = new String("R�ponse du serveur � " + str + "! ").getBytes();

						DatagramPacket packet2 = new DatagramPacket(
								buffer2,             //Les donn�es 
								buffer2.length,      //La taille des donn�es
								packet.getAddress(), //L'adresse de l'�metteur
								packet.getPort()     //Le port de l'�metteur

								);
						//Et on envoie vers l'�metteur du datagramme re�u pr�c�demment
						server.send(packet2);
						packet2.setLength(buffer2.length);

					}

				} catch (SocketException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		});  



		//Lancement du serveur

		t.start();
	}

}
