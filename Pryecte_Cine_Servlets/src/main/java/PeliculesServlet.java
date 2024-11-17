import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import java.util.ArrayList;
import CINE_V3_CompraNEntrades_Servidor.Cine;
import CINE_V3_CompraNEntrades_Servidor.Pelicula;
import CINE_V3_CompraNEntrades_Servidor.Sala;
import CINE_V3_CompraNEntrades_Servidor.Seient;
import CINE_V3_CompraNEntrades_Servidor.Seient.Estat;
import CINE_V3_CompraNEntrades_Servidor.Sessio;

public class PeliculesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private int pas;
	private Cine cine;
	Pelicula peliculaSeleciona;
	Sessio sessioPeli;
	private HttpSession session;
	Boolean reinici = false;
	Boolean ocultar = false;

    public PeliculesServlet() {
        super();
        cine = new Cine("KINEPOLIS");
        cine.carregaDadesInicials(cine);
    }

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		pas = 1;
		peliculaSeleciona = null;
		sessioPeli = null;
	}



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		session = request.getSession(false);
		if (session == null) {
			session = request.getSession(true);
		}
		
		String botons = request.getParameter("boton");
		
		if ("Seguent".equals(botons)) {
			if (pas != 6) {
				pas++;
				System.out.println("Següent");
				System.out.println("Pas "+pas);
			}else {
				pas = 0;
				System.out.println("Reinicia");
				System.out.println("Pas "+pas);
				response.sendRedirect("index.jsp");
	            // Invalidar la sesión
				session.invalidate();
				reinici = true;
			}
		}else if ("Enrere".equals(botons)) {
			if (pas == 6) {
				System.out.println("Tornem arrere del pas de les reserves");
				ocultar = true;
			}
			if (pas != 1) {
				pas --;
				System.out.println("Enreereee");
				System.out.println("Pas "+pas);
			}else {
				System.out.println("si torna mes enrere tanca sesio");
			}
			

		}else if ("Reinicia".equals(botons)) {
			if (pas == 5) {
				System.out.println("Reiniciem del pas de les reserves");
			}
			pas = 0;
			System.out.println("Reinicia");
			System.out.println("Pas "+pas);
			response.sendRedirect("index.jsp");
            // Invalidar la sesión
			session.invalidate();
			reinici = true;
		}	
		System.out.println("EL pas en el que estem es el pas "+pas);
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Cine</title>");
		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">");
		out.println("</head>");
		out.println("<h3>"+session.getId()+"</h3>");
		out.println("<header><h1>CINE KENEPOLIS</h1></header>");
		
		switch (pas) {
		case 0:
			if (pas == 0 && reinici == true) {
				pas = 1;
			}
			break;
		case 1:
			ocultar = false;
			int p = 0;
			if (session.getAttribute("peli") != null) {
				System.out.print("VA a eliminaar la peli");
				System.out.println((int) session.getAttribute("peli"));
				session.removeAttribute("peli");
			}
			
			System.out.println("Estem en el cas 1");
			List pelis = null;
			pelis = cine.getPelicules();

			out.println("<body>");
			out.println("<h2>Pas 1 de 6: Selecció de Pelicula</h2>");
			out.println("<h3>Seleccina del llistat de Pelicules:</h3>");

			out.println("<form action=PeliculesServlet method=POST>");
			out.println("<select id=\"peli\" name=\"peli\">");
			
			Pelicula pelicula;
			for (int i = 0; i < pelis.size(); i++) {
				pelicula = (Pelicula) pelis.get(i);
				out.println("<option value="+i+">"+pelicula.getNomPeli()+" / "+pelicula.getNacionalitat()+" / "+pelicula.getDirector()+"</option>");
			}
			out.println("</select><br><br>");


			break;
			
		case 2:
			int sessiosSelec;
			System.out.println("Estem en el cas 2");
			if (session.getAttribute("sessio") != null) {
				System.out.print("VA a eliminaar la sessio de la peli");
				System.out.println((int) session.getAttribute("sessio"));
				session.removeAttribute("sessio");
			}
			
			if (session.getAttribute("peli") == null) {
				p = Integer.parseInt(request.getParameter("peli"));
				peliculaSeleciona = cine.getPelicules().get(p);
				System.out.println("Crea el atribut peli");
				session.setAttribute("peli", p);
			}
			
			System.out.println((int) session.getAttribute("peli"));
			peliculaSeleciona = cine.getPelicules().get((int) session.getAttribute("peli"));
			System.out.println("Tingem la pelicula "+peliculaSeleciona.getNomPeli());
			
			out.println("<h2>Pas 2 de 6: Selecció de Sessió</h2>");
			out.println("<h3 class=\"txtSelect\">Peli seleccionada: "+peliculaSeleciona.getNomPeli()+"</h3>");
			out.println("<form action=PeliculesServlet method=POST>");
			out.println("<p>Selecciona de la llista de Session la peliculula "+peliculaSeleciona.getNomPeli()+"</p>");

			//////////////////// Llistata
			
			out.println("<select id=\"sessio\" name=\"sessio\">");
			
			Sessio sessio;
			for (int i = 0; i < peliculaSeleciona.llistarSessionsPeliNoMostrar(); i++) {
				sessio = peliculaSeleciona.getSessionsPeli().get(i);
				out.println("<option value="+i+">"+sessio.getNomSessio() + " / " + sessio.getmostraDataFormatada() + " / " + sessio.getPreu() +"€</option>");
			}
			out.println("</select><br><br>");
			break;
		case 3:
			int numEntrades = 0;
			System.out.println("Estem en el cas 3");
			
			if (session.getAttribute("entrades") != null) {
				System.out.print("VA a eliminaar entrades");
				System.out.println((int) session.getAttribute("entrades"));
				session.removeAttribute("entrades");
			}
			
			if (session.getAttribute("sessio") == null) {
				sessiosSelec = Integer.parseInt(request.getParameter("sessio"));
				sessio = peliculaSeleciona.getSessionsPeli().get(sessiosSelec);
				System.out.println("Crea el atribut sessio");
				session.setAttribute("sessio", sessiosSelec);
			}
			
			sessioPeli = peliculaSeleciona.getSessionsPeli().get((int) session.getAttribute("sessio"));
			System.out.println((int) session.getAttribute("sessio"));
			
			//sessioPeli.getSeients()
			Seient[][] seiens = sessioPeli.getSeients();
			int seiensLliures = 0;

			out.println("<h2>Pas 3 de 6: Selecció del num Entrades</h2>");
			out.println("<h3 class=\"txtSelect\">Sessio triada: "+sessioPeli.getNomSessio()+"</h3>");
			out.println("<table>");
						
			for (int i = 0; i < seiens.length; i++) {
				out.print("<tr>");
				for (int j = 0; j < seiens[i].length; j++) {
					if (seiens[i][j].getDisponibilitat() == Estat.OCUPAT) {
						out.print("<td class=\"rojo\">"+(i+1)+","+(j+1)+"</td>");
					}else {
						out.print("<td class=\"verde\">"+(i+1)+","+(j+1)+"</td>");
						seiensLliures++;
					}
				}
				//out.println("<br>");
			}
			out.println("</table>");
			out.println("<br>");
			out.println("<form action=PeliculesServlet method=POST>");
			out.println("<p>Tria quantes entrades vols comrpar:</p>");
			out.println("<select id=\"entrades\" name=\"entrades\">");
			for (int i = 0; i < seiensLliures; i++) {
				out.println("<option value="+i+">"+(i+1)+"</option>");
			}
			out.println("</select><br><br>");

			break;
		case 4:
			System.out.println("Estem en el pas 4");
			
			if (session.getAttribute("llistaEntrades") != null) {
				ArrayList<Seient> seientsReservats = new ArrayList<Seient>();	
				
				seientsReservats = (ArrayList<Seient>) session.getAttribute("llistaEntrades");
				
				if (seientsReservats.size() > 0) {
					System.out.println("El array ya te seient exactament "+seientsReservats.size());
					for (int i = 0; i < seientsReservats.size(); i++) {
						seientsReservats.get(i).alliberaSeient();
					}
				}else {
					System.out.println("No hi ha res en el array");
				}
			}else {
				System.out.println("El array no esta creat");
			}
			

			
			if (session.getAttribute("entrades") == null) {
				System.out.println("Crea el atribut num entrades");
				numEntrades = Integer.parseInt(request.getParameter("entrades")) +1;
				System.out.println("Les entrades son = "+numEntrades);
				session.setAttribute("entrades", numEntrades);
				System.out.println(session.getAttribute("entrades"));
			}else {
				System.out.println("Estes son les entrades");
				System.out.println( session.getAttribute("entrades"));
				numEntrades = (int) session.getAttribute("entrades");
			}
			
			sessioPeli = peliculaSeleciona.getSessionsPeli().get((int) session.getAttribute("sessio"));
			//System.out.println((int) session.getAttribute("sessio"));
			
			//sessioPeli.getSeients()
			seiens = sessioPeli.getSeients();
			seiensLliures = 0;
			
			out.println("<h2>Pas 4 de 6: Selecció dels Seients</h2>");
			out.println("<h3 class=\"txtSelect\">Entrades Seleccionades: "+numEntrades+"</h3>");
			out.println("<table>");
						
			for (int i = 0; i < seiens.length; i++) {
				out.print("<tr>");
				for (int j = 0; j < seiens[i].length; j++) {
					if (seiens[i][j].getDisponibilitat() == Estat.OCUPAT) {
						out.print("<td class=\"rojo\">"+(i+1)+","+(j+1)+"</td>");
					}else {
						out.print("<td class=\"verde\">"+(i+1)+","+(j+1)+"</td>");
					}			
				}
				//out.println("<br>");
			}
			out.println("</table>");
			out.println("<br>");

			out.println("<form action=PeliculesServlet method=POST>");
			for (int i = 0; i < numEntrades; i++) {
				out.println("<h3>Seient "+(i+1)+"</h3>");
				out.println("<p>Tria la FILA del Seient "+(i+1)+"</p>");
				out.println("<div class=\"input-group\">");
				out.println("<select id=\"fila\" name=\"fila"+i+"\" class=\"selectSeient\">");
				for (int j = 0; j < seiens.length; j++) {
					out.println("<option value=fila"+j+">Fila: "+(j+1)+"</option>");
				}
				out.println("</select>");

				out.println("<p>Tria la COLUMNA del Seient "+(i+1)+"</p>");
				out.println("<select id=\"columna\" name=\"columna"+i+"\" class=\"selectSeient\">");
				for (int j = 0; j < seiens.length; j++) {
					out.println("<option value=columna"+j+">Columna: "+(j+1)+"</option>");
				}
				out.println("</select>");
				out.println("<br><br>");
				out.println("</div>");
			}

			out.println("<br><br>");

			break;
		case 5:
			System.out.println(" EStem en el pas 5");
			numEntrades = (int) session.getAttribute("entrades");
			System.out.println("/*-"+numEntrades);
			
			int contadorCorrecte = 0;
			
			seiens = sessioPeli.getSeients();
			
			ArrayList<Seient> seientsAcomprar = new ArrayList<Seient>();	
			
			if (session.getAttribute("pagaEntrades") != null) {
				seientsAcomprar = (ArrayList<Seient>) session.getAttribute("llistaEntrades");
			}			
			System.out.println(seientsAcomprar.size());
			
			out.println("<h2>Pas 5 de 6: Pagament d'entrades</h2>");
			out.println("<h3 class=\"txtSelect\">Seients escollits: </h3>");
			out.println("<form action=PeliculesServlet method=POST>");
			out.println("<ul>");
			System.out.println("ESTEEEEEE ES LA PRIEREA FORLALALLALallalalalaLLALALAL");
			
			for (int i = 0; i < numEntrades; i++) {
				int fila;
				int columna;
				if (session.getAttribute("fila"+i) != null || session.getAttribute("columna"+i) != null) {
					fila = (int) session.getAttribute("fila"+i);
					session.removeAttribute("fila"+i);
					columna = (int) session.getAttribute("columna"+i);
					session.removeAttribute("columna"+i);
				}else {
					fila = Integer.parseInt(request.getParameter("fila"+i).split("fila")[1]);
					columna = Integer.parseInt(request.getParameter("columna"+i).split("columna")[1]);
				}
				
				System.out.println("Entrada Nº"+i);
				System.out.print("La fila = "+fila+" - ");
				System.out.println(columna);
				
				if (!seientsAcomprar.contains(sessioPeli.getSeients()[fila][columna])) {

					if (sessioPeli.getSeients()[fila][columna].getDisponibilitat() == Estat.LLIURE) {
						out.println("<li class=\"green\">Entrada Nº"+i+"  SEIENT ["+(fila+1)+"-"+(columna+1)+"] -> RESERVAT</li>");
						seientsAcomprar.add(seiens[fila][columna]);
						contadorCorrecte++;
						session.setAttribute("fila"+i, fila);
						session.setAttribute("columna"+i, columna);
						
						System.out.println("Crea al atribut "+fila+" en el valor "+ session.getAttribute("fila"+i));
					}else if (sessioPeli.getSeients()[fila][columna].getDisponibilitat() == Estat.OCUPAT) {
						out.println("<li class=\"red\">Entrada Nº"+i+"  SEIENT ["+(fila+1)+"-"+(columna+1)+"] -> Està OCUPAT</li>");
					}else {
						out.println("<li class=\"red\">Entrada Nº"+i+"  SEIENT ["+(fila+1)+"-"+(columna+1)+"] -> Està REPETIT</li>");
					}
				}else {
					out.println("<li class=\"red\">Entrada Nº"+i+"  SEIENT ["+(fila+1)+"-"+(columna+1)+"] -> Està REPETIT</li>");
				}
			}
			
			System.out.println(contadorCorrecte);
			System.out.println(seientsAcomprar.size());
			
			sessioPeli = peliculaSeleciona.getSessionsPeli().get((int) session.getAttribute("sessio"));
			
			System.out.println(sessioPeli.getPreu());
			
			BigDecimal preu = sessioPeli.getPreu().multiply(BigDecimal.valueOf(seientsAcomprar.size()));
						
			if (contadorCorrecte == numEntrades) {
				out.println("<h3 class=\"txtCorrecte\">Import a pagar: "+preu+" €</h3>");
				out.println("<br>");
				out.println("<p>S'ha pagat l'import?</p>");
				out.println("<select id=\"pagaEntrades\" name=\"pagaEntrades\">");
					out.println("<option value=si>SI</option>");
					out.println("<option value=no>No</option>");
				out.println("</select>");
				out.println("<br><br>");				
				for (int i = 0; i < seientsAcomprar.size(); i++) {
					if (seientsAcomprar.get(i).getDisponibilitat() != Estat.OCUPAT) {
						seientsAcomprar.get(i).reservaSeient();
					}
				}
				session.setAttribute("llistaEntrades", seientsAcomprar);
				System.out.println("Creeat el atribut");
			}else {
				out.println("<h3 class=\"txtError\">ERROR!</h3>");
				out.println("<p class=\"red\">Algun seient NO està disponible.</p>");
			}
			
			break;
		case 6:
			ocultar = true;
			System.out.println(" Estem en el pas 6");
			out.println("<h2>Pas 6 de 6: Confirmació compra entrades</h2>");
			
			
			seientsAcomprar = (ArrayList<Seient>) session.getAttribute("llistaEntrades");
			sessio = peliculaSeleciona.getSessionsPeli().get((int) session.getAttribute("sessio"));
			
			numEntrades = (int) session.getAttribute("entrades");
			seiens = sessioPeli.getSeients();
			Sala sala = sessio.getSala();
			
			
			
			String paga = request.getParameter("pagaEntrades");
			
			if (paga.equals("si")) {
				for (int i = 0; i < seientsAcomprar.size(); i++) {
					seientsAcomprar.get(i).ocupaSeient();
				}
				
				out.println("<p class=\"green\">pagat</p>");
				out.println("<table>");
				for (int i = 0; i < seiens.length; i++) {
					out.print("<tr>");
					for (int j = 0; j < seiens[i].length; j++) {
						if (seiens[i][j].getDisponibilitat() == Estat.OCUPAT) {
							out.print("<td class=\"rojo\">"+(i+1)+","+(j+1)+"</td>");
						}else {
							out.print("<td class=\"verde\">"+(i+1)+","+(j+1)+"</td>");
						}			
					}
				}
				out.println("</table>");
				out.println("<br>");
				
				out.println("<div class=\"contenedor-entradas\">");
				for (int i = 0; i < numEntrades; i++) {				
					out.println("<div class=\"entrada-cine\">");
					out.println("<h2>Entrada del Cine Kineapolis Nº"+(i+1)+"</h2>");
					out.println("<div class=\"atributo\"><span class=\"etiqueta\">Nombre: </span>");
					out.println("<span class=\"valor\">"+peliculaSeleciona.getNomPeli()+"</span></div>");
					
					out.println("<div class=\"atributo\"><span class=\"etiqueta\">Horario: </span>");
					out.println("<span class=\"valor\">"+sessio.obtindreDataFormatada()+"</span></div>");
					
					out.println("<div class=\"atributo\"><span class=\"etiqueta\">SEIENT  </span>");
					out.println("<span class=\"valor\">FIla: "+(seientsAcomprar.get(i).getFilaSeient()+1)+"  -  Seient: "+(seientsAcomprar.get(i).getNumeroSeient()+1)+"</span></div>");
					
					out.println("<div class=\"atributo\"><span class=\"etiqueta\">Preu: </span>");
					out.println("<span class=\"valor\">"+sessio.getPreu()+"</span></div>");
					out.println("</div>");
				}
				out.println("</div>");
			}else {
				out.println("<p class=\"red\">NO pagat</p>");
				for (int i = 0; i < seientsAcomprar.size(); i++) {
					seientsAcomprar.get(i).alliberaSeient();
				}
				out.println("<table>");
				for (int i = 0; i < seiens.length; i++) {
					out.print("<tr>");
					for (int j = 0; j < seiens[i].length; j++) {
						if (seiens[i][j].getDisponibilitat() == Estat.OCUPAT) {
							out.print("<td class=\"rojo\">"+(i+1)+","+(j+1)+"</td>");
						}else {
							out.print("<td class=\"verde\">"+(i+1)+","+(j+1)+"</td>");
						}			
					}
				}
				out.println("</table>");
				out.println("<br>");
			}
			
			out.println("<br><br>");
			out.println("<form action=PeliculesServlet method=POST>");
			break;
		}
		
		if (ocultar) {
			out.println("<button type='submit' name='boton' value='Enrere' class=\"buttonOcult\">Enrere</button>");
		}else {
			out.println("<button type='submit' name='boton' value='Enrere' class=\"button\">Enrere</button>");

		}
		
		out.println("<button type='submit' name='boton' value='Seguent' class=\"button\">Següent</button>");
		out.println("<br><br>");
		out.println("<button type='submit' name='boton' value='Reinicia' class=\"button\">Reinicia Sessio</button>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");	
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
