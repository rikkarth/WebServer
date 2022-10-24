package org.academiadecodigo.network;

import java.io.*;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Single-threaded simple web server implementation
 */
public class WebServer {

    private static final Logger logger = Logger.getLogger(WebServer.class.getName());

    private static final int DEFAULT_PORT = 8085;

    private ServerSocket bindSocket = null;

    public WebServer() {

        setupServerSocket();
    }

    public static void main(String[] args) {

        try {

            WebServer webServer = new WebServer();
            while (true) {

                webServer.bindSocket.accept();

                new Thread(new Listen(webServer.bindSocket)).start();

            }

        } catch (NumberFormatException e) {

            System.err.println("Usage: WebServer [PORT]");
            System.exit(1);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupServerSocket() {
        try {
            bindSocket = new ServerSocket(DEFAULT_PORT);
            logger.log(Level.INFO, "server bind to " + getAddress(bindSocket));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getAddress(ServerSocket socket) {

        if (socket == null) {
            return null;
        }

        return socket.getInetAddress().getHostAddress() + ":" + socket.getLocalPort();
    }
}
