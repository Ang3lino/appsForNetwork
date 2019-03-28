package com.manriquez;

import com.manriquez.Paquete.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) {
	// write your code here

        File file = new File("functional_programming_for_java_developers.pdf");
        byte[] fbytes = UtilFun.fileToBytes(file);
        ArrayList<Data> partition = UdpSend.splitBytes(fbytes);
        if (partition == null) return;

        TreeSet<Data> set = new TreeSet<>(partition);
        UdpReceive.partitionBytesWriteFile(set, file);
    }
}
