package gui;

public class GUIString implements GUIObject {

    String text;
    int x;
    int y;

    public GUIString(String text, int x, int y) {
        this.text = text;
        this.x = x;
        this.y = y;
    }

}