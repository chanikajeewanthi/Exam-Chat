package org.example.examplechat.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerController {

    @FXML
    private Button sendBtnServer;

    @FXML
    private TextArea txtAreaServer;

    @FXML
    private TextField txtFieldServer;

    ServerSocket serverSocket;
    Socket socket;
    DataOutputStream output;
    DataInputStream input;
    String message = "";

    private final List<ClientHandler> clients = new ArrayList<>();

    public void initialize(){
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(4000);
                appendMessage("Server started!");

                while (true) {
                    Socket socket = serverSocket.accept();
                    ClientHandler handler = new ClientHandler(socket);
                    clients.add(handler);
                    new Thread(handler).start();
                    appendMessage("New client connected");

                }

            } catch (IOException e) {
                appendMessage("Server error: " + e.getMessage());
            }
        }).start();
    }

    private void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage("Client: " + message);
            }
        }
    }

    private void appendMessage(String message) {
        javafx.application.Platform.runLater(() -> txtAreaServer.appendText(message + "\n"));
    }

    private class ClientHandler implements Runnable {
        private final Socket socket;
        private DataInputStream input;
        private DataOutputStream output;

        //handle the clients
        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                input = new DataInputStream(socket.getInputStream());
                output = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                appendMessage("Client handler error: " + e.getMessage());
            }
        }

        @Override
        public void run() {
            try {
                String message;
                while ((message = input.readUTF()) != null) {
                    appendMessage("Client: " + message);
                    broadcastMessage(message, this);
                }
            } catch (IOException e) {
                appendMessage("Client disconnected");
                clients.remove(this);

            }
        }

        public void sendMessage(String message) {
            try {
                output.writeUTF(message);
                output.flush();
            } catch (IOException e) {
                appendMessage("Error sending message to client");
            }
        }
    }
}
