package CINE_V3_CompraNEntrades_Servidor;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;


public class Sessio {
	private String nomSessio;
	private Calendar data;
	private  Sala sala;
	private Seient[][] seients;
	private BigDecimal preu;
	private boolean assignadaPeli;


	//Constructor 1
	public Sessio(String nomSessio, Calendar data, Sala sala, BigDecimal preu) {

		this.nomSessio = nomSessio;
		this.data = data;
		this.sala = sala;
		this.preu = preu;
		this.seients = new Seient[sala.getFiles()][sala.getTamanyFila()];	//Crea una matriu de Seients per la Sessio
		//Per a cada fila i cada columna, crea un nou Seient
		for (int i=0; i < sala.getTamanyFila(); i++){
			for (int j=0; j < sala.getFiles(); j++){
				this.seients[i][j] = new Seient(i,j);
			}//for
		}//for
		this.assignadaPeli=false;
	}


	//Constructor 2
	public Sessio(String nomSessio, int dia,int mes, int any, int hora, int minuts, Sala sala, BigDecimal preu) {

		this.nomSessio = nomSessio;
		this.data = Calendar.getInstance();
		this.data.set(any, mes-1, dia, hora, minuts);
		this.sala = sala;
		this.preu = preu;
		this.seients = new Seient[sala.getFiles()][sala.getTamanyFila()];	//Crea una matriu de Seients per la Sessio
		//Per a cada fila i cada columna, crea un nou Seient
		for (int i=0; i < sala.getTamanyFila(); i++){
			for (int j=0; j < sala.getFiles(); j++){
				this.seients[i][j] = new Seient(i,j);
			}//for
		}//for
		this.assignadaPeli=false;
	}


