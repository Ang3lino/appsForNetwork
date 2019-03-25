package com.manriquez;

public class Main {
    EchoClient client;

    public void setup(){
        new EchoServer().start();
        client = new EchoClient();
    }

    public void whenCanSendAndReceivePacket_thenCorrect() {
        String echo = client.sendEcho("hello server");
        System.out.println(echo);
        echo = client.sendEcho("server is working");
        System.out.println(echo);
    }

    public void tearDown() {
        client.sendEcho("end");
        client.close();
    }

    public Main() {
        setup();
        whenCanSendAndReceivePacket_thenCorrect();
        tearDown();
    }

    public static void main(String[] args) {
        new Main();
    }
}
