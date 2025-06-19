package edu.unlu.truco.partida;

import java.util.Scanner;

import edu.unlu.truco.partida.Controlador.Controlador;
import edu.unlu.truco.partida.Modelo.Partida;
import edu.unlu.truco.partida.Vista.VistaPartida;

public class Truco {

	public static void main(String[] args) throws Exception {
		boolean partidaIniciada = true;
		Partida partida = new Partida ();
		Controlador controlador = new Controlador(partida);
		partida.agregarObservador(controlador);
		Scanner scanner = new Scanner(System.in);
		while (partidaIniciada) {
			int opcion;
		    for (int i = 0; i < 50; i++) {
		        System.out.println();
		    }
		    System.out.println("Bienvenido al truco\n\n");
			System.out.println("Lobby");
			System.out.println("Seleccione una opción:\n");
			System.out.println("1. Añadir jugador");
			System.out.println("2. Mostrar jugadores");
			System.out.println("3. Activar/desactivar flor");
			System.out.println("4. Iniciar partida");
			opcion = VistaPartida.leerNumero ();
			switch (opcion) {
			case 1:
				controlador.añadirJugador();
				break;
				
			case 2:
				controlador.mostrarJugadores();
				break;
				
			case 3:
				controlador.alternarFlor();
				break;
				
			case 4:
				if (controlador.iniciarJuego()) {
					partidaIniciada = false;
				}
				break;
			default:
				System.out.println ("No valido");
				System.out.println("\nEnter para continuar...");
				scanner.nextLine();
				
			}
		}
		
	}

}
