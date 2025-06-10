package edu.unlu.truco.partida;

import java.util.LinkedList;

public class Partida extends Observado{
	boolean menuActivo;
	int cantoTruco = -1;
	int nivelTruco;
	int turno;
	int turnoActual;
	int rondaNumero;
	boolean rondaActiva;
	int cantidadPuntos;
	boolean flor;
	int cantidadJugadores;
	Jugador[] jugadores = new Jugador[2];
	LinkedList<TiposEnvido> listaEnvidos;
	boolean partidaIniciada;
	Mazo mazo;
	Mesa mesa;

	public Partida () {
		nivelTruco = 0;
		turno = 1;
		turnoActual = 0;
		rondaNumero = 0;
		rondaActiva = false;
		flor = false;
		cantidadPuntos = 30;
		cantidadJugadores = 0;
		partidaIniciada = false;
		listaEnvidos = new LinkedList<TiposEnvido> ();
		mazo = new Mazo();
		mesa = new Mesa();
	}
	
	public void agregarJugador (String nombre) throws Exception{
		Jugador jugadorAuxiliar;
		if (cantidadJugadores >= 2) {
			throw new Exception("Sala llena");
		}
		
		jugadorAuxiliar = new Jugador(nombre,cantidadJugadores);
		jugadores[cantidadJugadores] = jugadorAuxiliar;
		cantidadJugadores += 1;
		notificar("agregarJugador", nombre);
	}
	
	public Resultado getJugadores () throws Exception{
		Resultado resultado;
		String jugadorAuxiliar1,jugadorAuxiliar2;
		if (jugadores[0] != null) {
			jugadorAuxiliar1 = jugadores[0].getNombre();
		}
		else {
			jugadorAuxiliar1 = " ";
		}
		
		if (jugadores[1] != null) {
			jugadorAuxiliar2 = jugadores[1].getNombre();
		}
		else {
			jugadorAuxiliar2 = " ";
		}
		
		resultado = new Resultado (jugadorAuxiliar1,jugadorAuxiliar2);
		return resultado;
	}
	
	public void alternarFlor () {
		if (flor == true) {
			flor = false;
		}
		else {
			flor = true;
		}
		notificar("notificacionFlor", flor);
	}
	
	public boolean getRondaActiva () {
		return rondaActiva;
	}
	
	public int getTurnoActual () {
		return turnoActual;
	}
	
	public void notificarTurnoActual () {
		notificar("notificacionTurno", jugadores[turnoActual].getNombre());
	}
	
	public boolean iniciarRonda () throws Exception {
		if (cantidadJugadores < 2) {
			notificar ("enviarMensajeEspera", "Jugadores insuficientes");
			return false;
		}
		
		rondaActiva = true;
		mazo.barajarCartas();
		mesa.reiniciarMesa();
		listaEnvidos.clear();
		rondaNumero = 0;
		nivelTruco = 0;
		cantoTruco = -1;
		for (Jugador x:jugadores) {
			x.borrarCartas();
			mazo.entregarMano(x);
		}
		
		if (turno == 0) {
			turno = 1;
		}
		else {
			turno = 0;
		}

		turnoActual = turno;
		notificar("notificacionTurno", jugadores[turno].getNombre());

		return true;
	}
	
	public void entregarCartas (int numJugador) {
		notificar("mostrarCartas",jugadores[numJugador].getCartas());
	}
	
	public void mostrarMesa () {
		LinkedList<LinkedList<Carta>> resultadoAuxiliar;
		resultadoAuxiliar = mesa.getJugadas();
		notificar("mostrarMesa",resultadoAuxiliar);
	}
	
	public void jugarCarta (int jugador, int opcion) throws Exception{
		Carta cartaAuxiliar = jugadores[jugador].eliminarCarta(opcion);
		mesa.jugarCarta(cartaAuxiliar, jugador);
		menuActivo = false;
	}
	
