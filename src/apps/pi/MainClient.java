package apps.pi;

public class MainClient {

	public final static int port = 1882;

	public static void main(String[] args) {
		System.out.println("Hello world!");
		Thread t = new Thread(new UDPClient("UDP_CLient", 500));  
		//Lancement du client
		t.start();
	}

}
