package com.manriquez.Paquete;

import java.io.Serializable;

public class Pack <T> implements Serializable {
    private static final long serialVersionUID = 1L;
    public PackType type;
    public T value;

    public Pack(PackType type, T value) {
        this.type = type;
        this.value = value;
    }

    public Pack() { }
}
