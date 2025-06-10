package edu.unlu.truco.partida;

public class Resultado {
	Object jugadorUno;
	Object jugadorDos;
	
	public Resultado (Object j1, Object j2) {
		jugadorUno = j1;
		jugadorDos = j2;
	}
	
	public Object getUno () {
		return jugadorUno;
	}
	
	public Object getDos () {
		return jugadorDos;
	}
}
