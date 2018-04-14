package com.gett;

import com.gett.server.EchoServer;
import com.gett.server.Server;

public class Main {

    static public void main(String args[]) throws Exception {
        int port = 8080;
        if (args.length != 0) {
            // Grab the port number from the command-line
            port = Integer.parseInt(args[0]);
        }

        // Have debugging info sent to standard error stream
        Server.setDebugStream(System.err);

        // Create the server, and it's up and running
        new EchoServer(port);
    }

}
