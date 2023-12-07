package com.example.project.server;

import com.example.project.mail.receiveMail;

import java.awt.*;
import java.util.ArrayList;

public class getRequest{
    final String DEFAULT_MAIL = "projectmangmaytinh2004@gmail.com";
    final String DEFAULT_PASSWORD = "gorabwfzyfuqfkgy";
    receiveMail rm = new receiveMail(DEFAULT_MAIL,DEFAULT_PASSWORD);

    private String auth_code;

    private String key="";
    private String number="";
    public getRequest(String auth_code){
        this.auth_code = auth_code;
    }
    public void run() {
        rm.receiveMail();
        if (rm.getText().endsWith("\n")) {
            rm.setText(rm.getText().strip());
        }
        if (rm.getContent().split(" ").length>1){
            key  = rm.getContent().split(" ")[0];
            number  = rm.getContent().split(" ")[1];
        }
        if (!key.equals(auth_code)) return;
        if (key.equals(auth_code)){
            if (Server.reqList.isEmpty()){
                Server.reqList.add(rm.getText());
                Server.mailList.add(rm.getFrom());
                Server.numberList.add(number);
                System.out.println(Server.reqList.toString()+Server.numberList.toString());
                sendResponse sr = new sendResponse(key);
                sr.run();
            }
            else {
                if ((!Server.reqList.getLast().equals(rm.getText())) || (!Server.mailList.contains(rm.getFrom())) || (!Server.numberList.contains(number))) {
                    Server.reqList.add(rm.getText());
                    Server.mailList.add(rm.getFrom());
                    Server.numberList.add(number);
                    System.out.println(Server.reqList.toString()+Server.numberList.toString());
                    sendResponse sr = new sendResponse(key);
                    sr.run();
                }
            }
        }
    }
}