/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ahorcado;

import java.io.Serializable;

public class Objeto implements Serializable {

    String palabra;
    String letra;
    int indice;

    public Objeto(String palabra, int indice, String letra) {
        this.palabra = palabra;
        this.indice = indice;
        this.letra = letra;
    }

    public String getPalabra() {
        return palabra;
    }

    public int getIndice() {
        return indice;
    }

    public String getLetra() {
        return letra;
    }
}
