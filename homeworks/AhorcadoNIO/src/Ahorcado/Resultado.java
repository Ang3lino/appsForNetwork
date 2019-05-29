/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ahorcado;

import java.io.Serializable;


public class Resultado implements Serializable {

    String palabra;
    int error;
    int completado;

    public Resultado(String palabra, int error, int completado) {
        this.palabra = palabra;
        this.error = error;
        this.completado = completado;
    }

    public String getPalabra() {
        return palabra;
    }

    public int getError() {
        return error;
    }

    public int getCompletado() {
        return completado;
    }
}
