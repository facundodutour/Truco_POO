package edu.unlu.truco.partida.Vista;

import java.util.Scanner;

import edu.unlu.truco.partida.Modelo.TiposTruco;

public class VistaPartida {
	Scanner scanner = new Scanner(System.in);
	
	public void mostrarMenuRonda (TiposTruco nivel) {
		String truco = "truco";
		lineaDivision();
		System.out.println("\nSeleccione una opción:\n");
		System.out.println("1. Tirar carta");
		if (nivel != TiposTruco.ValeCuatro) {
			if (nivel == null) {
				truco = "truco";
			}
			else if (nivel == TiposTruco.Truco) {
				truco = "retruco";
			}
			else {
				truco = "valecuatro";
			}
			System.out.println("2. Cantar " + truco);
		}
		System.out.println("3. Cantar envido");
		System.out.println("4. Flor");
		System.out.println("5. Irse al mazo");
	}
	
	public void mostrarMenuTruco (TiposTruco nivel) {
		String proximoTruco = null;
		lineaDivision();
		System.out.println("\nSeleccione una opción:\n");
		System.out.println("1. Quiero");
		System.out.println("2. No quiero");
		if (nivel != TiposTruco.Retruco) {
			if (nivel == null) {
				proximoTruco = "retruco";
			}
			else {
				proximoTruco = "valecuatro";
			}
			System.out.println("3. Quiero " + proximoTruco);
		}
	}
	
	public void mostrarMenuEnvido1 () {
		lineaDivision();
		System.out.println("\nSeleccione una opción:\n");
		System.out.println("1. Envido");
		System.out.println("2. Real envido");
		System.out.println("3. Falta envido");
	}
	
	public void mostrarMenuEnvido2 (boolean envidos[], String envido) {
		lineaDivision();
		System.out.println("\nTe cantaron " +  envido);
		System.out.println("\nSeleccione una opción:\n");
		if (!envidos[2]) {
			System.out.println("1. Quiero");
			System.out.println("2. No quiero");
		}
		else {
			System.out.println("1. Quiero");
			System.out.println("2. No quiero");
			if (envidos[0]) {
				System.out.println("3. Envido");
			}
			if (envidos[1]) {
				System.out.println("4. Real envido");
			}
			System.out.println("5. Falta envido");
		}
	}
	
	public void mostrarMenuFlor (boolean flores[], String flor) {
		lineaDivision();
		System.out.println("\nTe cantaron " +  flor);
		System.out.println("\nSeleccione una opción:\n");
		
		
		if (!flores[1] || !flores[2]) {
			System.out.println ("1. Quiero");
		}
		System.out.println ("2. No quiero");
		if (flores[1]) {
			System.out.println ("3. Contra flor");
		}
		
		if (flores[2]) {
			System.out.println ("4. Contra flor al resto");
		}
	}
	
	public void mostrarMensaje (String mensaje) {
		System.out.println(mensaje);
	}
	
	public void mostrarMensajeEspera (String mensaje) {
		System.out.println(mensaje);
		esperarEntrada();
	}
	
	public static int leerNumero() {
		Scanner scanner = new Scanner(System.in);
		int lectura = -1;
		while (lectura == -1) {
			if (scanner.hasNextInt()) {
				lectura = scanner.nextInt();
			}
			else {
				scanner.next();
				System.out.println("No valido");
			}
		}
		scanner.nextLine(); 
		return lectura;
	}
	
	public String leerString () {
		String lectura = scanner.next();
		scanner.nextLine(); 
		return lectura;
	}
	
	public void limpiarConsola() {
	    for (int i = 0; i < 50; i++) {
	        System.out.println();
	    }
	}
	
	public void esperarEntrada() {

		System.out.println("\nEnter para continuar...");
		scanner.nextLine();
	}
	
	public void lineaDivision () {
		System.out.println ("_____________________________________________________________");
		
	}
	
}
