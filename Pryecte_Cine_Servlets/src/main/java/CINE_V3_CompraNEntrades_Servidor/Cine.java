package CINE_V3_CompraNEntrades_Servidor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

import CINE_V3_CompraNEntrades_Servidor.Seient.Estat;


public class Cine {
	private String nom;
	private ArrayList<Sala> sales;
	private ArrayList<Sessio> sessions;
	private ArrayList<Pelicula> pelicules;



	//Constructor 1
	public Cine(String nomCine) {
		nom = nomCine;
		//Creacio dels ArrayList buits
		sales = new ArrayList<Sala>();
		sessions = new ArrayList<Sessio>();
		setPelicules(new ArrayList<Pelicula>());
	}


	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		int opcio=-1;
		int sala, sessio, pelicula;
		int numFil = 1; 	//comptador de fils compraEntrada llançats

		Cine cine = new Cine("KINEPOLIS");

		System.out.println("+---------------------------------------------------+");
		System.out.println("|   CINE "+ cine.nom + " v3.0 Compra N Entrades amb fils  |");
		System.out.println("+---------------------------------------------------+");

		//carrega de dades Inicials per poder testejar programa
		cine.carregaDadesInicials(cine);

		do{
			//mostra menu() i captura opció escolllida
			opcio = cine.menu();

			//en funcio opció, es realitzen unes accions o altres
			switch(opcio){

			case 1: //Crear SALA
				System.out.println("Creant SALA...");
				sala = 0;
				boolean validatIdSala = false;

				System.out.println("Creació de la SALA\n>>>>>>>>>>>>>>>>>>>>>");
				//valida que el número de la SALA no existisca en la BD 
				do{
					sala = Validacio.validaSencer("\tNumero de la Sala? [0 -> Cancel·lar acció]:",10);

					if (cine.validaIdSala(sala)) validatIdSala = true;
					else System.out.println("\tERROR: Numero de SALA existent");
				} while (!validatIdSala);

				if(sala == 0)	//Si s'anula accio, acaba
					System.out.println("\tAnul·lada acció");
				else {			//validat el numero de SALA
					//Numero de Sala validat i inexistent, crea la nova SALA
					Sala sa = new Sala(sala);
					System.out.println(sa);
					//Afegeix la nova SALA al llistat de Sales
					cine.sales.add(sa);
				}
				System.out.println("\n\n");
				break;

				//********

			case 2: //Modificar SALA
				System.out.println("Modificant SALA...");
				//Si NO hi ha sales, es finalitza modificació
				if(cine.sales.size()==0) 
					System.out.println("ERROR Modifica SALA: No hi ha Sales a modificar");
				else{ //Hi ha sales creades i modificables
					cine.llistarSales();
					//obté una SALA existent
					sala = Validacio.validaSencer("\t Tria SALA a modificar [0 -> Cancel·lar acció]:",cine.sales.size());

					if(sala == 0)	//Si s'anula accio, acaba
						System.out.println("\tAnul·lada acció");
					else
						cine.modificaSala(sala);	//Modifica la SALA
				}
				System.out.println("\n\n");
				break;
				//********

			case 3: //Esborrar SALA
				System.out.println("Esborrant SALA...");
				//Si NO hi ha sales, es finalitza esborrat
				if(cine.sales.size()==0)
					System.out.println("ERROR Esborra SALA: No hi ha Sales a esborrar");
				else{ //Hi ha sales creades i esborrables
					cine.llistarSales();
					//obté una SALA existent
					sala = Validacio.validaSencer("\t Tria SALA a esborrar [0 -> Cancel·lar acció]:",cine.sales.size());

					if(sala == 0)		//Si s'anula accio, acaba
						System.out.println("\tAnul·lada acció");
					else
						cine.esborraSala(sala);		//Esborra la SALA
				}
				System.out.println("\n\n");
				break;
				//********

			case 4: //Crear SESSIO
				System.out.println("Creant SESSIO...");

				if (cine.sales.size()!=0) { //Si hi ha SALES, es pot crear la SESSIO 
					System.out.println("Creació de la SESSIO\n>>>>>>>>>>>>>>>>>>> ");
					String nomSessio;
					boolean validatIdSessio = false;
					//valida que el Id de la SESSIO no existisca en la BD 
					do{
						nomSessio = Validacio.validaCadena("\tIndentificador de la sessió? [0 -> Cancel·lar acció]:");
						if (nomSessio.equalsIgnoreCase("0")) 		//Si s'anula accio, acaba
							validatIdSessio = true;
						else 
							validatIdSessio = cine.validaIdSessio(nomSessio);

						if (!validatIdSessio)
							System.out.println("\tERROR: Numero de SESSIO existent");
					} while (!validatIdSessio);


					if(!nomSessio.equalsIgnoreCase("0")) {		//validat el numero de SALA
						Sessio se = new Sessio(nomSessio, cine.sales);		//crea nova SESSIO
						System.out.println(se);
						cine.sessions.add(se);	
					}else {
						//Si s'anula accio, acaba
						System.out.println("\tAnul·lada acció");
					}//else
				}else {
					//No hi ha SALES per associar a la SESSIO
					System.out.println("\n\t ERROR Creació de la SESSIO: No hi ha cap SALA registrada per associar.\nCancel·lant Acció...");
				}
				System.out.println("\n\n");
				break;
				//********

			case 5: //Modifica SESSIO
				System.out.println("Modificant SESSIO...");

				if(cine.sessions.size()==0) //NO hi ha SESSIONS
					System.out.println("ERROR Modifica SESSIO: No hi ha Sessions a modificar");
				else{ //Hi ha SESSIONS creades i modificables
					cine.llistarSessions();
					//obté una SESSIONS existent
					sessio = Validacio.validaSencer("\t Tria SESSIO a modificar  [0 -> Cancel·lar acció]:",cine.sessions.size());
					if(sessio == 0)//Si s'anula accio, acaba
						System.out.println("\tAnul·lada acció");
					else
						cine.modificaSessio(sessio);		//Modifica la SESSIO
				}
				System.out.println("\n\n");
				break;
				//********

			case 6: //Esborrar SESSSIO
				System.out.println("Esborrant SESSIO...");
				if(cine.sessions.size()==0) //NO hi ha SESSIONS
					System.out.println("ERROR Esborra SESSIO: No hi ha Sessions a modificar");
				else{ //Hi ha SESSIONS creades i esborrables
					cine.llistarSessions();
					//obté una SESSIONS existent
					sessio = Validacio.validaSencer("\t Tria SESSIO a esborrar  [0 -> Cancel·lar acció]:",cine.sessions.size());
					if(sessio == 0)//Si s'anula accio, acaba
						System.out.println("\tAnul·lada acció");
					else
						cine.esborraSessio(sessio-1);		//Esborra la SESSIO
				}
				System.out.println("\n\n");
				break;
				//********

			case 7: //Crear PELICULA
				System.out.println("Creant PEL·LICULA...");

				if (cine.sessions.size()!=0) { //Si hi ha SESSIONS, es pot crear la PEL·LICULA 
					System.out.println("Creació de la PEL·LICULA\n>>>>>>>>>>>>>>>>>>> ");

					String nomPeli;
					boolean validatIdPeli = false;
					//valida que el nom de la PEL·LICULA no existisca en la BD 
					do{
						nomPeli = Validacio.validaCadena("\tNom de la Pelicula?  [0 -> Cancel·lar acció]:");
						if (nomPeli.equalsIgnoreCase("0")) 
							validatIdPeli = true;
						else 
							validatIdPeli = cine.validaNomPeli(nomPeli);

						if (!validatIdPeli)
							System.out.println("\tERROR: Nom de PEL·LICULA existent");
					} while (!validatIdPeli);

					if(!nomPeli.equalsIgnoreCase("0")) {		//validat el nom de la PEL·LICULA
						Pelicula p = new Pelicula(nomPeli, cine.obteSessionsLliures());	//crea nova PEL·LICULA
						System.out.println(p);
						cine.getPelicules().add(p);
					}else {
						//Si s'anula accio, acaba
						System.out.println("\tAnul·lada acció");
					}//else
				}else {
					//No hi ha SALES per associar a la SESSIO
					System.out.println("\n\t ERROR Creació de la PEL·LICULA: No hi ha cap SESSIO per associar.\nCancel·lant Acció...");
				}
				System.out.println("\n\n");
				break;
				//********

			case 8: //Modifica PEL·LICULA
				System.out.println("Modificant PEL·LICULA...");

				if(cine.getPelicules().size()==0) //NO hi ha pelicules
					System.out.println("ERROR Modifica PEL·LICULA: No hi ha Pelicules a modificar");
				else{ //Hi ha PEL·LICULES creades i modificables
					cine.llistarPelicules();
					//obté una PEL·LICULA existent
					pelicula = Validacio.validaSencer("\t Tria PEL·LICULA a modificar  [0 -> Cancel·lar acció]:", cine.getPelicules().size());
					if(pelicula == 0)//Si s'anula accio, acaba
						System.out.println("\tAnul·lada acció");
					else
						cine.modificaPelicula(pelicula-1);		//Modifica la PEL·LICULA
				}
				System.out.println("\n\n");
				break;
				//********

			case 9: //Esborrar PEL·LICULA
				System.out.println("Esborrant PEL·LICULA...");
				if(cine.getPelicules().size()==0) //NO hi ha pelicules
					System.out.println("ERROR Esborra PEL·LICULA: No hi ha pelicules a esborrar");
				else{ //Hi ha PEL·LICULES creades i esborrables
					cine.llistarPelicules();
					//obté una PEL·LICULA existent
					pelicula = Validacio.validaSencer("\t Tria PEL·LICULA a esborrar  [0 -> Cancel·lar acció]:", cine.getPelicules().size());
					if(pelicula == 0)
						System.out.println("Anulant acció");
					else
						cine.esborraPelicula(pelicula-1);	//Esborra la PEL·LICULA
				}
				System.out.println("\n\n");
				break;
				//********

			case 10: //Associar PEL·LICULA a SESSIO 
				System.out.println("Associant PEL·LICULA a SESSIO...");
				if (cine.getPelicules().size() == 0) { //Si NO hi ha PELICULES, s'ix
					System.out.println("\t No hi ha PELICULES per ASSOCIAR");
					System.out.println("\tAnul·lada acció");
				}

				if (cine.sessions.size() == 0 || cine.llistarSessionsLliures() == 0) {//Si NO hi ha SESSIONS, s'ix
					System.out.println("\t No hi ha SESSIONS per ASSOCIAR");
					System.out.println("\tAnul·lada acció");
				}

				cine.associaSessioAPelicula();
				System.out.println("\n\n");
				break;

			case 11: //Comprar ENTRADA
				int port = 9110;
				System.out.println("Comprant ENTRADA...");
				
				Thread servidor = new Thread(new Servidor(port, cine),"SERVIDOR CINE");
				System.out.println("Executing the SERVER thread to attend TCP requests…");
				servidor.start();
				
				servidor.join();
				
				//Thread filNou1 = new Thread(new FilCompraNEntrades("filCompraEntrada" + numFil, cine),"filCompraEntrada" + numFil++);
				//Thread filNou2 = new Thread(new FilCompraNEntrades("filCompraEntrada" + numFil, cine),"filCompraEntrada" + numFil++);
				//COMPRA ENTRADES FIL INTERACTIVA
				//===============================		
				//filNou1.start();			//llançament del fil
				//filNou2.start();			//llançament del fil
				//filNou2.join();			//espera finalitzacio del fil
				//filNou1.join();			//espera finalitzacio del fil
				break;
				//********

			default: System.out.println("Eixint CINE...\n Programa finalitzat!!!");
			System.out.println("\n\n");
			}
		}while(opcio!=0);
	}//main


	//*********************************************************
	//VISUALITZA EL MENU PRINCIPAL
	public int menu(){
		int opcio;
		Scanner s = new Scanner(System.in);

		do{
			System.out.println("MENU Aplicació CINE:");
			System.out.println("====================");
			System.out.println("1.  Crear SALA");
			System.out.println("2.  Modificar SALA");
			System.out.println("3.  Eliminar SALA");

			System.out.println("4.  Crear SESSIO");
			System.out.println("5.  Modificar SESSIO");
			System.out.println("6.  Eliminar SESSIO");

			System.out.println("7.  Crear PEL·LICULA");
			System.out.println("8.  Modificar PEL·LICULA");
			System.out.println("9.  Eliminar PEL·LICULA");

			System.out.println("10. Associar PEL·LICULA a SESSIO");
			System.out.println("11. Comprar ENTRADA");
			System.out.println("0. Eixir Aplicació CINE");

			System.out.println("\n\nIntrodueix opció de menu:");
			String stropcio = s.next();
			opcio=Integer.parseInt(stropcio);
		}while (opcio < 0 || opcio > 11);

		//s.close();
		return opcio;
	}//menu


	//*************************************************************************
	//CÀRREGA DE DADES INICIALS DEL CINE (PELIS, SALES, SESSIONS,...)
	public void carregaDadesInicials(Cine cine) {
		System.out.println("Càrrega INICIAL de DADES...");
		Sala sa1, sa2, sa3;
		cine.sales.add( sa1 = new Sala(1, true, 5, 5));
		cine.sales.add( sa2 = new Sala(2, true, 7, 7));
		cine.sales.add( sa3 = new Sala(3, false, 9, 9));

		Sessio s1, s2, s3;
		cine.sessions.add(s1 = new Sessio("sesA-sala1", 15,7,2021,21,30, sa1, new BigDecimal(6)));
		cine.sessions.add(s2 = new Sessio("sesB-sala1", 14,4,2021,22,0, sa2, new BigDecimal(4.5)));
		cine.sessions.add(s3 = new Sessio("sesC-sala1", 16,12,2021,18,45, sa3, new BigDecimal(8)));

		Pelicula p1, p2, p3;
		cine.getPelicules().add(p1 = new Pelicula("Avatar", "USA", 195,	"James Cameron", "actor1, actriu1, ...", "bla, bla, bla ...", "Ficció",	"TP",new ArrayList<Sessio>()));
		p1.setSessioPeli(s1);
		cine.getPelicules().add(p2 = new Pelicula("Gladiator", "USA", 160,	"Ridley Scott", "actor1, actriu1, ...", "bla, bla, bla ...", "Ficció",	">18",new ArrayList<Sessio>()));
		p2.setSessioPeli(s2);
		cine.getPelicules().add(p3 = new Pelicula("Regreso al futuro", "USA", 195,	"Robert Zemeckis", "actor1, actriu1, ...", "bla, bla, bla ...", "Ficció",	"TP",new ArrayList<Sessio>()));
		p3.setSessioPeli(s3);
	}//carregaDadesInicials
	
	//CÀRREGA DE DADES INICIALS DEL CINE (PELIS, SALES, SESSIONS,...)
	public void carregaDadesInicialsNoMostra(Cine cine) {

		Sala sa1, sa2, sa3;
		cine.sales.add( sa1 = new Sala(1, true, 5, 5));
		cine.sales.add( sa2 = new Sala(2, true, 7, 7));
		cine.sales.add( sa3 = new Sala(3, false, 9, 9));

		Sessio s1, s2, s3;
		cine.sessions.add(s1 = new Sessio("sesA-sala1", 15,7,2021,21,30, sa1, new BigDecimal(6)));
		cine.sessions.add(s2 = new Sessio("sesB-sala1", 14,4,2021,22,0, sa2, new BigDecimal(4.5)));
		cine.sessions.add(s3 = new Sessio("sesC-sala1", 16,12,2021,18,45, sa3, new BigDecimal(8)));

		Pelicula p1, p2, p3;
		cine.getPelicules().add(p1 = new Pelicula("Avatar", "USA", 195,	"James Cameron", "actor1, actriu1, ...", "bla, bla, bla ...", "Ficció",	"TP",new ArrayList<Sessio>()));
		p1.setSessioPeli(s1);
		cine.getPelicules().add(p2 = new Pelicula("Gladiator", "USA", 160,	"Ridley Scott", "actor1, actriu1, ...", "bla, bla, bla ...", "Ficció",	">18",new ArrayList<Sessio>()));
		p2.setSessioPeli(s2);
		cine.getPelicules().add(p3 = new Pelicula("Regreso al futuro", "USA", 195,	"Robert Zemeckis", "actor1, actriu1, ...", "bla, bla, bla ...", "Ficció",	"TP",new ArrayList<Sessio>()));
		p3.setSessioPeli(s3);
	}//carregaDadesInicials




	//---------------------
	//metode que tracta de reservar una entrada 
	// retorna SEIENT  -> si s'ha escollit un seient disponible
	// retorna NULL    -> si NO s'ha pogut reservar cap seient
	public Seient reservaEntradaFil(Pelicula pelicula, Sessio sessio, Sala sala, ArrayList<Seient> seientsAcomprar, int fila, int columna) {
		Seient seient = null;
		boolean seientEscollit = false;
		///////////inici de SYNCHRONIZED ////////////////////
		/////////////////////////////////////////////////////
		synchronized(this) {
			do {
				System.out.println("<"+ Thread.currentThread().getName() +">"+"\tSeient: ["+fila+"] ["+columna+"]");

				if (!seientsAcomprar.contains(sessio.getSeients()[fila-1][columna-1])) { //SEIENT NO repetit pel mateix fil
					
					if(sessio.getSeients()[fila-1][columna-1].verificaSeient()) {	//Si seient LLIURE o RESERVAT
						System.out.println("<"+ Thread.currentThread().getName() +"> \tSeient [" +(fila-1) + "][" + (columna-1) + "]  RESERVAT");
						seientEscollit = true;
						//afegeix seient a llista SEIENTS RESERVATS
						sessio.getSeients()[fila-1][columna-1].reservaSeient();	
					}else {
						System.out.println("<"+ Thread.currentThread().getName() +"> \tSeient [" +(fila-1) + "][" + (columna-1) + "]  OCUPAT!");
					}
				}else {
					System.out.println("<"+ Thread.currentThread().getName() +"> \tSeient [" +(fila-1) + "][" + (columna-1) + "]  REPETIT! Selecciona un NOU");
				}
			}while(!seientEscollit);
		}//synchronized
		/////////////////////////////////////////////////////
		///////////final de SYNCHRONIZED //////////////////

		seient = sessio.getSeients()[fila-1][columna-1];

		//Compra seient
		System.out.println("<"+ Thread.currentThread().getName() +"> \n\tSeient RESERVAT: Seient["+ (fila-1) +"]["+ (columna-1) +"]");
		return seient;
	}//reserva_numEntrades


	//*********************************************************
	//PAGAMENT DE N ENTRADES
	boolean pagamentEntradesFil(ArrayList<Seient> seientsAcomprar, BigDecimal preu){
		System.out.println("<"+Thread.currentThread().getName()+"> \tImport a pagar: "+new BigDecimal(seientsAcomprar.size()).multiply(preu)+" €");
		System.out.print("\n<"+Thread.currentThread().getName()+"> \tPagant");
		for (int i=0; i<3; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.print(".");
		}//for
		//Valida una vegada més si algun fil s'ha adelantat
		///////////inici de SYNCHRONIZED ////////////////////
		/////////////////////////////////////////////////////
		synchronized(this) {
			for (int i=0; i<seientsAcomprar.size();i++) {
				if(!seientsAcomprar.get(i).verificaSeient())
					return false;
			}
			return Validacio.validaBoolea("<"+Thread.currentThread().getName()+"> \t Pagat? (S/N)");
		}//synchronized
		/////////////////////////////////////////////////////
		///////////final de SYNCHRONIZED //////////////////
	}//pagamentEntradesFil


	//********************************************************
	//OCUPACIO DE N SEIENTS
	///////////inici de SYNCHRONIZED ////////////////////
	/////////////////////////////////////////////////////
	synchronized void ocupaSeients(ArrayList<Seient> seientsAcomprar) {
		for (int i=0; i < seientsAcomprar.size(); i++) {
			seientsAcomprar.get(i).ocupaSeient(); // ocupa seient
			System.out.println("<"+ Thread.currentThread().getName() +"> \n\tSeient ["+(seientsAcomprar.get(i).getFilaSeient()+ 1 )+"]["+(seientsAcomprar.get(i).getNumeroSeient() + 1)+"] OCUPAT");
		}//for
	}//ocupaSeients
	/////////////////////////////////////////////////////
	///////////final de SYNCHRONIZED //////////////////


	//********************************************************
	//ALLIBERAMENT DE N SEIENTS
	///////////inici de SYNCHRONIZED ////////////////////
	/////////////////////////////////////////////////////
	synchronized void alliberaSeients(ArrayList<Seient> seientsAcomprar) {
		for (int i=0; i < seientsAcomprar.size(); i++) {
			//Si el seient ja està ocupat per altre fil, no es pot alliberar
			if (seientsAcomprar.get(i).getDisponibilitat()!= Estat.OCUPAT) {
				seientsAcomprar.get(i).alliberaSeient(); // ocupa seient
				System.out.println("<"+ Thread.currentThread().getName() +"> \n\tSeient ["+(seientsAcomprar.get(i).getFilaSeient()+ 1)+"]["+(seientsAcomprar.get(i).getNumeroSeient() + 1)+"] ALLIBERAT");
			}
		}//for
	}//alliberaSeients
	/////////////////////////////////////////////////////
	///////////final de SYNCHRONIZED //////////////////

	//-------------------
	public boolean validaIdSala(int s){
		boolean resultat = true;
		for(int i=0; i<sales.size();i++){
			if (sales.get(i).getNumeroSala()==s) return false;
		}
		return resultat;
	}//validaIdSala


	//-------------------
	public void llistarSales(){
		if (sales.size()==0) System.out.println("\n\t No hi ha cap SALA registrada");
		for(int i=1; i<=sales.size();i++){
			System.out.print("\n\t "+i+": "+sales.get(i-1).toString());
		}
		System.out.println();
	}//llistarSales


	//-------------------
	public void modificaSala(int i){
		if (i <= sales.size())
			sales.get(i-1).modificaSala();
		else {
			System.out.println("ERROR modificaSala: valor proporcionat fora de rang");
		}
	}//modificaSala


	//-------------------
	public void esborraSala(int i){
		if (i <= sales.size()){

			sales.get(i-1).mostraSalaEsborrada();
			sales.remove(i-1);
		}else {
			System.out.println("ERROR modificaSala: valor proporcionat fora de rang");
		}
	}//esborraSala


	//*********************************************************
	//Comprova si la ESSIO ja existeix
	//Retorna TRUE: SESSIO NO existeix
	//Retorna FALSE: SESSIO ja existeix
	public  boolean validaIdSessio(String s){
		boolean resultat = true;
		for(int i=0; i<sessions.size();i++){
			if (sessions.get(i).getNomSessio().compareToIgnoreCase(s)==0) return false;
		}
		return resultat;
	}//validaIdSessio


	//*********************************************************
	//Mostra la llista de TOTES les SESSIONS
	public void llistarSessions(){
		if (sessions.size()==0) System.out.println("\n\t No hi ha cap SESSIO registrada");
		else {
			//System.out.println("\n******** SESSIONS *******");
			for(int i=1; i<=sessions.size();i++){
				System.out.println("\n\t "+i+"-> "+sessions.get(i-1).toString());
				System.out.println("\t---");
			}
			System.out.println();
		}
	}//llistarSessions


	//*********************************************************
	//Mostra per pantalla la llista de  SESSIONS NO assignades a una PEL·LICULA i retorna quantes hi han
	public int llistarSessionsLliures(){
		int numSessionsLliures=0;
		if (sessions.size()==0) System.out.println("\n\t No hi ha cap SESSIO registrada");
		else {
			//System.out.println("\n******** SESSIONS *******");
			for(int i=1; i<=sessions.size();i++){
				if (!sessions.get(i-1).isAssignadaPeli()) {//si estan lliures, es mostren
					System.out.println("\n\t "+i+"-> "+sessions.get(i-1).toString());
					System.out.println("\t---");
					numSessionsLliures++;
				}
			}
			System.out.println();
		}
		return numSessionsLliures;
	}//llistarSessionsLliures

	//*********************************************************
	//Retorna la llista de  SESSIONS NO assignades a una PEL·LICULA
	public ArrayList<Sessio> obteSessionsLliures(){
		ArrayList<Sessio> sessionsLliures = new ArrayList<Sessio>();
		if (sessions.size()==0) {System.out.println("\n\t No hi ha cap SESSIO registrada");
		}else {
			//System.out.println("\n******** SESSIONS *******");
			for(int i=1; i<=sessions.size();i++){
				if (!sessions.get(i-1).isAssignadaPeli()) {//si estan lliures, es mostren
					sessionsLliures.add(sessions.get(i-1));
				}
			}
			System.out.println();
		}
		return sessionsLliures;
	}//obteSessionsLliures




	//-------------------
	public void modificaSessio(int i){
		if (i <= sessions.size())
			sessions.get(i-1).modificaSessio(sales);
		else {
			System.out.println("ERROR modificaSessio: valor proporcionat fora de rang");
		}
	}//modificaSessio


	//*********************************************************
	//Esborra la SESSIO de la posicio i
	public  void esborraSessio(int i){
		if (i <= sessions.size()){
			Sessio s = sessions.get(i-1);
			//Eliminem qualsevol enllaç de la SESSIO al llistat de Sessions a qualsevol PEL·LICULA
			for(int j=0; j < getPelicules().size(); j++) {
				for (int k=0; k < getPelicules().get(j).getSessionsPeli().size(); k++)
					if (getPelicules().get(j).getSessionsPeli().contains(s))
						getPelicules().get(j).getSessionsPeli().remove(k);
			}

			sessions.get(i-1).mostraSessioEsborrada();
			sessions.remove(i-1);
		}else {
			System.out.println("ERROR modificaSala: valor proporcionat fora de rang");
		}
	}//esborraSessio

	//*********************************************************
	//Mostra per pantalla TOTES les PELICULES i retorna la quantitat que hi han
	public int llistarPelicules(){
		if (getPelicules().size()==0) 
			System.out.println("\n\t No hi ha cap PEL·LICULA registrada");

		for(int i=1; i<=getPelicules().size();i++){
			System.out.println("\n\t "+i+"-> "+getPelicules().get(i-1).toString());
		}
		System.out.println();
		return getPelicules().size();
	}//llistarPelicules


	//*********************************************************
	//Comprova si la PEL·LICULA ja existeix
	//Retorna TRUE: PEL·LICULA NO existeix
	//Retorna FALSE: PEL·LICULA ja existeix
	public  boolean validaNomPeli(String s){
		boolean resultat = true;
		for(int i=0; i<getPelicules().size();i++){
			if (getPelicules().get(i).getNomPeli().compareToIgnoreCase(s)==0) return false;
		}
		return resultat;
	}//validaNomPeli

	//*********************************************************
	//Modifica la PEL·LICULA que ocupa la posicio i
	public void modificaPelicula(int i){
		if (i <= getPelicules().size())
			getPelicules().get(i).modificaPelicula(obteSessionsLliures());
		else {
			System.out.println("ERROR Pelicules.modificaSessio: valor proporcionat fora de rang");
		}
	}//modificaPelicula


	//*********************************************************
	//Esborra la PEL·LICULA que ocupa la posicio i
	public void esborraPelicula(int i){
		if (i <= getPelicules().size()){
			if (getPelicules().get(i-1).getSessionsPeli().size()>0) {//Si la PEL·LICULA conté sessions, preguntem què fer
				if(Validacio.validaBoolea("\n\t La PEL·LICULA conté Sessions. Esborra igualment? (S/N):")) { //Esborrem
					getPelicules().get(i-1).mostraPeliculaEsborrada();
					getPelicules().remove(i-1);
				}else //No esborra
					System.out.println(" PEL·LICULA NO esborrada");
			}
		}else {
			System.out.println("ERROR Sales.modificaSala: valor proporcionat fora de rang");
		}
	}//esborraPelicula

	//*********************************************************
	//Associa una SESSIO a una PEL·LICULA
	public void associaSessioAPelicula() {
		Scanner s = new Scanner(System.in);
		Sessio se = null;
		Pelicula p = null;
		int numSessionsLliures;

		//Llista actual de PELICULES
		System.out.println("\n\tLlista actual de PELICULES\n\t--------------------------");
		llistarPelicules();

		int numPelicula = Validacio.validaSencer("\n\tTria una PEL·LICULA: ", getPelicules().size());
		p = getPelicules().get(numPelicula-1);

		//Llista actual de les SESSIONS de la PEL·LICULA
		System.out.println("\n\tLlistat actual de SESSIONS per la PEL·LICULA " + p.getNomPeli()+"\n\t---------------------------------------");
		p.llistarSessionsPeli();

		//Llistat de TOTES les Sessions assignables a la PEL·LICULA
		System.out.println("\n\tLlistat assignable de SESSIONS\n\t--------------------------");
		numSessionsLliures = llistarSessionsLliures();

		//obté un num de Sessio
		int numSessio = Validacio.validaSencer("\n\tTria una SESSIO del llistat de les disponibles: ", sessions.size());

		se = sessions.get(numSessio-1);		//obté la sessio

		if (p.getSessionsPeli().contains(se)){	//Si conté la PEL·LICULA a la SESSIO
			System.out.println("PEL·LICULA ja associada a la SESSIO escollida");

		} else{ //Si no estava a la llista, s'afegeix
			p.getSessionsPeli().add(se);
			se.setAssignadaPeli(true);
		}//end else
	}//associaSessioAPelicula


	public ArrayList<Pelicula> getPelicules() {
		return pelicules;
	}


	public void setPelicules(ArrayList<Pelicula> pelicules) {
		this.pelicules = pelicules;
	}
}//class


