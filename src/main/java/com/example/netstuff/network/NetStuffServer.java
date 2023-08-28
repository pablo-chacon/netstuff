package com.example.netstuff.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class NetStuffServer extends Thread {
  private ServerSocket serverSocket;
  public int port = 8081;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true)
            new EchoClientHandler().start();
    }

    public void closeSocket() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class EchoClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public void ClientHandler(Socket clientSocket) {
            this.clientSocket = new Socket();
        }

        public void run() {

            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            String inputLine;
            try {
                while (true) {
                    if (!((inputLine = in.readLine()) != null)) break;

                    if (".".equals(inputLine)) {
                    out.println("bye");
                    break;
                }
                out.println(inputLine);}
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            out.close();
            try {
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        NetStuffServer netStuffServer = new NetStuffServer();
        netStuffServer.start();
    }
}