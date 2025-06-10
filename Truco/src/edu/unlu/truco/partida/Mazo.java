package edu.unlu.truco.partida;

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
		if (this.mazo.size() != 0 || this.mazoAuxiliar.size() != 0) {
			this.mazo.clear();
			this.mazoAuxiliar.clear();
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
	
	public LinkedList<Carta> getMazo (){
		return new LinkedList<Carta>(this.mazo);
	}
	
	public Carta getCartaAleatoria () {
		int numeroAleatorio;
		Carta cartaAuxiliar;
		
		numeroAleatorio = randomModulo.nextInt(this.mazo.size()-1);
		cartaAuxiliar = this.mazo.remove(numeroAleatorio);
		this.mazoAuxiliar.add (cartaAuxiliar);
		return cartaAuxiliar;
	}
	
	public void entregarMano (Jugador jugador) throws Exception {
		int numeroAleatorio;
		Carta cartaAuxiliar;
		
		for (int x=0;x!=3;x+=1) {
			numeroAleatorio = randomModulo.nextInt(this.mazo.size()-1);
			cartaAuxiliar = this.mazo.remove(numeroAleatorio);
			this.mazoAuxiliar.add (cartaAuxiliar);
			jugador.agregarCarta(cartaAuxiliar);
		}
	}
	
	public void barajarCartas () {
		while (!mazoAuxiliar.isEmpty()) {
		    Carta cartaAuxiliar = mazoAuxiliar.pollFirst();
		    mazo.add(cartaAuxiliar);
		}

		if (this.mazo.size() != 40 || this.mazoAuxiliar.size() != 0) {
			this.generarCartas();
		}
	}
	
	public void mostrarMazo () {
		for (Carta x:mazo) {
			System.out.println (x.getNumero() + " " + x.getPalo() + " " + x.getValor());
		}
	}
}
