package com.example.project.features.list;

import java.io.IOException;

public class KillPrc {
    boolean isSuccess = false;
    public void run(String pid){
        isSuccess = true;
        try {
            Process process = Runtime.getRuntime().exec("taskkill /F /PID "+pid);
        } catch (IOException e) {
            isSuccess = false;
            throw new RuntimeException(e);
        }
    }
    public boolean isSuccess(){
        return isSuccess;
    }
}
