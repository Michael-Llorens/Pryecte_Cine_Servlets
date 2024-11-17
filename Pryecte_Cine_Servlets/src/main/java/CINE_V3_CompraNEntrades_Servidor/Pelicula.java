package CINE_V3_CompraNEntrades_Servidor;
import java.util.ArrayList;
import java.util.Scanner;

public class Pelicula {

	private String nomPeli;
	private String nacionalitat;
	private int duracio;
	private String director;
	private String interprets;
	private String argument;
	private String genere;
	private String classificacio;
	private ArrayList<Sessio> sessionsPeli;


	//Constructor 1
	public Pelicula(String nomPeli) {

		this.nomPeli = nomPeli;
		this.sessionsPeli = null;
	}


	//Constructor 2
	public Pelicula(String nomPeli, String nacionalitat, int duracio,
			String director, String interprets, String argument, String genere,
			String classificacio) {

		this.nomPeli = nomPeli;
		this.nacionalitat = nacionalitat;
		this.duracio = duracio;
		this.director = director;
		this.interprets = interprets;
		this.argument = argument;
		this.genere = genere;
		this.classificacio = classificacio;
		this.sessionsPeli = new ArrayList<Sessio>();
	}


	//Constructor 3
	public Pelicula(String nomPeli, String nacionalitat, int duracio,
			String director, String interprets, String argument, String genere,
			String classificacio, ArrayList<Sessio> sessionsPeli) {

		this.nomPeli = nomPeli;
		this.nacionalitat = nacionalitat;
		this.duracio = duracio;
		this.director = director;
		this.interprets = interprets;
		this.argument = argument;
		this.genere = genere;
		this.classificacio = classificacio;
		this.sessionsPeli = sessionsPeli;
	}


	//Constructor INTERACTIU
	public Pelicula(String nomPeli, ArrayList<Sessio> sessionsLliures) {
		Scanner s = new Scanner(System.in);
		int numSessio;
		Sessio se = null;


		this.nomPeli = nomPeli;
		this.nacionalitat = Validacio.validaCadena("\tNacionalitat de la PEL·LICULA? ");
		this.duracio = Validacio.validaSencer("\tDuracio de la PEL·LICULA?(min) ",200);
		this.director = Validacio.validaCadena("\tDirector de la PEL·LICULA? ");
		this.interprets = Validacio.validaCadena("\tInterprets de la PEL·LICULA? ");
		this.argument = Validacio.validaCadena("\tArgument de la PEL·LICULA? ");
		this.genere = Validacio.validaCadena("\tGenere de la PEL·LICULA? ");
		this.classificacio = Validacio.validaCadena("\tClassificació de la PEL·LICULA? ");

		//Creacio d'una nova llista de SESSIONS per a la PEL·LICULA
		this.sessionsPeli = new ArrayList<Sessio>();

		if (sessionsLliures.size()==0) 
			System.out.println("\n\t No hi ha cap SESSIO registrada");
		else {
			//Hi ha SESSIONS lliures per assignar a la PEL·LICULA;
			// Llista TOTES les SESSIONS disponibles
			for(int i=1; i<=sessionsLliures.size();i++){
				System.out.println("\n\t "+i+"-> "+sessionsLliures.get(i-1).toString());
				System.out.println("\t---");
			}
			System.out.println();
			//Eleccio de la SESSIO
			numSessio = Validacio.validaSencer("\tIndentificador de sessio per la PEL·LICULA?", sessionsLliures.size());

			// Associa una SESSIO triada a la PEL·LICULA
			se = sessionsLliures.get((numSessio)-1);
			sessionsPeli.add(se);
			//Marca que la SESSIO ja té associada una PEL·LICULA
			se.setAssignadaPeli(true);
		}
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		//s.close();
	}


