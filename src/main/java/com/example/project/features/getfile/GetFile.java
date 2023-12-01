package com.example.project.features.getfile;
import java.io.*;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.Scanner;

public class GetFile {public ArrayList<String> diskList;
    private String path;
    Scanner sc;
    public GetFile(){
        this.sc = new Scanner(System.in);
    }

    public String getPath(){
        return path;
    }

    private ArrayList<String> driveList(){
        File[] computerDrives = File.listRoots();
        ArrayList<String> output = new ArrayList<String>();
        for (File drive : computerDrives) {
            output.add(drive.toString());
        }
        return output;
    }

    public String listDrive(){
        String disks = "";
        for (String x:driveList()){
            disks+=x+"\n";
        }
        return disks;
    }
    private ArrayList<String> fileList(String path){
        ArrayList<String> output = new ArrayList<String>();
        File folder = new File(path);
        for (File x: folder.listFiles()){
            if ((x.isFile() || x.isDirectory()) && x.exists()) {
                int pos = x.toString().lastIndexOf("\\");
                output.add(x.toString());
            }
        }
        return output;
    }

    public String listFile(String path){
        String fileLst = "";
        for (String x:fileList(path)){
            fileLst+= x + "\n";
        }
        return fileLst;
    }



}
