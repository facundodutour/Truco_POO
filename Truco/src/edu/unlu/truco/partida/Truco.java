package edu.unlu.truco.partida;


public class Truco {

	public static void main(String[] args) throws Exception {
		boolean partidaIniciada = true;
		Partida partida = new Partida ();
		Controlador controlador = new Controlador(partida);
		partida.agregarObservador(controlador);

		while (partidaIniciada) {
			int opcion;
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
				
			}
		}
		
	}

}
