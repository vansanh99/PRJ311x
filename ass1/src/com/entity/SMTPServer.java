/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entity;

/**
 *
 * @author vansa
 */

public class SMTPServer {
    String authenication, port, server;

    public SMTPServer() {
    }

    public SMTPServer(String authenication, String port, String server) {
        this.authenication = authenication;
        this.port = port;
        this.server = server;
    }

    public String getAuthenication() {
        return authenication;
    }

    public void setAuthenication(String authenication) {
        this.authenication = authenication;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    @Override
    public String toString() {
        return "SMTPServer{" + "authenication=" + authenication + ", port=" + port + ", server=" + server + '}';
    }
    
}
