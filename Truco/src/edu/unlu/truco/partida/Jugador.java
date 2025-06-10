package edu.unlu.truco.partida;

import java.util.LinkedList;
import java.util.List;

public class Jugador {
	private String nombre;
	private int numeroJugador;
	private int puntos;
	private List<Carta> mano;
	
	public Jugador (String nombre, int numeroJugador) {
		mano = new LinkedList<>();
		puntos = 0;
		this.nombre = nombre;
		this.numeroJugador = numeroJugador;
	}
	
	public String getNombre () {
		return this.nombre;
	}
	
	public int getNumero () {
		return numeroJugador;
	}
	
	public void agregarCarta (Carta carta) throws Exception{
		if (this.mano.size() >= 3) {
			throw new Exception("Mano llena");
		}
		else {
			this.mano.add(carta);
		}
	}
	
	public Carta eliminarCarta (int posicion) throws Exception{
		if (posicion > this.mano.size() || 0 >= posicion) {
			throw new Exception("Carta no existente");
		}
		else {
			return mano.remove(posicion-1);
		}
	}
	
	public List<Carta> getCartas () {
		return new LinkedList<>(this.mano);
	}
	
	public void borrarCartas () {
		mano.clear();
	}
	
	public void sumarPuntos (int puntos) {
		this.puntos += puntos;
	}
	
	public int getPuntos () {
		return this.puntos;
	}
	
	public int getPuntosEnvido () {
		Carta cartaAuxiliar1, cartaAuxiliar2;
		int valor1, valor2, suma;
		int contador = 0;
		int[] posiblesEnvido = new int[3];
		for (int x=0;x!=mano.size();x+=1) {
			cartaAuxiliar1 = mano.get(x);
			valor1 = cartaAuxiliar1.getNumero();
			if (valor1 >= 10) {
				valor1 = 0;
			}
			for (int y=x;y!=mano.size();y+=1) {
				cartaAuxiliar2 = mano.get(y);
				valor2 = cartaAuxiliar2.getNumero();
				if (valor2 >= 10) {
					valor2 = 0;	
				}
				
				if (cartaAuxiliar1.getPalo() == cartaAuxiliar2.getPalo()) {
					suma = 20;
					suma = valor1 + valor2;
				}
				else {
					suma = 0;
					if (valor1 > valor2) {
						suma = valor1;
					}
					else {
						suma = valor2;
					}
				}
				
				posiblesEnvido[contador] = suma;
				contador += 1;
				
			}
		}
		
		contador = 0;
		for (int x:posiblesEnvido) {
			if (x > contador) {
				contador = x;
			}
		}
		return contador;
	}
}
