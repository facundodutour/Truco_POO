package edu.unlu.truco.partida.Modelo;

import java.util.LinkedList;

public class Mesa {
	LinkedList<Carta> mesaJugadorUno;
	LinkedList<Carta> mesaJugadorDos;
	
	public Mesa () {
		mesaJugadorUno = new LinkedList<Carta>();
		mesaJugadorDos = new LinkedList<Carta>();
	}
	
	public LinkedList<LinkedList<Carta>> getJugadas () {
		LinkedList<LinkedList<Carta>> listaAuxiliar = new LinkedList<LinkedList<Carta>>();
		listaAuxiliar.add(new LinkedList<Carta>(this.mesaJugadorUno));
		listaAuxiliar.add(new LinkedList<Carta>(this.mesaJugadorDos));
		return listaAuxiliar;
	}
	
	public void jugarCarta (Carta carta, int jugador) throws Exception {
		if (jugador == 0) {
			if (mesaJugadorUno.size()>=3) {
				throw new Exception ("Error");
			}
			mesaJugadorUno.add(carta);
		}
		else if (jugador == 1) {
			if (mesaJugadorDos.size()>=3) {
				throw new Exception ("Error");
			}
			mesaJugadorDos.add(carta);
		}
		else {
			throw new Exception ("Jugador no valido");
		}
	}
	
	public void reiniciarMesa () {
		mesaJugadorUno.clear();
		mesaJugadorDos.clear();
	}
	
	public int ganadorRonda (int numeroRonda) {
		Carta cartaAuxiliar1, cartaAuxiliar2;
		
		if (mesaJugadorUno.size() < numeroRonda || mesaJugadorDos.size() < numeroRonda) {
			return -1;
		}
		
		cartaAuxiliar1 = mesaJugadorUno.get(numeroRonda-1);
		cartaAuxiliar2 = mesaJugadorDos.get(numeroRonda-1);
		
		if (cartaAuxiliar1.getValor() > cartaAuxiliar2.getValor()) {
			return 0;
		}
		else if (cartaAuxiliar1.getValor() < cartaAuxiliar2.getValor()) {
			return 1;
		}
		else {
			return -1;
		}
		
	}
	
	
}