	public void finTurno () {
		int resultado;
		rondaNumero += 1;
		//aca realizar logica cambio de turno
		if (rondaNumero % 2 == 0) {
			int ganador = mesa.ganadorRonda(rondaNumero/2);
			
			if  (ganador == 0) {
				turnoActual = 0;
			}else if (ganador == 1) {
				turnoActual = 1;
			}
			else {
				if (turnoActual == 0) {
					turnoActual = 1;
				}
				else {
					turnoActual = 0;
				}
			}
		}
		else {
			if (turnoActual == 0) {
				turnoActual = 1;
			}
			else {
				turnoActual = 0;
			}
		}
		
		resultado = calcularPuntos ();
		
		if (resultado == -1) {
			notificar("notificacionTurno", jugadores[turnoActual].getNombre());
		}
		else {
			int puntos = 1;
			
			if (nivelTruco == 1) {
				puntos = 2;
			}
			else if (nivelTruco == 2) {
				puntos = 3;
			}
			else if (nivelTruco == 3) {
				puntos = 4;
			}
			jugadores[resultado].sumarPuntos(puntos);
			rondaActiva = false;
			if (verificarFinPartida ()) {
				
			}
			else {
				notificar("enviarMensaje","Ronda terminada!");
				notificar("mostrarPuntaje",new Resultado (jugadores[0],jugadores[1]));
				notificar("enviarMensajeEspera","Enter para comenzar la siguiente ronda");
			}
		}
	}
	
	private int calcularPuntos () {
		int puntosJug1 = 0;
		int puntosJug2 = 0;
		boolean parda1 = false;
		boolean parda2 = false;
		boolean parda3 = false;
		LinkedList<LinkedList<Carta>> manos = mesa.getJugadas();
		LinkedList<Carta> manoJug1 = manos.get(0);
		LinkedList<Carta> manoJug2 = manos.get(1);
		

		
		if (manoJug1.size() >= 2 && manoJug2.size() >= 2 && manoJug1.size() == manoJug2.size()) {
			for (int x=0;x!=manoJug1.size();x+=1) {
				Carta cartaAuxiliar1 = manoJug1.get(x);
				Carta cartaAuxiliar2 = manoJug2.get(x);
				
				if (cartaAuxiliar1.getValor() > cartaAuxiliar2.getValor()) {
					if (parda1 || parda2) {
						puntosJug1 += 2;
					}
					else {
						puntosJug1 += 1;
					}
				}
				else if (cartaAuxiliar1.getValor() < cartaAuxiliar2.getValor()) {
					if (parda1 || parda2) {
						puntosJug2 += 2;
					}
					else {
						puntosJug2 += 1;
					}
				}
				else {
					if (x == 0) {
						parda1 = true;
					}
					else if (x == 1) {
						parda2 = true;
						
					}
					else if (x == 2) {
						parda3 = true;
					}
				}
				
			}
			
			if (parda3) {
				if (turno == 0) {
					puntosJug1 += 2;
				}
				else {
					puntosJug2 += 2;
				}
			}
			
			if (puntosJug1 >= 2 || puntosJug2 >= 2) {
				if (puntosJug1 > puntosJug2) {
					return 0;
				}
				else {
					return 1;
				}
			}
		}
		
		return -1;
	}
	
	public boolean getPartidaIniciada () {
		return partidaIniciada;
	}
	
	public void activarPartida () {
		partidaIniciada = true;
	}
	
	public void desactivarPartida () {
		partidaIniciada = false;
	}
	
	private boolean verificarFinPartida () {
		for (Jugador x:jugadores) {
			if (x.getPuntos() >= cantidadPuntos) {
				partidaIniciada = false;
				notificar ("mostrarFinPartida", x);
				notificar("mostrarPuntaje",new Resultado (jugadores[0],jugadores[1]));
				menuActivo = false;
				rondaActiva = false;
				desactivarPartida();
				return true;
			}
		}
		return false;
	}
	
	public boolean[] verificarJugadasValidas (int numeroJugador) {
		boolean[] jugadasValidas = new boolean[3];
		if (cantoTruco != numeroJugador && nivelTruco != 3) {
			jugadasValidas[0] = true;
		}
		else {
			jugadasValidas[0] = false;
		}
		
		if (rondaNumero == 0 || rondaNumero == 1) {
			jugadasValidas[1] = true;
		}
		else {
			jugadasValidas[1] = false;
		}
		
		if ((turnoActual == 0 || turnoActual == 1) && flor == true) {
			jugadasValidas[2] = true;
		}
		else {
			jugadasValidas[2] = false;
		}

		return jugadasValidas;
	}
	
	public TiposTruco getNivelTruco () {
		switch (nivelTruco) {
			case 1:
				return TiposTruco.Truco;
				
			case 2:
				return TiposTruco.Retruco;
			
			case 3:
				return TiposTruco.ValeCuatro;
		}
		return null;
	}
	
	public void subirNivelTruco () {
		nivelTruco += 1;
	}
	
