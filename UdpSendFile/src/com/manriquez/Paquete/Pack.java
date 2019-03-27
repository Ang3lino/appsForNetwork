package com.manriquez.Paquete;

import com.manriquez.PackType;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class Pack implements Serializable {
    private static final long serialVersionUID = 1L;

    public int index;
    public byte[] bytes;
    public PackType type;
    public File file;
    public List<Integer> indexes;

    public Pack(int index, byte[] bytes, PackType type) {
        this.bytes = bytes;
        this.index = index;
        this.type = type;
    }

    public Pack() {
    }
}
