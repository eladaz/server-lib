package com.gett.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class Server implements Runnable {

    //the port we'll be listing on
    private int port;

    //how many connections we've handled
    public int numConnections;

    //the reporter that's reporting on this Server
    private Reporter reporter;

    //set this to true to tell the thread to stop accepting connections
    private boolean mustQuit = false;

    public Server(int port) {

        //remember the port number so the thread can listen on it
        this.port = port;

        //the constructor starts a background thread
        new Thread(this).start();

        //and start the reporter
        reporter = new Reporter(this);
    }

    //this is our background thread
    public void run() {
        ServerSocket ss = null;

        try {
            //get ready to listen
            ss = new ServerSocket(port);

            while (!mustQuit) {
                //give out some debugging info
                debug("Listining on " + port);

                //wait for an incoming connection
                Socket s = ss.accept();

                //record that we got anther connection
                numConnections++;

                //more debugging info
                debug("Got connection on " + s);

                //process the connection -- this is implemented by the subclass
                handleConnection(s);
            }
        } catch (IOException ie) {
            debug(ie.toString());
        }
    }

    //the default implementation does nothing
    abstract public void handleConnection(Socket s);

    //tell the thread to stop accepting connections
    public void close() {
        mustQuit = true;
        reporter.close();
    }

    //put any last-minute clean-up stuff in here
    protected void cleanUp() {

    }

    //everything below provides a simple debug system for this package

    //set this to a print stream if you want debug info
    //sent to it; otherwise, leave it null
    static private PrintStream debugStream;

    //we have two versions of this...
    static public void setDebugStream(PrintStream ps) {
        debugStream = ps;
    }

    //... just for convenience
    static public void setDebugStream(OutputStream out) {
        debugStream = new PrintStream(out);
    }

    //sent debug info to the print stream, if there is one
    static public void debug(String s) {
        if (debugStream != null) {
            debugStream.println(s);
        }
    }

}
