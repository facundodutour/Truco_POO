package edu.unlu.truco.partida.Modelo;

import java.util.LinkedList;
import java.util.Random;

public class Mazo {
	LinkedList<Carta> mazo;
	LinkedList<Carta> mazoAuxiliar;
	Random randomModulo = new Random();
	
	public Mazo () {
		mazo = new LinkedList<Carta>();
		mazoAuxiliar = new LinkedList<Carta>();
		this.generarCartas();
	}
	
	private void generarCartas () {
		Carta cartaAuxiliar;
		if (mazo.size() != 0 || this.mazoAuxiliar.size() != 0) {
			mazo.clear();
			mazoAuxiliar.clear();
		}
		for (Palos palo:Palos.values()) {
			for (int x=1;x!=13;x+=1) {
				if (x==8 || x==9) {

				}
				else {
					cartaAuxiliar = new Carta(x,palo);
					mazo.add(cartaAuxiliar);
				}
			}
		}
	} 
	
	public void entregarMano (Jugador jugador) throws Exception {
		int numeroAleatorio;
		Carta cartaAuxiliar;
		
		for (int x=0;x!=3;x+=1) {
			numeroAleatorio = randomModulo.nextInt(this.mazo.size()-1);
			cartaAuxiliar = mazo.remove(numeroAleatorio);
			mazoAuxiliar.add (cartaAuxiliar);
			jugador.agregarCarta(cartaAuxiliar);
		}
	}
	
	public void barajarCartas () {
		while (!mazoAuxiliar.isEmpty()) {
		    Carta cartaAuxiliar = mazoAuxiliar.pollFirst();
		    mazo.add(cartaAuxiliar);
		}

		if (mazo.size() != 40 || mazoAuxiliar.size() != 0) {
			generarCartas();
		}
	}
}
