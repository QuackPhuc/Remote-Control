package com.example.project.server;


import com.example.project.features.getfile.GetFile;
import com.example.project.features.list.ListPrc;
import com.example.project.features.list.ListApp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Server {
    private String key = keyGenerator();
    static ArrayList<String> reqList = new ArrayList<String>();
    static ArrayList<String> mailList = new ArrayList<String>();

    private String keyGenerator(){
        Random r = new Random();
        String s = "";
        for (int i=0;i<8;i++) {
            s += (char) (r.nextInt(25) + 65);
        }
        return s;
    }
    public void Server(){

    }
    public String getKey(){
        return key;
    }
    public void run(){
        getRequest gr = new getRequest(this.key);
        sendResponse sr = new sendResponse();

        System.out.println("CODE: " + key);
        while (true){
            gr.run();
            sr.run();
        }
    }
    public static void main(String[]args) throws IOException {
        Server server = new Server();
        server.run();

    }
}

