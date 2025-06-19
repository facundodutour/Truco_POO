package edu.unlu.truco.partida.Modelo;

public class Carta {
	private int numero;
	private Palos palo;
	private int valor;
	
	
	public Carta (int numero, Palos palo) {
		this.numero = numero;
		this.palo = palo;
		valorCarta(palo,numero);
	}
	
	public int getNumero () {
		return numero;
	}
	
	public Palos getPalo () {
		return palo;
	}
	
	public int getValor () {
		return valor;
	}

	private void valorCarta (Palos palo, int numero) {
		if (numero == 3) {
			valor = 15;
		}
		else if (numero == 2) {
			valor = 14;
		}
		else {
			valor = numero;
		}
		
		if (palo == Palos.ESPADA && numero == 1) {
			valor = 19;
		}
		else if (palo == Palos.BASTO && numero == 1) {
			valor = 18;
		}
		else if (palo == Palos.ESPADA && numero == 7) {
			valor = 17;
		}
		else if (palo == Palos.ORO && numero == 7) {
			valor = 16;
		}
		else if ((palo == Palos.COPA || palo == Palos.ORO) && numero == 1) {
			valor = 13;
		}
		else if ((palo == Palos.COPA || palo == Palos.BASTO) && numero == 7) {
			valor = 7;
		}
	}
}
