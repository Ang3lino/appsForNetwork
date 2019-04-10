package com.manriquez.Paquete;

import java.io.Serializable;

public class Pack <T> implements Serializable {
    private static final long serialVersionUID = 1L;
    public PackType response, request;
    public T value;

    public Pack(T value) {
        this.value = value;
    }

    public Pack(PackType request, PackType response,  T value) {
        this.value = value;
        this.request = request;
        this.response = response;
    }

    public Pack() { }
}
