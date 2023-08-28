package com.example.netstuff.network;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {


    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket client;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String name;
    private String message;

    public ClientHandler(Socket client) {
        try {
            this.client = client;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            clientHandlers.add(this);
            broadcastInput(name + ": " + message);
        } catch (IOException e) {
            closeClient(client, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run() {
        String newGuess;

        while(client.isConnected()) {
            try {
                newGuess = bufferedReader.readLine();
                broadcastInput(newGuess);
            } catch (IOException e) {
                closeClient(client, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void broadcastInput(String newGuess) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.equals(this.name)) {
                    clientHandler.bufferedWriter.write(newGuess);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeClient(client, bufferedReader, bufferedWriter);
            }
        }
    }

    public void removeClient() {
        clientHandlers.remove(this);
    }

    public void closeClient(Socket client, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClient();
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }

            if (bufferedWriter != null) {
                bufferedWriter.close();
            }

            if (client != null) {
                client.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
