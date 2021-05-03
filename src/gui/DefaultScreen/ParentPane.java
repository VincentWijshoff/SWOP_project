package gui.DefaultScreen;

import gui.Objects.GUIObject;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ParentPane extends Pane{

    Pane child1;
    Pane child2;

    ParentPane(){

    }

    void changeChild(ParentPane parent, ChildPane child){
        if(this.child1 == child){
            this.child1 = parent;
        }else{
            this.child2 = parent;
        }
    }

    /**
     * Handle a key event on this pane
     *
     * @param id       The id off the key event
     * @param keyCode  The keycode off the key event
     * @param keyChar  The char of the key pressed
     * @param modifier The modifier on the key pressed
     */
    @Override
    void handleKeyEvent(int id, int keyCode, char keyChar, int modifier) {
        if(child1.isInFocus){
            child1.handleKeyEvent(id, keyCode, keyChar, modifier);
        }else{
            child2.handleKeyEvent(id, keyCode, keyChar, modifier);
        }
    }

    void setChildren(ChildPane p1, ChildPane p2){
        this.child1 = p1;
        this.child2 = p2;
    }

    /**
     * Handle a mouse event on this pane
     *
     * @param id         The id off the mouse event
     * @param x          The x coordinate off the mouse event
     * @param y          The y coordinate off the mouse event
     * @param clickCount The click count on the mouse event
     */
    @Override
    void handleMouseEvent(int id, int x, int y, int clickCount) {
        // here we check the in focus things on the pane
        if(child1.isOnPane(x, y)){
            child1.isInFocus = true;
            child2.isInFocus = false;
        }else{
            child1.isInFocus = false;
            child2.isInFocus = true;
        }
        if(child1.isInFocus){
            child1.handleMouseEvent(id, x, y, clickCount);
        }else{
            child2.handleMouseEvent(id, x, y, clickCount);
        }
    }

    @Override
    public void loadWelcomeDoc() {
        if(child1.isInFocus){
            child1.loadWelcomeDoc();
        }else{
            child2.loadWelcomeDoc();
        }
    }

    @Override
    public void loadErrorDoc() {
        if(child1.isInFocus){
            child1.loadErrorDoc();
        }else{
            child2.loadErrorDoc();
        }
    }

    @Override
    public void loadAddress(String url) throws IOException {
        if(child1.isInFocus){
            child1.loadAddress(url);
        }else{
            child2.loadAddress(url);
        }
    }

    @Override
    public String getCurrentHtml() {
        if(child1.isInFocus){
            return child1.getCurrentHtml();
        }else{
            return child2.getCurrentHtml();
        }
    }

    @Override
    public ArrayList<GUIObject> getDrawnGUIObjects() {
        if(child1.isInFocus){
            return child1.getDrawnGUIObjects();
        }else{
            return child2.getDrawnGUIObjects();
        }
    }

    @Override
    public void addGUIObjects(ArrayList<GUIObject> objects) {
        if(child1.isInFocus){
            child1.addGUIObjects(objects);
        }else{
            child2.addGUIObjects(objects);
        }
    }

    @Override
    public void draw(Graphics g) {
        child1.draw(g);
        child2.draw(g);
        // TODO line between panes?
    }
}
