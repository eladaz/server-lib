package com.gett.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoServer extends Server {

    public EchoServer(int port) {
        //the superclass know what to do with the port number, we don't have to care about it
        super(port);
    }

    //this is called by the Server class when a connection comes in.
    //in and out come from the income socket connection
    public void handleConnection(Socket socket) {
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            //just copy the input to the output
            while (true) {
                out.write(in.read());
            }
        } catch (IOException ie) {
            System.out.println(ie);
        }
    }

    protected void cleanUp() {
        System.out.println( "Cleaning up" );
    }

}
