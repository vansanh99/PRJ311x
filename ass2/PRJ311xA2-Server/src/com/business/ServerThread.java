/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.business;

import com.entity.Client;
import com.entity.Server;
import com.ui.ServerBox;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TrongDuyDao
 */
public class ServerThread implements Runnable {

    private ServerSocket server;
    private Server chatServer;
    private Socket socket;

    public ServerThread(Server chatServer) {
        /*start the server and ready to receive connection from client*/
        this.chatServer = chatServer;
        try {
            server = new ServerSocket(chatServer.getPort());
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        /*Wait for client's connection, accept the connection and handle the connection*/
        try {
            while (true) {
                //output client to dropdown list 
                socket = server.accept();
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                String username = dis.readUTF();
                Client c = new Client();
                //clear old data
                if (username != null) {
                    //output ;ogged username to list of available users
                    c.setUsername(username);
                    c.setSocket(socket);
                    ServerBox.clients.addElement(c);
                    //create a new thread to handle the connection
                    ClientHandler ch = new ClientHandler(socket, c);
                    clients.put(username, ch);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*contain the list of ClientHandler */
    public static HashMap<String, ClientHandler> clients = new HashMap<>();

}
