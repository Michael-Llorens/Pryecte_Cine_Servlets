package CINE_V3_CompraNEntrades_Servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class FilCompraNEntrades extends Thread {

	//ATRIBUTS
	private Pelicula pelicula;
	private Cine cine;
	private Sessio sessio;
	private Sala sala;
	
	private Socket socket;

	//CONSTRUCTOR
	public FilCompraNEntrades(Socket socketClient, int num, Cine cine) {;
		this.cine = cine;
		this.socket = socketClient;
		this.setName("filCompraEntradesClient"+num);
	}

	//METODES
	// ----------
	public void run() {	
		String cadenaAEnviar = cine.getPelicules().size()+"º";
		String cadenaAMostrar;
		
		try {
			//Flux de lectura
			InputStream in = socket.getInputStream();
			// Gestionat per un DataInputStream
			DataInputStream inStream = new DataInputStream(in);
			
			//Flux de escritura
			OutputStream ou = socket.getOutputStream();
			// Gestionat per un DataOuputStream
			DataOutputStream ouStream = new DataOutputStream(ou);

			//Si NO hi ha PELICULES, s'ix del procés de compra
			if (cine.getPelicules().size() == 0) {
				System.out.println("<"+ Thread.currentThread().getName() +">"+"\t\tERROR Cine:compraEntrades: No hi ha PELICULES");
				return;
			}

			for (int i = 0; i < cine.getPelicules().size(); i++) {
				cadenaAEnviar +=  (i+1) + "-> "+cine.getPelicules().get(i).toString()+"\n\n";
			}

			ouStream.writeUTF(cadenaAEnviar);
			System.out.println("\n<"+ Thread.currentThread().getName() +">"+"\t\tCadena enviada al client\n"+cadenaAEnviar);

			System.out.println("\n<"+ Thread.currentThread().getName() +">"+"\t\tAnem a recibir quina peli ha elegit");
			String cadenaRebuda = inStream.readUTF();


			int peliSelec = Integer.parseInt(cadenaRebuda.split("/")[1]);
			System.out.println("<"+ Thread.currentThread().getName() +">"+"\t\tLa pelicula escolida per el client es: "+(peliSelec+1));
			System.out.println("<"+ Thread.currentThread().getName() +">"+"\t\tEl client ha seleccionat la pelicuala " + (peliSelec+1));
			System.out.println(cine.getPelicules().get(peliSelec));

			System.out.println("\n<"+ Thread.currentThread().getName() +">"+"\t\tLlista actual de SESSIONS de la PEL·LICULA:");

			Pelicula peli = cine.getPelicules().get(peliSelec);

			if (peli.getSessionsPeli().size() == 0) {
				System.out.println("<"+ Thread.currentThread().getName() +">"+"\t\tERROR Cine:compraEntradaPelicula: No hi ha SESSIONS per a esta PELICULA");
				return;
			}

			cadenaAEnviar = peli.getSessionsPeli().size()+"º";
			for (int i = 0; i < peli.getSessionsPeli().size(); i++) {
				//System.out.println(peli.getSessionsPeli().get(i).toString());
				cadenaAEnviar += (i+1) + "-> "+peli.getSessionsPeli().get(i).toString()+"\n\n";
			}
			ouStream.writeUTF(cadenaAEnviar);
			System.out.println("<"+ Thread.currentThread().getName() +">"+"\t\tCadena enviada al client\n"+cadenaAEnviar);

			System.out.println("\n<"+ Thread.currentThread().getName() +">"+"\t\tAnem a recibir quina SECIO de la pelicula ha elegit");
			cadenaRebuda = inStream.readUTF();

			int sesioSelec = Integer.parseInt(cadenaRebuda.split("/")[1]);
			System.out.println("\n<"+ Thread.currentThread().getName() +">"+"\t\tEl client ha seleccionat la sesio " + (sesioSelec+1));

			System.out.println("\n<"+ Thread.currentThread().getName() +">"+"\t\tLa sessio te els seguens seients");
			sessio = peli.getSessionsPeli().get(sesioSelec);
			sessio.mapaSessio();

			Seient[][] seiens = sessio.getSeients();
			int seiensLliures = 0;

			for (int i = 0; i < seiens.length; i++) {
				for (int j = 0; j < seiens[i].length; j++) {
					if (seiens[i][j].getDisponibilitat() != Seient.Estat.OCUPAT)
					seiensLliures++;
				}
			}
			System.out.println("\n<"+ Thread.currentThread().getName() +">"+"\t\tNian "+ seiensLliures+ " Seiens lliures");
			ouStream.write(seiensLliures);

			System.out.println("\n<"+ Thread.currentThread().getName() +">"+"\t\tRecibim la cantitat de entrades seleccionades ");
			int entrades = inStream.read();
			System.out.println("\n<"+ Thread.currentThread().getName() +">"+"\t\tEl client vol "+entrades+" entrades");

			System.out.println("\n<"+ Thread.currentThread().getName() +">"+"\t\tEnviem les files i columnes que te la sala");
			ouStream.write(seiens.length);
			// ArrayList de la quantitat de seients reservats que es volen comprar
			ArrayList<Seient> seientsAcomprar = new ArrayList<Seient>();

			this.sala = sessio.getSala();

			//		//Per a cada entrada, tracta de reservar els seients
			for (int i=0; i < entrades; i++) {
				boolean lliure = false;
				do {
					cadenaAEnviar = "Entrada "+(i+1)+"-> Selecciona una fila y una columna";
					cadenaAEnviar += sessio.getMapaSessio() + "\n\n";

					ouStream.writeUTF(cadenaAEnviar);
					System.out.println("\n<"+ Thread.currentThread().getName() +">"+"\t\tDemanem la fila i la columna de la entrada "+(i+1));
					cadenaRebuda = inStream.readUTF();
					//System.out.println(cadenaRebuda);
					int fila = Integer.parseInt(cadenaRebuda.split("º")[0]);
					int columna = Integer.parseInt(cadenaRebuda.split("º")[1]);

					if (!seientsAcomprar.contains(sessio.getSeients()[fila-1][columna-1])) {
						if(sessio.getSeients()[fila-1][columna-1].verificaSeient()) {
							seientsAcomprar.add(cine.reservaEntradaFil(peli, sessio, sala, seientsAcomprar,fila,columna));
							lliure = true;
							cadenaAEnviar = "SI";
						}else {
							cadenaAEnviar = "No";
							System.out.println("\n<"+ Thread.currentThread().getName() +">"+"\t\tSeleccione atra volta el seient ya que esta ocupat");
						}
					}else {
						cadenaAEnviar = "NO";
						System.out.println("\n<"+ Thread.currentThread().getName() +">"+"\t\tEl seient esta repetit seleccione un altre");
					}

					ouStream.writeUTF(cadenaAEnviar);

				}while (!lliure);
			}

			System.out.println("\n<"+ Thread.currentThread().getName() +">"+"\t\tEl client va a reservar "+seientsAcomprar.size()+" seiens");

			//Una vegada reservades, es demana el pagament

			System.out.println("\n<"+ Thread.currentThread().getName() +">"+"\t\tAnem a enviarli el preu de les entrades");


			cadenaAEnviar = String.valueOf(new BigDecimal(seientsAcomprar.size()).multiply(sessio.getPreu()));
			ouStream.writeUTF(cadenaAEnviar);

			System.out.println("\n<"+ Thread.currentThread().getName() +">"+"\t\tRecibim el pagament del client");
			cadenaRebuda = inStream.readUTF();
			System.out.println(cadenaRebuda);
			if (cadenaRebuda.equals("SI")) {
				//Pagament EXITOS, cal OCUPAR els Seients i traure les entrades
				cine.ocupaSeients(seientsAcomprar);

				cadenaAEnviar = "";
				for (int i=0; i < entrades; i++) {
					sessio.imprimirTicket(seientsAcomprar.get(i), sessio, sala, peli);
					cadenaAEnviar += "\tENTRADA "+(i+1)+ "\n"+sessio.getimprimirTicket(seientsAcomprar.get(i), sessio, sala, peli);
					System.out.println();
				}//for

				ouStream.writeUTF(cadenaAEnviar);

			}else {
				//Pagament ERRONI, cal ALLIBERAR els Seients
				System.err.println("<"+Thread.currentThread().getName()+">"
						+ "\tERROR: NO sha pogut fer la compra de "+entrades+" entrades. Es queden Lliures");
				cine.alliberaSeients(seientsAcomprar);
			}

			cadenaAEnviar = sessio.getMapaSessio();

			ouStream.writeUTF(cadenaAEnviar);
			
		} catch (Exception e) {
			System.out.println(e);
		}

	}


	//GETTERS & SETTERS
	public Pelicula getPelicula() {
		return pelicula;
	}

	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}

	public Sessio getSessio() {
		return sessio;
	}

	public void setSessio(Sessio sessio) {
		this.sessio = sessio;
	}
}// class
