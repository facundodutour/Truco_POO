package edu.unlu.truco.partida.Controlador;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import edu.unlu.truco.partida.Modelo.Carta;
import edu.unlu.truco.partida.Modelo.Jugador;
import edu.unlu.truco.partida.Modelo.Observador;
import edu.unlu.truco.partida.Modelo.Partida;
import edu.unlu.truco.partida.Modelo.Resultado;
import edu.unlu.truco.partida.Modelo.TiposEnvido;
import edu.unlu.truco.partida.Modelo.TiposFlor;
import edu.unlu.truco.partida.Modelo.TiposTruco;
import edu.unlu.truco.partida.Vista.VistaPartida;

public class Controlador implements Observador{
	private Partida partida;
	Scanner scanner = new Scanner(System.in);
	private VistaPartida vista;
	public Controlador(Partida p) {
		partida = p;
		vista = new VistaPartida();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void actualizar(String evento, Object datos) {

		switch (evento) {

	    case "agregarJugador": {
	        vista.mostrarMensajeEspera("Jugador " + datos + " agregado");
	        break;
	    }

	    case "notificacionFlor": {
	        boolean florActiva = (boolean) datos;
	        String estadoFlor;
	        if (florActiva) {
	        	estadoFlor = "activada";
	        }
	        else {
	        	estadoFlor = "desactivada";
	        }
	        vista.mostrarMensajeEspera("La flor está " + estadoFlor);
	        break;
	    }

	    case "notificacionTurno": {
	    	vista.limpiarConsola ();
	        vista.mostrarMensajeEspera("Es el turno de " + datos + "\n¡Solo puede mirar él!");
	        break;
	    }

	    case "mostrarCartas": {
	        List<Carta> datosAuxiliar = (List<Carta>) datos;
	        int contador = 1;
	        vista.mostrarMensaje("Tus cartas son: ");
	        for (Carta x : datosAuxiliar) {
	            vista.mostrarMensaje(contador + ". " + x.getNumero() + " de " + x.getPalo());
	            contador++;
	        }
	        break;
	    }

	    case "mostrarMesa": {
	        int turnoAuxiliar;
	        int turnoActual = partida.getTurnoActual();
	        if (partida.getTurnoActual() == 1) {
	        	turnoAuxiliar = 0;
	        }
	        else {
	        	turnoAuxiliar = 1;
	        }
	        
	        LinkedList<LinkedList<Carta>> datosAuxiliar = (LinkedList<LinkedList<Carta>>) datos;

	        LinkedList<Carta> tusCartas = datosAuxiliar.get(turnoActual);
	        LinkedList<Carta> susCartas = datosAuxiliar.get(turnoAuxiliar);

	        int contador = 1;
	        vista.mostrarMensaje("Mesa:");
	        vista.mostrarMensaje("Tus tiradas:");
	        for (Carta x : tusCartas) {
	            vista.mostrarMensaje(contador + ". " + x.getNumero() + " " + x.getPalo());
	            contador++;
	        }

	        contador = 1;
	        vista.mostrarMensaje("\nSus tiradas:");
	        for (Carta x : susCartas) {
	            vista.mostrarMensaje(contador + ". " + x.getNumero() + " " + x.getPalo());
	            contador++;
	        }
	        vista.lineaDivision ();
	        break;
	    }
	    
	    case "mostrarPuntaje":{
	    	Resultado datosAuxiliar = (Resultado) datos;
	    	Jugador jugAuxiliar1 = (Jugador) datosAuxiliar.getUno();
	    	Jugador jugAuxiliar2 = (Jugador) datosAuxiliar.getDos();
	    	vista.lineaDivision();
	    	vista.mostrarMensaje ("Puntuacion:");
	    	vista.mostrarMensaje (jugAuxiliar1.getNombre() + ": " + jugAuxiliar1.getPuntos());
	    	vista.mostrarMensaje (jugAuxiliar2.getNombre() + ": " + jugAuxiliar2.getPuntos());
	    	vista.lineaDivision();
	    	break;
	    }
	    
	    case "mostrarFinPartida": {
	    	Jugador datosAuxiliar = (Jugador) datos;
	    	vista.lineaDivision();
	    	vista.mostrarMensaje ("\nPartida terminada!\n El ganador es " + datosAuxiliar.getNombre());
	    	break;
	    }
	    
	    case "mostrarMenuRonda": {
	    	menuPrincipal();
	    	break;
	    }
	    
	    case "enviarMensaje": {
	    	vista.mostrarMensaje ((String)datos);
	    	break;
	    }
	    
	    case "enviarMensajeEspera": {
	    	vista.mostrarMensajeEspera ((String)datos);
	    	break;
	    }
	    
	    case "cantarFlor": {
	    	flor ("flor");
	    	break;
	    }
	    
	    case "limpiarConsola": {
	    	vista.limpiarConsola();
	    	break;
	    }
	    
	    case "notificarEnvidoAceptado": {
	    	Resultado datosAuxiliar = (Resultado) datos;
	    	Jugador jugAuxiliar1 = (Jugador) datosAuxiliar.getUno();
	    	Jugador jugAuxiliar2 = (Jugador) datosAuxiliar.getDos();
	    	vista.limpiarConsola();
	    	vista.mostrarMensaje("Envido aceptado!");
	    	if (partida.getTurno() == 0 ) {
	    		if (datosAuxiliar.getGanadorEnvido() == 0){
	    			vista.mostrarMensaje(jugAuxiliar1.getNombre() + ": " + datosAuxiliar.getP1());
	    			vista.mostrarMensaje(jugAuxiliar2.getNombre() + ": Son buenas.");
	    		}
	    		else {
	    			vista.mostrarMensaje(jugAuxiliar1.getNombre() + ": " + datosAuxiliar.getP1());
	    			vista.mostrarMensaje(jugAuxiliar2.getNombre() + ": " + datosAuxiliar.getP2() + " son mejores.");
	    		}
	    		
	    	}else {
	    		if (datosAuxiliar.getGanadorEnvido() == 1){
	    			vista.mostrarMensaje(jugAuxiliar2.getNombre() + ": " + datosAuxiliar.getP2());
	    			vista.mostrarMensaje(jugAuxiliar1.getNombre() + ": Son buenas.");
	    		}
	    		else {
	    			vista.mostrarMensaje(jugAuxiliar2.getNombre() + ": " + datosAuxiliar.getP2());
	    			vista.mostrarMensaje(jugAuxiliar1.getNombre() + ": " + datosAuxiliar.getP1() + " son mejores.");
	    		}
	    	}
	    	break;
	    }
	    
	    case "notificarFlorAceptado": {
	    	Resultado datosAuxiliar = (Resultado) datos;
	    	Jugador jugAuxiliar1 = (Jugador) datosAuxiliar.getUno();
	    	Jugador jugAuxiliar2 = (Jugador) datosAuxiliar.getDos();
	    	vista.mostrarMensaje("Flor aceptada!");
	    	if (partida.getTurno() == 0 ) {
	    		if (datosAuxiliar.getGanadorEnvido() == 0){
	    			vista.mostrarMensaje(jugAuxiliar1.getNombre() + ": " + datosAuxiliar.getP1());
	    			vista.mostrarMensaje(jugAuxiliar2.getNombre() + ": Son buenas.");
	    		}
	    		else {
	    			vista.mostrarMensaje(jugAuxiliar1.getNombre() + ": " + datosAuxiliar.getP1());
	    			vista.mostrarMensaje(jugAuxiliar2.getNombre() + ": " + datosAuxiliar.getP2() + " son mejores.");
	    		}
	    		
	    	}else {
	    		if (datosAuxiliar.getGanadorEnvido() == 1){
	    			vista.mostrarMensaje(jugAuxiliar2.getNombre() + ": " + datosAuxiliar.getP2());
	    			vista.mostrarMensaje(jugAuxiliar1.getNombre() + ": Son buenas.");
	    		}
	    		else {
	    			vista.mostrarMensaje(jugAuxiliar2.getNombre() + ": " + datosAuxiliar.getP2());
	    			vista.mostrarMensaje(jugAuxiliar1.getNombre() + ": " + datosAuxiliar.getP1() + " son mejores.");
	    		}
	    	}
	    	break;
	    }
	    
	    case "notificarEnvidoRechazado": {
	    	vista.limpiarConsola();
	    	vista.mostrarMensaje("Envido rechazado!");
	    	break;
	    }

	    case "notificarFlorRechazada": {
	    	vista.limpiarConsola();
	    	vista.mostrarMensaje("Flor rechazada!");
	    	break;
	    }
	    
	    case "notificarIrseMazo": {
	    	vista.limpiarConsola();
	    	vista.mostrarMensaje((String)datos + " se fue al mazo!");
	    	break;
	    }
	    
	    default: {
	    	vista.mostrarMensajeEspera("Evento desconocido: " + evento);
	        break;
	    }
	}

		
		
	}
	
	public boolean iniciarJuego () throws Exception {
		if (partida.iniciarPartida()) {
			return true;
		}
		return false;
	}
	
	
	public void añadirJugador () throws Exception {
		String nombreAuxiliar;
		vista.mostrarMensaje("Ingrese el nombre del jugador: ");
		nombreAuxiliar = vista.leerString();
		try {
			partida.agregarJugador(nombreAuxiliar);
		} catch (Exception e) {
			vista.mostrarMensajeEspera("Excepción: \nSala llena");
		}
	}
	
	public void mostrarJugadores () throws Exception {
		try {
			Resultado resultado = partida.getJugadores();
			vista.mostrarMensajeEspera("Lista de jugadores:\n"+resultado.getUno()+"\n"+resultado.getDos());
		}
		catch (Exception e) {
			vista.mostrarMensajeEspera("No hay jugadores en la sala");
		}
	}
	
	public void alternarFlor () {
		partida.alternarFlor();
	}
	
	private void seleccionarCarta () {
		boolean menuActivo = true;
		int opcion;
		while (menuActivo) {
			vista.mostrarMensaje("Selecciona que carta tirar: ");
			opcion = VistaPartida.leerNumero();
			try {
				partida.jugarCarta(partida.getTurnoActual(), opcion);
				menuActivo = false;
			} catch (Exception e) {
				vista.mostrarMensaje("Opcion no valida");
			}
		}
	}
	
	private boolean truco () {
		int opcion;
		boolean trucoActivo = true;
		TiposTruco nivelTruco;
		String queTruco = "truco";
		nivelTruco = partida.getNivelTruco ();
		if (nivelTruco == null) {
			queTruco = "truco";
		}
		else if (nivelTruco == TiposTruco.Truco) {
			queTruco = "retruco";
		}
		else if (nivelTruco == TiposTruco.Retruco) {
			queTruco = "valecuatro";
		}
		vista.limpiarConsola();
		vista.mostrarMensajeEspera("Cantaste " + queTruco + ". ¡Enter para dejarlo elegir al otro jugador!");
		vista.limpiarConsola();

		partida.cantoTruco();
		
		while (trucoActivo) {
			vista.mostrarMensaje("Te cantaron " + queTruco);
			vista.mostrarMensaje("¿Que vas a hacer?\n");
			partida.mostrarCartasTruco();
			vista.mostrarMenuTruco(nivelTruco);
			opcion = VistaPartida.leerNumero();
			
			switch (opcion) {
				case 1:
					trucoActivo = false;
					partida.trucoAceptado();
					break;
				
				case 2:
					trucoActivo = false;
					partida.subirNivelTruco();
					partida.trucoRechazado();
					return false;
					
				case 3:
					trucoActivo = false;
					partida.subirNivelTruco();
					return truco();
					
				default:
					vista.mostrarMensajeEspera("Opcion no valida");
			}
		}
		return true;
		
	}
	
	private void envido (String envido) {
		int opcion;
		boolean envidoActivo = true;
		boolean[] jugadasValidas = partida.verificarEnvidoValido();
		vista.limpiarConsola();
		vista.mostrarMensajeEspera("Cantaste " + envido + ". ¡Enter para dejarlo elegir al otro jugador!");
		vista.limpiarConsola();
		while (envidoActivo) {
			partida.mostrarCartasEnvido();
			vista.mostrarMenuEnvido2(jugadasValidas, envido);
			opcion = VistaPartida.leerNumero();
			switch (opcion) {
				
				case 1:
					partida.aceptarEnvido();
					envidoActivo = false;
					break;
				case 2:
					partida.rechazarEnvido();
					envidoActivo = false;
					break;
				case 3:
					if (jugadasValidas[0] == true) {
						envidoActivo = false;
						partida.cantarEnvido(TiposEnvido.Envido);
						envido((envido + " + " + "envido"));
					}
					else {
						vista.mostrarMensajeEspera("Ya fue cantado!");
					}
					
					break;
				case 4:
					if (jugadasValidas[1] == true) {
						envidoActivo = false;
						partida.cantarEnvido(TiposEnvido.RealEnvido);
						envido((envido + " + " + "real envido"));
					}
					else {
						vista.mostrarMensajeEspera("Ya fue cantado!");
					}
					break;
				case 5:	
					if (jugadasValidas[2] == true) {
						envidoActivo = false;
						partida.cantarEnvido(TiposEnvido.FaltaEnvido);
						envido((envido + " + " + "falta envido"));
					}
					else {
						vista.mostrarMensajeEspera("Ya fue cantado!");
					}
					break;
				default:
					vista.mostrarMensajeEspera("Jugada no valida");
			}
		}
	}
	
	private void cantarEnvido () {
		int opcion;
		boolean envidoActivo = true;
		boolean[] jugadasValidas = partida.verificarEnvidoValido();
		while (envidoActivo) {
			vista.mostrarMenuEnvido1();
			opcion = VistaPartida.leerNumero();
			
			switch (opcion) {
				case 1:
					if (jugadasValidas[0] == true) {
						envidoActivo = false;
						partida.cantarEnvido(TiposEnvido.Envido);
						envido("envido");
					}
					else {
						vista.mostrarMensajeEspera("Ya fue cantado!");
					}
					break;
				case 2:
					if (jugadasValidas[1] == true) {
						envidoActivo = false;
						partida.cantarEnvido(TiposEnvido.RealEnvido);
						envido("real envido");
					}
					else {
						vista.mostrarMensajeEspera("Ya fue cantado!");
					}
					break;
				case 3:
					if (jugadasValidas[2] == true) {
						envidoActivo = false;
						partida.cantarEnvido(TiposEnvido.FaltaEnvido);
						envido("falta envido");
					}
					else {
						vista.mostrarMensajeEspera("Ya fue cantado!");
					}
					break;
				default:
					vista.mostrarMensaje("Opcion no valida");
			}
		}
	}
	
	private void flor (String flor) {
		int opcion;
		boolean florActivo = true;
		boolean[] jugadasValidas = partida.verificarFlorValido();
		vista.limpiarConsola();
		vista.mostrarMensajeEspera("Cantaste " + flor + ". ¡Enter para dejarlo elegir al otro jugador!");
		vista.limpiarConsola();
		while (florActivo) {
			partida.mostrarCartasFlor();
			vista.mostrarMenuFlor(jugadasValidas, flor);
			opcion = VistaPartida.leerNumero();
			switch (opcion) {
				
				case 1:
					//aceptarflor
					if (!jugadasValidas[1] || !jugadasValidas[2]) {
						partida.aceptarFlor();
						florActivo =  false;
					}
					else {
						vista.mostrarMensajeEspera("Jugada no valida!");
					}
					break;
				case 2:
					partida.rechazarFlor();
					florActivo =  false;

					break;
				case 3:
					if (jugadasValidas[1] == true) {
						florActivo = false;
						partida.cantarFlor(TiposFlor.ContraFlor);
						flor((flor + " + " + "contra flor"));
					}
					else {
						vista.mostrarMensajeEspera("Ya fue cantado!");
					}
					break;
				case 4:
					if (jugadasValidas[2] == true) {
						florActivo = false;
						partida.cantarFlor(TiposFlor.ContraFlorResto);
						flor((flor + " + " + "contra flor al resto"));
					}
					else {
						vista.mostrarMensajeEspera("Ya fue cantado!");
					}
					break;
					
				default:
					vista.mostrarMensajeEspera("Jugada no valida");
			}
		}
	}
	
	private boolean checkCantarTruco () {
		boolean[] jugadasValidas;
		jugadasValidas = partida.verificarJugadasValidas(partida.getTurnoActual());
		if (jugadasValidas[0] == true) {
			return truco();
		}
		else {
			vista.mostrarMensajeEspera("Jugada no valida!");
		}
		return true;
	}
	
	private void checkCantarEnvido () {
		boolean[] jugadasValidas;
		jugadasValidas = partida.verificarJugadasValidas(partida.getTurnoActual());
		if (jugadasValidas[1] == true) {
			cantarEnvido ();
		}
		else {
			vista.mostrarMensajeEspera("Jugada no valida!");
		}
	}
	
	private void checkCantarFlor () {
		boolean[] jugadasValidas;
		jugadasValidas = partida.verificarJugadasValidas(partida.getTurnoActual());
		if (jugadasValidas[2] == true) {
			partida.cantarFlor(TiposFlor.Flor);
		}
		else {
			vista.mostrarMensajeEspera("Jugada no valida!");
		}
	}
	
	private void menuPrincipal () {
		boolean menuActivo = true;
		while (menuActivo) {
			vista.mostrarMenuRonda(partida.getNivelTruco());
			int opcion = VistaPartida.leerNumero();
			switch (opcion) {
				case 1:
					seleccionarCarta();
					menuActivo = false;
					break;
					
				case 2:
					if (!checkCantarTruco()) {
						menuActivo = false;
					}
					break;
					
				case 3:
					checkCantarEnvido();
					if (!partida.getPartidaIniciada()) {
						menuActivo = false;
					}
					break;
					
				case 4:
					checkCantarFlor();
					if (!partida.getPartidaIniciada()) {
						menuActivo = false;
					}
					break;
				
				case 5:
					partida.irseMazo();
					menuActivo = false;
					break;
				default:
					vista.mostrarMensajeEspera("Opción no valida!");
					
			}
		}
	}

}
