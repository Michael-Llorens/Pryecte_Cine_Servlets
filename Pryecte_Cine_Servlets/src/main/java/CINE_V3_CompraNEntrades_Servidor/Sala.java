package CINE_V3_CompraNEntrades_Servidor;

public class Sala {

	private int numeroSala;
	private boolean sala3D;
	private int files;
	private int tamanyFila;


	//Constructor 1
	public Sala(int numeroSala) {
		boolean sala3d = false;

		this.numeroSala=numeroSala;

		sala3d = Validacio.validaBoolea("\n\tSala 3D? (S/N)");
		this.sala3D=sala3d;

		int fseients = Validacio.validaSencer("\tNum de files de seients de la Sala? ",100);
		this.files=fseients;

		int seients = Validacio.validaSencer("\tNum de seients per fila? ",100);
		this.tamanyFila=seients;

		System.out.println("\n<<<<<<<<<<<<<<<<<<<<<<<< ");
	}


	//Constructor 2
	public Sala(int numero, int nfiles, int seients) {

		this.numeroSala = numero;
		this.sala3D = false;
		this.files = nfiles;
		this.tamanyFila = seients;
	}


	//Constructor 3
	public Sala(int numero, boolean sala3d, int nfiles, int seients) {

		this.numeroSala = numero;
		this.sala3D = sala3d;
		this.files = nfiles;
		this.tamanyFila = seients;
	}
	
	//*********************************************************
	//Modifica la SALA
	//Funcio que modifica el contingut dels atributs de la SALA
	public void modificaSala() {
		boolean sala3d = false;
		System.out.println("\tModificació de la SALA\nPrem tecla INTRO per matenir informació anterior ");

		numeroSala = Validacio.validaSencerDefecte("\tNumero de la Sala? ",5,this.getNumeroSala());
		this.setNumeroSala(numeroSala);

		sala3d = Validacio.validaBooleaDefecte("\n\tSala 3D? (S/N)",this.isSala3D());
		this.setSala3D(sala3d);

		int fseients = Validacio.validaSencerDefecte("\tNum de files de seients de la Sala? ",100, this.getFiles());
		this.setFiles(fseients);

		int seients = Validacio.validaSencerDefecte("\tNum de seients per fila? ",100, this.getTamanyFila());
		this.setTamanyFila(seients);

		System.out.println("\n<<<<<<<<<<<<<<<<<<<<< ");
		System.out.println(this);
	}//modificaSala

	
	//*********************************************************
	//Funcio que mostra el text de Sala esborrada
	public void mostraSalaEsborrada() {
		System.out.println("Sala esborrada!");
	}//mostraSalaEsborrada


	//*********************************************************
	//Funcio que sobreescriu el metode toString() de la classe
	@Override
	public String toString() {
		return "Sala [numeroSala=" + numeroSala + ", sala3D=" + sala3D
				+ ", files=" + files + ", tamanyFila=" + tamanyFila+"]";
	}


	//GETTERS & SETTERS
	//*********************************************************
	public  int getNumeroSala() {
		return numeroSala;
	}

	public int getFiles() {
		return files;
	}

	public void setFiles(int files) {
		this.files = files;
	}

	public int getTamanyFila() {
		return this.tamanyFila;
	}

	public void setTamanyFila(int tamanyFila) {
		this.tamanyFila = tamanyFila;
	}

	public  void setNumeroSala(int numeroSala) {
		this.numeroSala = numeroSala;
	}

	public  boolean isSala3D() {
		return sala3D;
	}

	public  void setSala3D(boolean sala3d) {
		sala3D = sala3d;
	}
}//class