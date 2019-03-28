package com.manriquez.Paquete;

import java.math.BigInteger;

public class Data implements Comparable<Data>   {
    public int index;
    public byte[] bytes;

    public Data(int index, byte[] bytes) {
        this.index = index;
        this.bytes = bytes;
    }

    public boolean equals(Data other) {
        return index == other.index && bytes == other.bytes;
    }

    @Override
    public String toString() {
        return String.format(
                "Index: %d\nBytes: %016X", index, new BigInteger(1, bytes));
    }

    public int compareTo(Data other) {
        return index - other.index;
    }


}
