/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.business;

import com.entity.MessageDetail;
import com.entity.Server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author TrongDuyDao
 */
public class ClientThread implements Runnable, Serializable {

    //for I/O
    private Socket socket;
    private Server server;
    private JTextArea txtContent;
    //use to read and write data to/from server
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public ClientThread(Server server, JTextArea txtContent) {
        /*insert code for opening a connection to server here*/
        try {
            this.txtContent = txtContent;
            this.server = server;
            /*connect to server and get input/output stream here*/
            socket = new Socket(server.getHost(), server.getPort());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            oos = new ObjectOutputStream(dos);
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            /*insert code for receiving and output a message from server here*/
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            ois = new ObjectInputStream(dis);
            //receive message from server
            while(true) {
                Object obj = ois.readObject();
                if(obj instanceof MessageDetail) {
                    //output receive message
                    MessageDetail m = (MessageDetail) obj;
                    txtContent.append("Server: " + m.getContent() + "\n");
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //send message to client
    public void send(Object line) throws Exception {
        /*insert code for sending a message to client here*/
        oos.writeObject(line);
        if(line instanceof MessageDetail) {
            MessageDetail m = (MessageDetail) line;
            txtContent.append("Me: " + m.getContent() + "\n");
        }
    }

}
