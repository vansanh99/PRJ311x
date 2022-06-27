/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.business;

import com.dal.MessageDAO;
import com.dal.UserDAO;
import com.entity.Client;
import com.entity.Server;
import com.entity.Users;
import com.ui.ServerBox;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
        this.chatServer = chatServer;
        try {
            server = new ServerSocket(chatServer.getPort());

        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
        while(true) {
        //accept a connection from client
        socket = server.accept();
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            ObjectInputStream ois = new ObjectInputStream(dis);
            //read information of logged in user
            Object obj = ois.readObject();
            if(obj instanceof Users) {
                //save connected users to database if needed
                Users m = (Users) obj;
                UserDAO u = new UserDAO();
                u.addUser(m);
                MessageDAO d = new MessageDAO();
                Client c = new Client();
                //setting message for client thread
                //for each connection from client, create a thread-clienthandler to handle the connection
                c.setUsername(m.getUsername());
                c.setSocket(socket);
                ServerBox.clients.addElement(c);
                System.out.println("Welcom " + m.getUsername());
                ClientHandler ch = new ClientHandler(socket, c, ois);
                //save to list of cienthandler
                clients.put(c.getUsername(), ch);   
            }
        
        }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static HashMap<String, ClientHandler> clients = new HashMap<>();

}
