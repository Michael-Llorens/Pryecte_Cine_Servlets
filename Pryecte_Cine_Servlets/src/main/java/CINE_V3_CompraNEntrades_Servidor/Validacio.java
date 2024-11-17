package CINE_V3_CompraNEntrades_Servidor;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Scanner;

public class Validacio {
	
	static Scanner s = new Scanner(System.in);

	//metode que  mostra per pantalla missatge i retorna el valor introduit per teclat, aquest valor serà sempre menor o igual a maxSencer
	static synchronized int validaSencer(String missatge, int maxSencer) {
		int sencer = -1;
		String cadena = null;
		boolean validatSencer = false;
		
		do {
			System.out.print(missatge);
			cadena = s.next();
			if (cadena.matches("0")) return 0;
			else {
				if (cadena.matches("^(?!^0)\\d{1,9}$")) { // valor numeric
					sencer = Integer.parseInt(cadena);
					if (sencer <= maxSencer)
						validatSencer = true;
					else
						System.out.println("\tERROR: Numero fora de rang");
				} else { // valor NO numeric
					System.out.println("\tERROR: Valor no numeric");
				}}
		} while (!validatSencer);

		return sencer;
	}

	// --------------------------------------------------
	//metode que  mostra per pantalla missatge i retorna el valor introduit per teclat o,
	//si es polsa INTRO retorna el valor defecte(3r argument),
	//aquest valor serà sempre menor o igual a maxSencer
	static synchronized int validaSencerDefecte(String missatge, int maxSencer, int defecte) {
		int sencer = -1;
		String cadena = null;
		boolean validatSencer = false;

		do {
			System.out.print(missatge + " [" + defecte + "]");
			cadena = s.nextLine();
			if (cadena.matches("^(?!^0)\\d{1,9}$")) { // valor numeric
				sencer = Integer.parseInt(cadena);
				if ((sencer <= maxSencer) && (sencer > 0))
					validatSencer = true;
				else
					System.out.println("\tERROR: Numero fora de rang");
			} else { // valor NO numeric
				if (cadena.equals("")) { // Valor per defecte
					validatSencer = true;
					sencer = defecte;
				} else
					System.out.println("\tERROR: Valor no numeric");
			}
		} while (!validatSencer);

		return sencer;
	}

	// --------------------------------------------------
	//metode que mostra per pantalla missatge i retorna el valor boleà introduit per teclat
	static synchronized boolean validaBoolea(String missatge) {

		boolean resultat = false;
		String cadena = null;
		boolean validatBoolea = false;

		do {
			System.out.print(missatge);
			cadena = s.next();
			if (cadena.toUpperCase().matches("^[S,N]$")) { // valor boolea

				if (cadena.equalsIgnoreCase("S")) { // valor S
					validatBoolea = true;
					resultat = true;
				} else { // valor N
					validatBoolea = true;

				}
			} else { // valor NO boolea
				System.out.println("\tERROR: Valor NO boolea");
			}
		} while (!validatBoolea);

		return resultat;
	}

	// --------------------------------------------------
	//metode que  mostra per pantalla missatge i retorna el valor introduit per teclat o,
	//si es polsa INTRO retorna el valor defecte(2n argument)
	static synchronized boolean validaBooleaDefecte(String missatge, boolean defecte) {

		boolean resultat = false;
		String cadena = null;
		char valorDefecte;
		boolean validatBoolea = false;

		if (defecte)
			valorDefecte = 'S';
		else
			valorDefecte = 'N';

		do {
			System.out.print(missatge + " [" + valorDefecte + "]");
			cadena = s.nextLine();
			if (cadena.equals("")) { // Valor per defecte
				validatBoolea = true;
				resultat = defecte;
			} else {
				if (cadena.toUpperCase().matches("^[S,N]$")) { // valor boolea

					if (cadena.equalsIgnoreCase("S")) { // valor S
						validatBoolea = true;
						resultat = true;
					} else { // valor N
						validatBoolea = true;
					}
				} else { // valor NO boolea
					System.out.println("\tERROR: Valor NO boolea");
				}
			}
		} while (!validatBoolea);

		return resultat;
	}

	// --------------------------------------------------
	//metode que  mostra per pantalla missatge i retorna la cadena introduida per teclat
	static synchronized String validaCadena(String missatge) {
		String cadena = null;

		System.out.print(missatge);
		cadena = s.nextLine();
		
		return cadena;
	}

	// --------------------------------------------------
	//metode que  mostra per pantalla missatge i retorna la cadena introduida per teclat o,
	//si es polsa INTRO retorna el valor defecte(2n argument)
	static synchronized String validaCadenaDefecte(String missatge, String defecte) {
		String cadena = null;

		System.out.print(missatge + " [" + defecte + "]");
		cadena = s.nextLine();

		if (cadena.equals("")) // Valor per defecte
			cadena = defecte;

		return cadena;
	}

