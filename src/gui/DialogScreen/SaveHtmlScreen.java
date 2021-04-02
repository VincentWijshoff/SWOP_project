package gui.DialogScreen;

import gui.Screen;
import gui.Window;

import java.awt.*;

public class SaveHtmlScreen extends DialogScreen{
    public SaveHtmlScreen(Window window, Screen prevScreen){
        super(window, prevScreen);
    }

    protected void create(){
        System.out.println("Creating save html screen");
    }

    public void draw(Graphics g){

    }
}