	//Constructor INTERACTIU
	public Sessio(String nomSessio, ArrayList<Sala> sales) {
		int nsala;
		Scanner s = new Scanner(System.in);
		//Demana una data i una vegada validada, la desa al seu atribut
		this.nomSessio = nomSessio;
		this.data = Validacio.validaData("\n\tData de la sessió? (dd/mm/aaaa) ");
		this.mostraDataFormatada();
		//Demana una SALA i una vegada validada, la desa al seu atribut
		System.out.println("\n\tLlistat de SALES disponibles:");

		//Llista TOTES les SALES DISPONIBLES
		for(int i=1; i<=sales.size();i++){
			System.out.print("\n\t "+i+": "+sales.get(i-1).toString());
		}
		System.out.println("");
		//Eleccio de la Sala
		nsala = Validacio.validaSencer("\n\tIndentificador de la sala?", sales.size());
		
		if (nsala!=0) {		//Eleccio de SALA vàlida...
			//Desa la SALA al seu atribut
			this.sala = sales.get(nsala-1);
			//Demana un preu i una vegada validat, ho desa al seu atribut
			this.preu = Validacio.validaMoneda("\tPreu de la sessió? ");
			//Crea una matriu de Seients per la Sessio
			this.seients = new Seient[sala.getFiles()][sala.getTamanyFila()]; 		
			//Creacio SEIENTS Per a cada fila i cada columna, crea un nou Seient
			for (int i=0; i < sala.getTamanyFila(); i++){
				for (int j=0; j < sala.getFiles(); j++){
					this.seients[i][j] = new Seient(i,j);
				}
			}//for
			this.assignadaPeli=false;	//S'indica que la SESSIO NO té associada cap PEL·LICULA
		}//if

		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		//s.close();
	}

	
	//*********************************************************
	//Modifica la SESSIO
	//Funcio que modifica el contingut dels atributs de la SESSIO
	public void modificaSessio(ArrayList<Sala> sales) {
		int nsala;
		Scanner s = new Scanner(System.in);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>");
		System.out.println("\tModificació de la SESSIO\n>>>>>>>>>>>>>>>>>>>>>");
		System.err.println("\tPrem tecla INTRO per matenir informació anterior ");

		this.nomSessio = Validacio.validaCadenaDefecte("\tIndentificador de la sessió? ",this.getNomSessio());

		this.data = Validacio.validaDataDefecte("\n\tData de la sessió? (dd/mm/aaaa) ["
				+ data.get(Calendar.DAY_OF_MONTH) + "/"
				+ data.get(Calendar.MONTH) + "/"
				+ data.get(Calendar.YEAR) + "]",data);
		this.mostraDataFormatada();

		System.out.println("\n\tLlistat de SALES disponibles:");
		//Llista TOTES les SALES DISPONIBLES
		for(int i=1; i<=sales.size();i++){
			System.out.print("\n\t "+i+": "+sales.get(i-1).toString());
		}
		System.out.println("");
		nsala = Validacio.validaSencerDefecte("\n\tIndentificador de la SALA?", sales.size(),sales.indexOf(this.sala)+1);

		if  (nsala!=0) { //SALA distinta a 0
			//Canvia la SALA anterior a una de la llista 
			this.sala = sales.get((nsala)-1);
		}

		this.preu = Validacio.validaMonedaDefecte("\tPreu de la sessió? ",this.getPreu());

		boolean reinicia = Validacio.validaBoolea("\tReiniciar assignació seients? (S/N) ");

		if(reinicia) {	//S'inicialitza la matriu de Seients deixant-los tots LLIURES
			//Creacio SEIENTS
			for (int i=0; i < this.sala.getTamanyFila(); i++){
				for (int j=0; j < this.sala.getFiles(); j++){
					if(this.seients[i][j]!=null) //Si seient ja existeix, l'allibera	
						this.seients[i][j].alliberaSeient();
					else	//si NO existeix, es crea 
						this.seients[i][j] = new Seient(i,j);
				}//for
			}//for
		}else{
			//resposta = N. Deixa matriu de Seients com estan
		}//else

		System.out.println("\t=======================");
		System.out.println(this);

		this.assignadaPeli= Validacio.validaBooleaDefecte("\tTé PELICULA associada? (S/N) ", this.assignadaPeli);
		//s.close();
	}//modificaSessio

	
	//*********************************************************
	//Esborra la SESSIO
	public void mostraSessioEsborrada() {
		System.out.println("Sessió esborrada!");
	}//mostraSessioEsborrada

	
	//*********************************************************
	//Mostra la distribució de SEIENTS a la SALA
	public  void mapaSessio(){
		//*************************************************
		synchronized(System.out) {
		System.out.println("\n\t --------  MAPA SESSIO  -----------");
		//CAPÇALERA de la SALA
		System.out.print("\t Seient-> ");
		for (int x=1; x <= this.sala.getTamanyFila(); x++)
			System.out.print(x +"  ");	//mostra el num de Columna

		//COS de la SALA
		System.out.println();
		for (int i=0; i < this.sala.getTamanyFila(); i++){	//Per cada columna...
			System.out.print("\t Fila "+(i+1)+": ");		//mostra el num de Fila
			for (int j=0; j < this.sala.getFiles(); j++){	//Per cada fila..
				System.out.print(" "+this.seients[i][j].iconaSeient()+" ");		//mostra estat Seient
			}//endfor	
			System.out.println();
		}//endfor
		System.out.println("\n\t SIMBOLOGIA: X=ocupat; O=lliure; ?=reservant\n\n");
		}
	}//mapaSessio

	public  String getMapaSessio(){
		String mapa = "";
		//*************************************************
		synchronized(System.out) {
			mapa += "\n\t --------  MAPA SESSIO  -----------\n";
			//CAPÇALERA de la SALA
			mapa += "\t Seient-> ";
			for (int x=1; x <= this.sala.getTamanyFila(); x++)
				mapa += x +"  ";	//mostra el num de Columna

			//COS de la SALA
			mapa += "\n";
			for (int i=0; i < this.sala.getTamanyFila(); i++){	//Per cada columna...
				mapa += "\t Fila "+(i+1)+": ";		//mostra el num de Fila
				for (int j=0; j < this.sala.getFiles(); j++){	//Per cada fila..
					mapa += " "+this.seients[i][j].iconaSeient()+" ";		//mostra estat Seient
				}//endfor
				mapa += "\n";
			}//endfor
			mapa += "\n\t SIMBOLOGIA: X=ocupat; O=lliure; ?=reservant\n\n";
		}
		return mapa;
	}//mapaSessio

	
	//*********************************************************
	//Mostra DATA en format espanyol
	public void mostraDataFormatada(){
		int day = this.data.get(Calendar.DAY_OF_MONTH);
		int month = this.data.get(Calendar.MONTH);
		int year = this.data.get(Calendar.YEAR);
		int hour = this.data.get(Calendar.HOUR_OF_DAY);
		int minute = this.data.get(Calendar.MINUTE);

		System.out.print("\t" +day+"/"+month+"/"+year+" "+hour+":"+minute);
	}//mostraDataFormatada
	
