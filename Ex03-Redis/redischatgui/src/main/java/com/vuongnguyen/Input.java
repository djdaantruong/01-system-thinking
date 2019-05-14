package com.vuongnguyen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.vuongnguyen.Chat;

public class Input {
    private JTextField txtUser;
    private JButton btnInput;
    private JTextField txtChanel;
    private JLabel lblChanel;
    private JLabel lblUser;
    private JPanel panelInput;
    private static JFrame frame;

    public Input() {
        btnInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enterRoom();
            }
        });

        txtUser.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getID()==KeyEvent.KEY_PRESSED){
                    if(e.getKeyCode()==KeyEvent.VK_ENTER){
                        enterRoom();
                    }
                }
            }
        });
        txtChanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getID()==KeyEvent.KEY_PRESSED){
                    if(e.getKeyCode()==KeyEvent.VK_ENTER){
                        enterRoom();
                    }
                }
            }
        });
    }

    public void enterRoom(){
        if(txtChanel.getText().equals("") || txtUser.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Chanel and User not null");
        }
        else {
            showChatForm();
            frame.setVisible(false);
        }
    }

    public void showChatForm(){
        JFrame frame = new JFrame("Chat");
        frame.setContentPane(new Chat(txtChanel.getText(),txtUser.getText()).panelChat);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);

        frame.setSize(500,300);
        frame.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        frame = new JFrame("Input");
        frame.setContentPane(new Input().panelInput);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
