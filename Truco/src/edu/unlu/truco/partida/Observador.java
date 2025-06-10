package edu.unlu.truco.partida;

public interface Observador {
    void actualizar(String evento, Object datos);
}