	// --------------------------------------------------
	//metode que  mostra per pantalla missatge i retorna la data introduida per teclat
	static synchronized Calendar validaData(String missatge) {
		Calendar data;
		String strdata = null;
		int year = 0, month = 0, day = 0, hour = 0, minute = 0;
		boolean validatData = false, validatHora = false;

		do {
			System.out.print(missatge);
			strdata = s.nextLine();
			if (strdata.toUpperCase().matches(
					"^[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]$")) { // valor
				// boolea
				validatData = true;
				String[] date = strdata.split("/");

				System.out.print("\n\t" + date[0] + "/" + date[1] + "/"
						+ date[2]);

				year = Integer.parseInt(date[2]);
				month = Integer.parseInt(date[1]);
				day = Integer.parseInt(date[0]);

			}
		} while (!validatData);
		do {
			System.out.print("\n\tHora de la sessió? (hh:mm) ");
			strdata = s.nextLine();
			if (strdata.toUpperCase().matches("^[0-9][0-9]:[0-9][0-9]$")) { // valor
				// boolea
				validatHora = true;
				String[] time = strdata.split(":");

				System.out.println("\n\t" + time[0] + ":" + time[1]);
				hour = Integer.parseInt(time[0]);
				minute = Integer.parseInt(time[1]);
			}
		} while (!validatHora);

		data = Calendar.getInstance();
		data.set(year, month, day, hour, minute);
		
		return data;
	}

	// --------------------------------------------------
	//metode que  mostra per pantalla missatge i retorna la data introduida per teclat, o
	//si es polsa INTRO retorna el valor defecte(2n argument)
	static synchronized Calendar validaDataDefecte(String missatge, Calendar antiga) {
		Calendar data;
		int year = 0, month = 0, day = 0, hour = 0, minute = 0;
		String strdata;
		boolean validatData = false, validatHora = false;

		do {
			System.out.print(missatge);
			strdata = s.nextLine();

			if (strdata.equals("")) { // Valor per defecte
				validatData = true;
				day = antiga.get(Calendar.DAY_OF_MONTH);
				month = antiga.get(Calendar.MONTH);
				year = antiga.get(Calendar.MONTH);

			} else { // nou valor introduit
				if (strdata.toUpperCase().matches(
						"^[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]$")) { // valor
					// boolea
					validatData = true;
					String[] date = strdata.split("/");

					System.out.print("\n\t" + date[0] + "/" + date[1] + "/"
							+ date[2]);

					year = Integer.parseInt(date[2]);
					month = Integer.parseInt(date[1]);
					day = Integer.parseInt(date[0]);

				}
			}
		} while (!validatData);

		do {
			System.out.print("\n\tHora de la sessió? (hh:mm) ["
					+ antiga.get(Calendar.HOUR) + ":"
					+ antiga.get(Calendar.MINUTE) + "]");
			strdata = s.nextLine();

			if (strdata.equals("")) { // Valor per defecte
				validatHora = true;
				hour = antiga.get(Calendar.HOUR);
				minute = antiga.get(Calendar.MINUTE);
			} else {

				if (strdata.toUpperCase().matches("^[0-9][0-9]:[0-9][0-9]$")) { // valor
					// boolea
					validatHora = true;
					String[] time = strdata.split(":");

					System.out.print("\n\t" + time[0] + ":" + time[1]);
					hour = Integer.parseInt(time[0]);
					minute = Integer.parseInt(time[1]);
				}
			}
		} while (!validatHora);

		data = Calendar.getInstance();
		data.set(year, month, day, hour, minute);
		return data;
	}

	// --------------------------------------------------
	//metode que  mostra per pantalla missatge i retorna el BigDecimal introduida per teclat
	static synchronized BigDecimal validaMoneda(String missatge) {
		BigDecimal resultat = null;
		String cadena = null;
		boolean validaMoneda = false;

		do {
			System.out.print(missatge);
			cadena = s.nextLine();
			if (cadena.toUpperCase().matches("^(?!^0)\\d{1,9}.(?!^0)\\d{1,9}$") || cadena.toUpperCase().matches("^(?!^0)\\d{1,9}$")) { // valor
				validaMoneda = true;
				resultat = new BigDecimal(cadena);
			}
		} while (!validaMoneda);
		
		return resultat;
	}

	// --------------------------------------------------
	//metode que  mostra per pantalla missatge i retorna el BigDecimal introduida per teclat, o
	//si es polsa INTRO retorna el valor defecte(2n argument)
	static synchronized BigDecimal validaMonedaDefecte(String missatge, BigDecimal antiga) {
		BigDecimal resultat = null;
		String cadena = null;
		boolean validaMoneda = false;

		do {
			System.out.print(missatge + " ["+ antiga + "]");
			cadena = s.nextLine();
			if(cadena.equalsIgnoreCase("")) {
				return antiga;
			}
			if (cadena.toUpperCase().matches("^(?!^0)\\d{1,9}.(?!^0)\\d{1,9}$") || cadena.toUpperCase().matches("^(?!^0)\\d{1,9}$")) { // valor
				validaMoneda = true;
				resultat = new BigDecimal(cadena);
			}
		} while (!validaMoneda);
		
		return resultat;
	}
	// --------------------------------------------------
}
