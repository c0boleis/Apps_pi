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
					//Création de la connexion côté serveur, en spécifiant un port d'écoute
					DatagramSocket server = new DatagramSocket(port);

					while(true){
						//On s'occupe maintenant de l'objet paquet
						byte[] buffer = new byte[8192];
						DatagramPacket packet = new DatagramPacket(buffer, buffer.length);



						//Cette méthode permet de récupérer le datagramme envoyé par le client
						//Elle bloque le thread jusqu'à ce que celui-ci ait reçu quelque chose.
						server.receive(packet);



						//nous récupérons le contenu de celui-ci et nous l'affichons

						String str = new String(packet.getData());
						System.out.print("Reçu de la part de " + packet.getAddress() 
						+ " sur le port " + packet.getPort() + " : ");
						System.out.println(str);



						//On réinitialise la taille du datagramme, pour les futures réceptions
						packet.setLength(buffer.length);

						//et nous allons répondre à notre client, donc même principe
						byte[] buffer2 = new String("Réponse du serveur à " + str + "! ").getBytes();

						DatagramPacket packet2 = new DatagramPacket(
								buffer2,             //Les données 
								buffer2.length,      //La taille des données
								packet.getAddress(), //L'adresse de l'émetteur
								packet.getPort()     //Le port de l'émetteur

								);
						//Et on envoie vers l'émetteur du datagramme reçu précédemment
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