	public void cantoTruco () {
		if (cantoTruco == -1) {
			cantoTruco = turnoActual;
		}
		else if (cantoTruco == 1) {
			cantoTruco = 0;
		}
		else {
			cantoTruco = 1;
		}
	}
	
	public void trucoRechazado () {
		int valorTruco;
		menuActivo = false;
		switch (nivelTruco) {
			case 1:
				valorTruco = 1;
				break;
				
			case 2:
				valorTruco = 2;
				break;
			
			case 3:
				valorTruco = 3;
				break;
				
			default:
				valorTruco = 1;
				break;
		}

		jugadores[cantoTruco].sumarPuntos(valorTruco);
		if (verificarFinPartida ()) {
			
		}
		else {
			rondaActiva = false;
			notificar("enviarMensaje","Truco rechazado");
			notificar("mostrarPuntaje",new Resultado (jugadores[0],jugadores[1]));
			notificar("enviarMensajeEspera","\nPasando al siguiente turno");
		}
		
	}
	
	public boolean[] verificarEnvidoValido () {
		boolean resultado[] = new boolean[3];
		int contadorEnvido = 0;
		int contadorRealEnvido = 0;
		int contadorFaltaEnvido = 0;
		
		for (TiposEnvido x:listaEnvidos) {
			if (x == TiposEnvido.Envido) {
				contadorEnvido += 1;
			}
			else if (x == TiposEnvido.RealEnvido) {
				contadorRealEnvido += 1;
			}
			else {
				contadorFaltaEnvido += 1;
			}
		}
		
		if (contadorEnvido < 2) {
			resultado[0] = true;
		}
		else {
			resultado[0] = false;
		}
		if (contadorRealEnvido < 1) {
			resultado[1] = true;
		}
		else {
			resultado[1] = false;
		}
		if (contadorFaltaEnvido < 1) {
			resultado[2] = true;
		}
		else {
			resultado[2] = false;
		}
		return resultado;
	}
	
	public void cantarEnvido (TiposEnvido envido) {
		listaEnvidos.add(envido);
	}
	
	
	public void aceptarEnvido () {
		int envidoJugadorUno, envidoJugadorDos, ganador, puntos, equipoGanador = 0;
		boolean faltaEnvido = false;
		envidoJugadorUno = jugadores[0].getPuntosEnvido();
		envidoJugadorDos = jugadores[1].getPuntosEnvido();
		
		if (envidoJugadorUno > envidoJugadorDos) {
			ganador = 0;
		}
		else if (envidoJugadorUno < envidoJugadorDos) {
			ganador = 1;
		}
		else {
			if (turno == 0) {
				ganador = 0;
			}
			else {
				ganador = 1;
			}
		}
		
		puntos = 0;
		
		for (TiposEnvido x:listaEnvidos) {
			if (x == TiposEnvido.Envido) {
				puntos += 2;
			}
			else if (x == TiposEnvido.RealEnvido) {
				puntos += 3;
			}
			else {
				if (jugadores[0].getPuntos() >= jugadores[1].getPuntos()) {
					equipoGanador = 0;
				}
				else {
					equipoGanador = 1;
				}
				faltaEnvido = true;
				puntos = jugadores[equipoGanador].getPuntos() - cantidadPuntos;
				break;
			}
		}
		
		if (faltaEnvido) {
			if (ganador == equipoGanador) {
				jugadores[ganador].sumarPuntos(cantidadPuntos);
			}
			else {
				jugadores[ganador].sumarPuntos(puntos);
			}
		}
		else {
			jugadores[ganador].sumarPuntos(puntos);
		}
		
		if (verificarFinPartida()) {
			
		}
		
	}
	
	public void rechazarEnvido () {
		
	}
	
	public boolean iniciarPartida () throws Exception {
		activarPartida();
		while (getPartidaIniciada()) {
			
			if (!iniciarRonda()) {
				desactivarPartida();
				return false;
			}
			desarrolloRonda();
		}
		return true;
	}
	
	private void desarrolloRonda () throws Exception {
		while (getRondaActiva()) {
			menuActivo = true;
			turnoActual = getTurnoActual();
			System.out.println (turnoActual);
			while (menuActivo) {
				mostrarMesa();
				entregarCartas(turnoActual);
				notificar("mostrarMenuRonda", 0);
			}
			
			if (getRondaActiva()) {
				notificar("enviarMensajeEspera","\nPasando al siguiente turno");
				notificar("limpiarConsola",0);
				finTurno();
			}

		}
	}
}
