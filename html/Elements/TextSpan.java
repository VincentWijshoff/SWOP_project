package html.Elements;

import gui.GUIObject;
import html.HtmlRenderer;

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
        HtmlRenderer.addGUIString(this.getText(), startX, startY + getHeight(), objects);
        return objects;
    }
}
