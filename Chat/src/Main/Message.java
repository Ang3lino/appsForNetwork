package Main;

import java.io.*;

public class Message implements Serializable {
    public static final long serialVersionUID = 1L;

    private String nickname, message;
    private MyState state;

    // public enum Main.MyState {
    //     LOG_IN, LOG_OUT, PRIVATE_MSG, PUBLIC_MSG; 
    // }

    public Message(String nick, String msg, MyState state) {
        nickname = nick;
        message = msg;
        this.state = state;
    }

    @Override
    public String toString() {
        return String.format("State: %s\nNickname: %s\nMessage: %s", state, nickname, message);
    }

    public String getNickname() { return nickname; }
    public String getMessage() { return message; }
    public MyState getState() { return state; }
}
