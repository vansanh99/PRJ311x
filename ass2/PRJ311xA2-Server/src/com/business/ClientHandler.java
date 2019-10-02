package com.business;

import com.entity.Client;
import com.ui.ChatBox;
import java.awt.Frame;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class ClientHandler implements Runnable {

    private DataInputStream dis;
    private DataOutputStream dos;
    private Socket socket;
    private Client client;
    private JTextArea txtContentServerBox;
    ChatBox cb;
    /*provide the setter and getter here*/
    private Frame parent;

    public ClientHandler(Socket socket) {
        this.socket = socket;

    }

    public ClientHandler(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

    }

    public ClientHandler(Socket socket, Client client, JTextArea txtContent) {
        this.socket = socket;
        this.client = client;
        this.txtContentServerBox = txtContent;
    }

    @Override
    public void run() {
        /*Handler connection for individual client connection*/
        try {
            //receive message from client and output to txtcontent
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            while (true) {
                Object line = dis.readUTF();
                if (line != null) {
                    txtContentServerBox.append(client.getUsername() + ": " + line + "\n");
                }
            }
        } catch (IOException ex) {
        }
    }

    public void send(Object line) throws Exception {
        /*send message to client*/
        dos.writeUTF("Server: " + line.toString());
        txtContentServerBox.append("Me: " + line + "\n");
    }

    public void setTxtContent(JTextArea txtContent) {
        this.txtContentServerBox = txtContent;
    }
}
