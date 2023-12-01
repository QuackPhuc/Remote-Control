package com.example.project.server;

import com.example.project.mail.receiveMail;

import java.awt.*;
import java.util.ArrayList;

public class getRequest extends Thread{
    final String DEFAULT_MAIL = "servermmtk22tnt1@gmail.com";
    final String DEFAULT_PASSWORD = "toemssrwmsyismoy";
    receiveMail rm = new receiveMail(DEFAULT_MAIL,DEFAULT_PASSWORD);

    private String auth_code;


    public getRequest(String auth_code){
        this.auth_code = auth_code;
    }
    @Override
    public void run() {

        rm.receiveMail();

        if (!rm.getContent().equals(auth_code)){
            System.out.println("Wrong code");
            return;
        }
        if (Server.reqList.size()==0){
            Server.reqList.add(rm.getText());
            Server.mailList.add(rm.getFrom());
        }

        while (!(Server.mailList.getLast().equals(rm.getFrom()) && Server.reqList.getLast().equals(rm.getText()))) {
            rm.receiveMail();
            if (!rm.getContent().equals(auth_code)){
                System.out.println("Wrong Code");
                break;
            }
            Server.reqList.add(rm.getText());
            Server.mailList.add(rm.getFrom());
        }
    }
}