	// ---------------------------------
	//modificacio PEL·LICULA
	//Funcio que modifica el contingut dels atributs de la PEL·LICULA
	public void modificaPelicula(ArrayList<Sessio> sessionsLliures) {
		Scanner s = new Scanner(System.in);
		int numSessio;
		Sessio se = null;
		String accio;
		System.out.println(">>>>>>>>>>>>>>>>>>>>>");
		System.out.println("\tModificació de la PEL·LICULA\n>>>>>>>>>>>>>>>>>>>>>");
		System.err.println("\tPrem tecla INTRO per matenir informació anterior ");

		this.nomPeli = Validacio.validaCadenaDefecte("\tNom de la PEL·LICULA? ",this.getNomPeli());

		this.nacionalitat = Validacio.validaCadenaDefecte("\tNacionalitat de la PEL·LICULA? ", this.getNacionalitat());

		this.duracio = Validacio.validaSencerDefecte("\tDuracio de la PEL·LICULA?(min) ",200, this.getDuracio());

		this.director = Validacio.validaCadenaDefecte("\tDirector de la PEL·LICULA? ", this.getDirector());

		this.interprets = Validacio.validaCadenaDefecte("\tInterprets de la PEL·LICULA? ", this.getInterprets());

		this.argument = Validacio.validaCadenaDefecte("\tArgument de la PEL·LICULA? ",this.getArgument());

		this.genere = Validacio.validaCadenaDefecte("\tGenere de la PEL·LICULA? ",this.getGenere());

		this.classificacio = Validacio.validaCadenaDefecte("\tClassificació de la PEL·LICULA? ",this.getClassificacio());

		//Llistar les SESSIONS de la PEL·LICULA
		System.out.println("\n\tLlista actual de SESSIONS per la PEL·LICULA:");
		this.llistarSessionsPeli();

		//Demana si MODIFICAR llistat de SESSIONS de la PEL·LICULA
		if (Validacio.validaBoolea("\n\tModifica llistat de SESSIONS per la PEL·LICULA?(S/N)")) {

			//Si hi ha SESSIONS per la PEL·LICULA, demana acció a fer (AFEGIR o ESBORRAR)
			if(this.getSessionsPeli().size()!=0) {
				do{
					System.out.println("\tAfegir(A) o Esborrar(E) Sessio a la Peli? ");
					accio = s.nextLine();
				}while (accio.toUpperCase().compareTo("A")!=0 & accio.toUpperCase().compareTo("E")!=0);
			}else {
				//No hi ha cap SESSIO, acció -> d'AFEGIR SESSIO a la PEL·LICULA
				//A = Afegeix Sessio de Peli
				accio = "A";
				if (sessionsLliures.size()==0) 
					System.out.println("\n\t No hi ha cap SESSIO registrada");
				else {
					// Llista TOTES les SESSIONS disponibles
					for(int i=1; i<=sessionsLliures.size();i++){
						System.out.println("\n\t "+i+"-> "+sessionsLliures.get(i-1).toString());
						System.out.println("\t---");
					}
					System.out.println();

					numSessio = Validacio.validaSencer("\n\tIndentificador de la SESSIO LLIURE a AFEGIR?", sessionsLliures.size());
					if (numSessio != 0) { //Escollida una SESSIO LLIURE vàlida
						//obté la SESSIO proporcionada per l'usuari
						se = sessionsLliures.get((numSessio)-1);
						//Confirmar AFEGIR SESSIO a la PEL·LICULA
						if (Validacio.validaBoolea("\tAfegeix Sessio a la Peli?(S/N)")) {
							sessionsPeli.add(se);
							se.setAssignadaPeli(true);
						}//if	
					}//if
				}//else
			}//else
			//Si acció es ESBORRAR SESSIO
			if(accio.toUpperCase().compareTo("E")==0){  //E = Esborra Sessio de Peli
				System.out.println("\n\tLlista actual de SESSIONS per la PEL·LICULA");
				this.llistarSessionsPeli();
				System.out.println();

				numSessio = Validacio.validaSencer("\n\tIndentificador de la SESSIO a ESBORRAR?", sessionsPeli.size());
				if (numSessio != 0) { //Escollida una SESSIO vàlida a ESBORRAR
					//obté la SESSIO proporcionada per l'usuari
					se = retornaSessioPeli(numSessio);
					se.mostraSessioEsborrada();
					se.setAssignadaPeli(false);
					this.esborraSessioPeli(numSessio-1);
				}//if
			}//if ESBORRAR
		}//if MODIFICAR SESSIONS

		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		//s.close();
	}//modificaPelicula


