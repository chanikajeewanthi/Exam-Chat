package org.example.examplechat.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientController {

    @FXML
    private Button btnSend;

    @FXML
    private TextArea textArea;

    @FXML
    private TextField txtField;

    Socket socket;
    DataOutputStream output;
    DataInputStream input;

    public void initialize() {
        new Thread(() -> {
            try {
                socket = new Socket("localhost", 4000);
                input = new DataInputStream(socket.getInputStream());
                output = new DataOutputStream(socket.getOutputStream());
                while (true) {
                    String message = input.readUTF();
                    appendMessage(message);
                }
            } catch (IOException e) {
                appendMessage("Error: " + e.getMessage());
            }
        }).start();
    }

    //handle the send button
    @FXML
    void btnSendOnAction(ActionEvent event) {
        try {
            if (output != null && socket != null && !socket.isClosed()) {
                String message = txtField.getText();
                output.writeUTF(message);
                output.flush();
                appendMessage("client: " + message);
                txtField.clear();
            } else {
                appendMessage(" Not connected to server.");
            }
        } catch (IOException e) {
            appendMessage(" Failed to send message: " + e.getMessage());
        }
    }

    private void appendMessage(String message) {
        javafx.application.Platform.runLater(() -> txtField.appendText(message + "\n"));

    }
}








