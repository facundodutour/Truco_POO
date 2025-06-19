package edu.unlu.truco.partida.Modelo;

public interface Observador {
    void actualizar(String evento, Object datos);
}