	public String obtindreDataFormatada(){
		int day = this.data.get(Calendar.DAY_OF_MONTH);
		int month = this.data.get(Calendar.MONTH);
		int year = this.data.get(Calendar.YEAR);
		int hour = this.data.get(Calendar.HOUR_OF_DAY);
		int minute = this.data.get(Calendar.MINUTE);

		//System.out.print("\t" +day+"/"+month+"/"+year+" "+hour+":"+minute);
		
		return +day+"/"+month+"/"+year+" "+hour+":"+minute;
	}//mostraDataFormatada

	public String getmostraDataFormatada(){
		int day = this.data.get(Calendar.DAY_OF_MONTH);
		int month = this.data.get(Calendar.MONTH);
		int year = this.data.get(Calendar.YEAR);
		int hour = this.data.get(Calendar.HOUR_OF_DAY);
		int minute = this.data.get(Calendar.MINUTE);

		String fecha = +day+"/"+month+"/"+year+" "+hour+":"+minute;
		return fecha;
	}//mostraDataFormatada


	//*********************************************************
	//Mostra TICKET de compra de la PELICULA
	public synchronized void imprimirTicket(Seient s, Sessio se, Sala sa, Pelicula p){
		System.out.println("Imprimint el seu Ticket...");
		System.out.println("***************************");
		System.out.println("* ***TICKET ENTRADA *******");
		System.out.println("* PELICULA: "+ p.getNomPeli() +" *");
		System.out.print("* HORARI: ");
		se.mostraDataFormatada();
		System.out.println("*\n* Seient FILA:"+(s.getFilaSeient()+1)+ " SEIENT:"+(s.getNumeroSeient()+1)+"*");
		System.out.println("* Preu: "+ se.getPreu()+" €");
		System.out.println("****************************");
	}//imprimirTicket

	public synchronized String getimprimirTicket(Seient s, Sessio se, Sala sa, Pelicula p){
		String entrada = "";
		entrada += "Imprimint el seu Ticket...\n";
		entrada += "***************************\n";
		entrada += "* ***TICKET ENTRADA *******\n";
		entrada += "* PELICULA: "+ p.getNomPeli() +"        *\n";
		entrada += "* HORARI: ";
		entrada += se.getmostraDataFormatada();
		entrada += " *\n* Seient FILA:"+(s.getFilaSeient()+1)+ " SEIENT:"+(s.getNumeroSeient()+1)+"  *\n";
		entrada += "* Preu: "+ se.getPreu()+" €               *\n";
		entrada += "***************************\n\n";
		return  entrada;
	}//imprimirTicket


	//*********************************************************
	//Funcio que sobreescriu el metode toString() de la classe
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMMM yyyy 'a les' hh:mm:ss");
		return "Sessio [nomSessio=" + nomSessio + "\n\t data=" + sdf.format(data.getTime()) + 
				"\n\t sala="+ sala + "\n\t preu=" + preu + "\n\t assignadaPeli="+ assignadaPeli + "]";
	}


	//GETTERS & SETTERS
	//*********************************************************
	public  String getNomSessio() {
		return nomSessio;
	}

	public  void setNomSessio(String nomSessio) {
		this.nomSessio = nomSessio;
	}

	public  Calendar getData() {
		return data;
	}

	public  void setData(Calendar data) {
		this.data = data;
	}

	public  Sala getSala() {
		return sala;
	}

	public  void setSala(Sala sala) {
		this.sala = sala;
	}

	public  BigDecimal getPreu() {
		return preu;
	}

	public  void setPreu(BigDecimal preu) {
		this.preu = preu;
	}

	public  Seient[][] getSeients() {
		return seients;
	}

	public  void setSeients(Seient[][] seients) {
		this.seients = seients;
	}

	public boolean isAssignadaPeli() {
		return assignadaPeli;
	}

	public void setAssignadaPeli(boolean assignadaPeli) {
		this.assignadaPeli = assignadaPeli;
	}
}//class
