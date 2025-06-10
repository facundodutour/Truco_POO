package edu.unlu.truco.partida;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

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
	    	vista.mostrarMensaje ("Partida terminada!\n El ganador es " + datosAuxiliar.getNombre());
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
	    
	    case "cantarTruco": {
	    	
	    }
	    
	    case "limpiarConsola": {
	    	vista.limpiarConsola();
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
	
	public void seleccionarCarta () {
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
		vista.mostrarMensajeEspera("Cantaste " + queTruco + ". ¡Enter para dejarlo elegir al otro jugador!");
		vista.limpiarConsola();

		partida.cantoTruco();
		
		while (trucoActivo) {
			vista.mostrarMensaje("Te cantaron " + queTruco);
			vista.mostrarMensaje("¿Que vas a hacer?");
			vista.mostrarMenuTruco(nivelTruco);
			opcion = VistaPartida.leerNumero();
			
			switch (opcion) {
				case 1:
					trucoActivo = false;
					partida.subirNivelTruco();
					partida.notificarTurnoActual();
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
		vista.mostrarMensajeEspera("Cantaste " + envido + ". ¡Enter para dejarlo elegir al otro jugador!");
		vista.limpiarConsola();
		while (envidoActivo) {
			vista.mostrarMenuEnvido2(jugadasValidas, envido);
			opcion = VistaPartida.leerNumero();
			switch (opcion) {
				
				case 1:
					envidoActivo = false;
					break;
				case 2:
					envidoActivo = false;
					break;
				case 3:
					if (jugadasValidas[0] == true) {
						envidoActivo = false;
						partida.cantarEnvido(TiposEnvido.Envido);
						envido("envido");
					}
					else {
						vista.mostrarMensajeEspera("Ya fue cantado!");
					}
					
					break;
				case 4:
					if (jugadasValidas[1] == true) {
						envidoActivo = false;
						partida.cantarEnvido(TiposEnvido.RealEnvido);
						envido("real envido");
					}
					else {
						vista.mostrarMensajeEspera("Ya fue cantado!");
					}
					break;
				case 5:	
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
	
	private void flor () {
		
	}
	
	public boolean checkCantarTruco () {
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
	
	public void checkCantarEnvido () {
		boolean[] jugadasValidas;
		jugadasValidas = partida.verificarJugadasValidas(partida.getTurnoActual());
		if (jugadasValidas[1] == true) {
			cantarEnvido ();
		}
		else {
			vista.mostrarMensajeEspera("Jugada no valida!");
		}
	}
	
	public void checkCantarFlor () {
		boolean[] jugadasValidas;
		jugadasValidas = partida.verificarJugadasValidas(partida.getTurnoActual());
		if (jugadasValidas[2] == true) {
			flor ();
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
					break;
					
				case 4:
					checkCantarFlor();
					break;
				default:
					vista.mostrarMensajeEspera("Opción no valida!");
					
			}
		}
	}

}
