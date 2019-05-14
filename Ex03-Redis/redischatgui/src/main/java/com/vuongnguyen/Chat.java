package com.vuongnguyen;

import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.api.listener.MessageListener;
import org.redisson.config.Config;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Chat {
  private JTextArea txtChatBox;
  private JTextField txtChatInput;
  private JButton btnEnter;
  public JPanel panelChat;
  private JLabel lblChanel;
  private JLabel lblUser;
  private JScrollPane scrollChatBox;

  String chanel = new String(), user=new String();
  RTopic subscribeTopic;
  RTopic publishTopic;
  RedissonClient client;
  RListMultimapCache<String,CustomMessage> map;

  public Chat(String _chanel, String _user) {
    this.chanel=_chanel;
    this.user=_user;
    lblChanel.setText("Chanel: "+chanel);
    lblUser.setText("User: "+user);

    //auto scroll down
    DefaultCaret caret = (DefaultCaret)txtChatBox.getCaret();
    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

    //set Wrap
    txtChatBox.setLineWrap(true);

    connectServer();

    btnEnter.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        publishChat();
      }
    });

    txtChatInput.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if(e.getID()==KeyEvent.KEY_PRESSED){
          if(e.getKeyCode()==KeyEvent.VK_ENTER){
            publishChat();
          }
        }
      }
    });
  }

  public String getCurrentDate(){
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Date date = new Date();
    return dateFormat.format(date);
  }

  public void publishChat(){
    if(!txtChatInput.getText().equals("")){
      CustomMessage customMessage =new CustomMessage(chanel,user,txtChatInput.getText(),getCurrentDate());

      publishTopic.publish(customMessage);
      txtChatInput.setText("");

      putMessageToCache(chanel,customMessage);

    }
  }

  public void connectServer(){
    Config config = new Config();
    config.useSingleServer().setAddress("redis://127.0.0.1:6379");

    client = Redisson.create(config);

    map=client.getListMultimapCache(chanel);

    Set<String> keys = map.readAllKeySet();
    for(String key : keys){
      List<CustomMessage> values=map.getAll(key);
      for(CustomMessage value : values){
        txtChatBox.append(value.toString());

      }
    }

    listenServer();
  }

  public void listenServer(){

    subscribeTopic=client.getTopic(chanel);
    subscribeTopic.addListener(CustomMessage.class, new MessageListener<CustomMessage>() {

      public void onMessage(CharSequence charSequence, CustomMessage customMessage) {
        txtChatBox.append(customMessage.toString());

      }
    });
    publishTopic=client.getTopic(chanel);

    CustomMessage customMessage=new CustomMessage(chanel,user, "joined the chat room!",getCurrentDate());

    publishTopic.publish(customMessage);

    putMessageToCache(chanel,customMessage);

  }

  public void putMessageToCache(String key, CustomMessage customMessage){
    map.put(key,customMessage);
    map.expireKey(key,1,TimeUnit.DAYS);
  }

}


