package gui.Objects;

import events.KeyEventListener;
import events.MouseEventListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GUIButton extends GUIObject{

    String text;

    MouseEventListener mouseListner;
    KeyEventListener keyListner;

    public GUIButton(String startTxt, int x, int y, int width, int height){
        super(x, y, width, height);
        this.text = startTxt;
        this.mouseListner = (x1, y1, id, clickCount) -> { };
        this.keyListner = (id, keyCode, keyChar, modifier) -> false;
    }

    public void handleMouseEvent(int x, int y, int id, int clickCount){
        if(this.isInGUIObject(x, y) && id == MouseEvent.MOUSE_PRESSED ){
            this.mouseListner.handleMouseEvent(x, y, id, clickCount);
        }
    }

    public boolean handleKeyEvent(int id, int keyCode, char keyChar, int modifier) {
        return this.keyListner.handleKeyEvent(id, keyCode, keyChar, modifier);
    }

    public void setMouseEvent(MouseEventListener l){
        this.mouseListner = l;
    }

    public void setKeyEvent(KeyEventListener l){
        this.keyListner = l;
    }

    public void draw(Graphics g){
        g.setColor(Color.lightGray);
        g.fillRect(this.coordX, this.coordY, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(this.coordX, this.coordY, width, height); // border
        g.setColor(Color.BLACK);
        g.drawString(this.text, this.coordX+5, this.coordY+((int) (height/1.5)));
    }
}
