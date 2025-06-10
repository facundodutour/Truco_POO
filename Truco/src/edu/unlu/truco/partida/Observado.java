package edu.unlu.truco.partida;

import java.util.ArrayList;
import java.util.List;

public class Observado {
    private List<Observador> observadores = new ArrayList<>();

    public void agregarObservador(Observador o) {
        observadores.add(o);
    }

    public void eliminarObservador(Observador o) {
        observadores.remove(o);
    }

    public void notificar(String evento, Object datos) {
        for (Observador o : observadores) {
            o.actualizar(evento, datos); 
        }
    }
}
