package gui.DefaultScreen;

import gui.Objects.GUIObject;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ParentPane extends Pane{

    Pane child1;
    Pane child2;

    ParentPane(DocumentArea docArea){
        this.docArea = docArea;
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

    @Override
    public void updateDimensions(int x, int y, int w, int h){
        super.setDimensions(x, y, w, h);
        // we need to update the child dimensions
        if(this.child1 != null && this.child2 != null){
            if(child1.x == child2.x){
                // same x different y
                child1.updateDimensions(this.x, this.y, this.width, this.height/2);
                child2.updateDimensions(this.x, this.y + this.height/2, this.width, this.height/2);
            }else{
                // same y different x
                child1.updateDimensions(this.x, this.y, this.width/2, this.height);
                child2.updateDimensions(this.x + this.width/2, this.y, this.width/2, this.height);
            }
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
            child1.setInFocus();
            child2.setOutFocus();
        }else if(child2.isOnPane(x, y)){
            child1.setOutFocus();
            child2.setInFocus();
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
        if(child1.x == child2.x){
            // same x so horizontal line on lowest y
            g.drawLine(child1.x, Math.max(child1.y, child2.y), child1.x + child1.width, Math.max(child1.y, child2.y));
        }else {
            // not same x so vertical line on highest x
            g.drawLine(Math.max(child1.x, child2.x), child1.y, Math.max(child1.x, child2.x), child1.y + child1.height);
        }
    }

    @Override
    protected void setInFocus() {
        // it will fix focus on children later on
        this.isInFocus = true;
    }

    @Override
    protected void setOutFocus() {
        this.isInFocus = false;
        this.child1.setOutFocus();
        this.child2.setOutFocus();
    }

    @Override
    public ChildPane getFocusedPane() {
        if (child1.isInFocus) {
            return child1.getFocusedPane();
        }
        else {
            return child2.getFocusedPane();
        }
    }

    public void removeChild(ChildPane childPane) {
        if(this.child1 == childPane){
            removeThisSubPane(this.child2);
        }else{
            removeThisSubPane(this.child1);
        }
    }

    private void removeThisSubPane(Pane child) {
        //we remove this parent pane
        //we set the parent pane of the remaining child as this parent
        child.setParentPane(this.parentPane);
        //we then switch this for the given pane as child in this parent
        if(this.parentPane == null){
            this.docArea.setPane(child);
        }else{
            this.parentPane.switchChild(this, child);
        }
        // we set the dimension off the new child as this dimension
        child.updateDimensions(this.x, this.y, this.width, this.height);
    }

    private void switchChild(Pane c1, Pane child) {
        if(this.child1 == c1){
            this.child1 = child;
        }else{
            this.child2 = child;
        }
    }
}
