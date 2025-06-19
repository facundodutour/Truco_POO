package edu.unlu.truco.partida.Modelo;

public class Resultado {
	Object jugadorUno;
	Object jugadorDos;
	int ganadorEnvido, p1, p2;
	
	public Resultado (Object j1, Object j2) {
		jugadorUno = j1;
		jugadorDos = j2;
	}
	
	public Resultado (Object j1, Object j2, int ganadorEnvido, int p1, int p2) {
		jugadorUno = j1;
		jugadorDos = j2;
		this.ganadorEnvido = ganadorEnvido;
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public Object getUno () {
		return jugadorUno;
	}
	
	public Object getDos () {
		return jugadorDos;
	}
	
	public int getGanadorEnvido () {
		return ganadorEnvido;
	}
	
	public int getP1 () {
		return p1;
	}
	
	public int getP2 () {
		return p2;
	}
}
