/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ui;

import com.business.MyMail;
import com.entity.MailMessage;
import com.entity.SMTPServer;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.mail.Session;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author vansa
 */
public class MyEmail implements ActionListener {
//declare needed java swing components 

    JTextField txtFrom, txtTo, txtSubject, txtUsername;
    JPasswordField txtPassword;
    JLabel lblFrom, lblTo, lblSubject, lblSMTPServer, lblUsername, lblPassword, lblMessage;
    JComboBox<SMTPServer> cbxServer;
    JTextArea txtMessage;
    JButton btnSend;
    JPanel pnlTop;
    GridBagConstraints constraints = new GridBagConstraints();
    String[] smtpServerName = {"smtp.gmail.com(SSL)", "smtp.gmail.com(TLS)"};
    SMTPServer smtpserver = new SMTPServer();
    MailMessage mailmessage = new MailMessage();

    public void createAndShowGUI() {
//Create and set up the window. 
        JFrame frame = new JFrame("Send E-Mail Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// Set up the content pane. 
        addComponentsToPane(frame.getContentPane());
//Use the content pane's default BorderLayout. No need for 
//setLayout(new BorderLayout()); 
//Display the window. 
        frame.pack();
        frame.setVisible(true);
    }

    public void addComponentsToPane(Container pane) {
        lblFrom = new JLabel("From:");
        lblTo = new JLabel("To:");
        lblSubject = new JLabel("Subject:");
        lblSMTPServer = new JLabel("SMTP Server:");
        lblUsername = new JLabel("Username");
        lblPassword = new JLabel("Password:");
        lblMessage = new JLabel("Message:");

        txtFrom = new JTextField(30);
        txtTo = new JTextField(30);
        txtSubject = new JTextField(30);
        txtUsername = new JTextField(30);
        txtPassword = new JPasswordField(30);

        cbxServer = new JComboBox<>();

        txtMessage = new JTextArea(10, 50);

        btnSend = new JButton("Send E-Mail");

        cbxServer = new JComboBox(smtpServerName);

        pnlTop = new JPanel(new GridLayout(0, 2, 5, 5));
        pane.setLayout(new BorderLayout(10, 10));
        pnlTop.add(lblFrom);
        pnlTop.add(txtFrom);
        pnlTop.add(lblTo);
        pnlTop.add(txtTo);
        pnlTop.add(lblSubject);
        pnlTop.add(txtSubject);
        pnlTop.add(lblSMTPServer);
        pnlTop.add(cbxServer);
        pnlTop.add(lblUsername);
        pnlTop.add(txtUsername);
        pnlTop.add(lblPassword);
        pnlTop.add(txtPassword);
        pnlTop.add(lblMessage);

        pane.add(pnlTop, BorderLayout.NORTH);
        pane.add(txtMessage, BorderLayout.CENTER);
        pane.add(btnSend, BorderLayout.SOUTH);
        btnSend.addActionListener(this);
    }

    public String getFrom() {
        return txtFrom.getText();
    }

    public String getTo() {
        return txtTo.getText();
    }

    public String getSubject() {
        return txtSubject.getText();
    }

    public String getUsername() {
        return txtUsername.getText();
    }

    public String getPassword() {
        return txtPassword.getText();
    }

    public String getMessage() {
        return txtMessage.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (cbxServer.getSelectedIndex() == 0) {
            smtpserver.setAuthenication("SSL");
            smtpserver.setPort("465");
            smtpserver.setServer("smtp.gmail.com");
        } else {
            if (cbxServer.getSelectedIndex() == 1) {
                smtpserver.setAuthenication("TLS");
                smtpserver.setPort("587");
                smtpserver.setServer("smtp.gmail.com");
            }
        }
        mailmessage.setFrom(getFrom());
        mailmessage.setTo(getTo());
        mailmessage.setSubject(getSubject());
        mailmessage.setMessage(getMessage());
        MyMail mymail = new MyMail();
        Session session = mymail.getMailSession(smtpserver, getUsername(), getPassword());

        try {
            if (mymail.sendMail(mailmessage, session)) {
                JOptionPane.showMessageDialog(null, "Email sent to " + getTo(), "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, 
                    "Error while sending the e-mail: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