	//---------------------------------
	//Esborra PEL·LICULA
	public void mostraPeliculaEsborrada() {
		setSessionsPeli(null);
		System.out.println("PEL·LICULA esborrada!");
	}//mostraPeliculaEsborrada


	//---------------------------------
	//Llista TOTES les SESSIONS associades a la PEL·LICULA
	public int llistarSessionsPeli() {
		ArrayList<Sessio> sessions = this.getSessionsPeli();

		if(sessions.size()==0)
			System.out.println("\t No hi ha SESSIONS de la PEL·LICULA");

		for (int i = 1; i <= sessions.size(); i++) {
			Sessio se = sessions.get(i-1);
			System.out.println("\t " + i + ": " + se);
		}
		return sessions.size();
	}//llistarSessionsPeli
	public int llistarSessionsPeliNoMostrar() {
		ArrayList<Sessio> sessions = this.getSessionsPeli();

		if(sessions.size()==0)
			System.out.println("\t No hi ha SESSIONS de la PEL·LICULA");

		for (int i = 1; i <= sessions.size(); i++) {
			Sessio se = sessions.get(i-1);
			System.out.println("\t " + i + ": " + se);
		}
		return sessions.size();
	}

	//---------------------------------
	//Retorna la SESSIO que està a la posicio i del llistat de SESSIONS de la PEL·LICULA
	public Sessio retornaSessioPeli(int i) {
		ArrayList<Sessio> sessionsPeli = this.getSessionsPeli();
		if (i <= sessionsPeli.size()) {
			return sessionsPeli.get(i-1);
		} else {
			System.out
			.println("ERROR PEL·LICULA:retornaSessioPeli: valor proporcionat fora de rang");
			return null;
		}
	}//retornaSessioPeli

	//---------------------------------
	//Esborra la SESSIO que està a la posicio i del llistat de SESSIONS de la PEL·LICULA
	public void esborraSessioPeli(int i) {
		ArrayList<Sessio> sessions = this.getSessionsPeli();
		sessions.remove(i);
	}//esborraSessioPeli


	//*********************************************************
	//Funcio que sobreescriu el metode toString() de la classe
	@Override
	public String toString() {
		return "PEL·LICULA [\n\t nomPeli=" + nomPeli + "\n\t nacionalitat="
				+ nacionalitat + "\n\t duracio=" + duracio + "\n\t director="
				+ director + "\n\t interprets=" + interprets + "\n\t argument="
				+ argument + "\n\t genere=" + genere + "\n\t classificacio="
				+ classificacio + "\n\t sessionsPeli={" + sessionsPeli + "}]";
	}

	//GETTERS & SETTERS
	//*********************************************************
	public String getNomPeli() {
		return nomPeli;
	}

	public void setNomPeli(String nomPeli) {
		this.nomPeli = nomPeli;
	}

	public String getNacionalitat() {
		return nacionalitat;
	}

	public void setNacionalitat(String nacionalitat) {
		this.nacionalitat = nacionalitat;
	}

	public int getDuracio() {
		return duracio;
	}

	public void setDuracio(int duracio) {
		this.duracio = duracio;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getInterprets() {
		return interprets;
	}

	public void setInterprets(String interprets) {
		this.interprets = interprets;
	}

	public String getArgument() {
		return argument;
	}

	public void setArgument(String argument) {
		this.argument = argument;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public String getClassificacio() {
		return classificacio;
	}

	public void setClassificacio(String classificacio) {
		this.classificacio = classificacio;
	}

	public ArrayList<Sessio> getSessionsPeli() {
		return sessionsPeli;
	}

	public void setSessionsPeli(ArrayList<Sessio> sessionsPeli) {
		this.sessionsPeli = sessionsPeli;
	}

	public void setSessioPeli(Sessio s1) {
		sessionsPeli. add(s1);
		s1.setAssignadaPeli(true);
	}
}//class
