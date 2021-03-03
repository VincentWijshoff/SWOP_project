package html.Elements;

import gui.GUIObject;
import gui.GUIString;

import java.util.ArrayList;

public class TextSpan extends ContentSpan {

    private String text;

    public TextSpan(){
    }

    public TextSpan(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public int getHeight() {
        return 16;
    }

    @Override
    public int getWidth() {
        return 16*text.length();
    }

    @Override
    public ArrayList<GUIObject> render(int startX, int startY, ArrayList<GUIObject> objects) {
        objects.add(new GUIString(getText(), startX, startY + getHeight()));
        return objects;
    }
}
