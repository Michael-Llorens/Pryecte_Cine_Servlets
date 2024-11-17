package CINE_V3_CompraNEntrades_Servidor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor implements Runnable{

	int portEscolta;
	Cine cine;
	
	public Servidor(int port, Cine c) {
		this.portEscolta = port;
		this.cine = c;
		System.out.println("[SERVIDOR CINE]\t\tEscoltant en el port "+portEscolta);
		System.out.println("[SERVIDOR CINE]\t\tEsperant solicituts de clients");
	}

	@Override
	public void run() {
		int i = 0;
		Socket socketClient = null;
		FilCompraNEntrades fil = null;
		
		try {
			// Creacio del serverSocket
			ServerSocket serverSocket = new ServerSocket(portEscolta);

			while (true){

				// lanzaremo sel hilo filCompraNEntrades que esperara les peticions dels cliens
				socketClient = serverSocket.accept();
				System.out.println("["+Thread.currentThread().getName()+"]\t\tinicia (FilCompraNEntrades"+i+")");

				fil = new FilCompraNEntrades(socketClient, i++, cine);
				fil.start();


			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
