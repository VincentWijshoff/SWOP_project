package gui.DefaultScreen;

import gui.Objects.GUIObject;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ParentPane extends Pane{

    public Pane child1; // the first child
    public Pane child2; // the second child
    public int linePosition;   // the position off the separator line between the children

    /**
     * constructor
     * @param screen   the default screen
     */
    ParentPane(PaneManager screen){
        this.screen = screen;
    }

    /**
     * switch one off the child panes with a parent pane
     * @param parent    the parent pane that will be the new child off this parent
     * @param child     the child pane to switch out
     */
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

    /**
     * update the dimension off this parent pane, will also update children dimension
     * @param x the new x coordinate
     * @param y the new y coordinate
     * @param w the new width
     * @param h the new height
     */
    @Override
    public void updateDimensions(int x, int y, int w, int h){
        super.setDimensions(x, y, w, h);
        // we need to update the child dimensions
        updateChildren();
    }

    /**
     * set the children for this parent pane
     * @param p1    the first child
     * @param p2    the second child
     */
    void setChildren(ChildPane p1, ChildPane p2){
        this.child1 = p1;
        this.child2 = p2;
        if(child1.x == child2.x) {
            this.linePosition = this.y + this.height/2;
        }else{
            this.linePosition = this.x + this.width/2;
        }
    }

    boolean isMovingLine = false; // is the line moving

    /**
     * update the dimensions for all children with own dimensions and separator line
     */
    void updateChildren(){
        // update the children with new line position
        if (child1 == null || child2 == null) return;
        if(child1.x == child2.x){
                child1.updateDimensions(this.x, this.y, this.width, this.linePosition - this.y);
                child2.updateDimensions(this.x, this.linePosition, this.width, this.height + this.y - this.linePosition);
            }else{
                // same y different x
                child1.updateDimensions(this.x, this.y, this.linePosition - this.x, this.height);
                child2.updateDimensions(this.linePosition, this.y, this.width + this.x - this.linePosition, this.height);
            }    // same x different y

    }

    private static final int lineBounds = 50; // the bounds for the separator line

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
            this.isMovingLine = false;
        }else if(child2.isOnPane(x, y)){
            child1.setOutFocus();
            child2.setInFocus();
            this.isMovingLine = false;
        }
        if(isOnLine(x, y)){
            //now on line, so moving line
            this.isMovingLine = true;
            // update positions off children
            if(child1.x == child2.x){
                // the line can only go to 10 pixels from borders
                Pane topPane = this.child1.y > this.child2.y ? child2 : child1;
                Pane bottomPane = this.child1.y > this.child2.y ? child1 : child2;
                if (Math.abs(topPane.getBottomMost() - y) >= lineBounds && Math.abs(bottomPane.getTopMost() - y) >= lineBounds) {
                    this.linePosition = y;
                }
            }else{
                Pane leftPane = this.child1.x > this.child2.x ? child2 : child1;
                Pane rightPane = this.child1.x > this.child2.x ? child1 : child2;
                if(Math.abs(leftPane.getRightMost() - x) >= lineBounds && Math.abs(rightPane.getLeftMost() - x) >= lineBounds) {
                    this.linePosition = x;
                }
            }
            this.updateChildren();
        }
        if(!isMovingLine) {
            if (child1.isInFocus) {
                child1.handleMouseEvent(id, x, y, clickCount);
            } else {
                child2.handleMouseEvent(id, x, y, clickCount);
            }
        }
    }

    /**
     * gets the right most position off the pane
     */
    @Override
    int getRightMost(){
        if(child1.x > child2.x){
            return child1.getRightMost();
        }else if(child2.x > child1.x){
            return child2.getRightMost();
        }else{
            return Math.max(child1.getRightMost(), child2.getRightMost());
        }
    }

    /**
     * gets the left most position off the pane
     */
    @Override
    int getLeftMost() {
        if(child1.x > child2.x){
            return child2.getLeftMost();
        }else if(child2.x > child1.x){
            return child1.getLeftMost();
        }else{
            return Math.min(child1.getLeftMost(), child2.getLeftMost());
        }
    }

    /**
     * gets the top most position off the pane
     */
    @Override
    int getTopMost() {
        if(child1.y > child2.y){
            return child2.getTopMost();
        }else if(child2.y > child1.y){
            return child1.getTopMost();
        }else{
            return Math.min(child1.getTopMost(), child2.getTopMost());
        }
    }

    /**
     * gets the bottom most position off the pane
     */
    @Override
    int getBottomMost() {
        if(child1.y > child2.y){
            return child1.getBottomMost();
        }else if(child2.y > child1.y){
            return child2.getBottomMost();
        }else{
            return Math.max(child1.getBottomMost(), child2.getBottomMost());
        }
    }

    /**
     * check if the coordinates are on the separator line
     * @param x the x coordinate
     * @param y the y coordinate
     * @return  true if the coordinates are on the line
     */
    private boolean isOnLine(int x, int y) {
        if(child1.x == child2.x){
            return Math.abs(y - this.linePosition) <= 5;
        }else{
            return Math.abs(x - this.linePosition) <= 5;
        }
    }

    /**
     * load the welcome document
     */
    @Override
    public void loadWelcomeDoc() {
        if(child1.isInFocus){
            child1.loadWelcomeDoc();
        }else{
            child2.loadWelcomeDoc();
        }
    }

    /**
     * load the error document
     */
    @Override
    public void loadErrorDoc() {
        if(child1.isInFocus){
            child1.loadErrorDoc();
        }else{
            child2.loadErrorDoc();
        }
    }

    /**
     * load the address
     * @param url   the url to the web page
     * @throws IOException  if an error occurred trying to load the page
     */
    @Override
    public void loadAddress(String url) throws IOException {
        if(child1.isInFocus){
            child1.loadAddress(url);
        }else{
            child2.loadAddress(url);
        }
    }

    /**
     * get the current html off the child in focus
     * @return  the html off the child in focus
     */
    @Override
    public String getCurrentHtml() {
        if(child1.isInFocus){
            return child1.getCurrentHtml();
        }else{
            return child2.getCurrentHtml();
        }
    }

    /**
     * get all gui objects from the child in focus
     * @return  the gui objects from the child in focus
     */
    @Override
    public ArrayList<GUIObject> getDrawnGUIObjects() {
        if(child1.isInFocus){
            return child1.getDrawnGUIObjects();
        }else{
            return child2.getDrawnGUIObjects();
        }
    }

    /**
     * add a list off gui objects to the child in focus
     * @param objects   the list to add to the child in focus
     */
    @Override
    public void addGUIObjects(ArrayList<GUIObject> objects) {
        if(child1.isInFocus){
            child1.addGUIObjects(objects);
        }else{
            child2.addGUIObjects(objects);
        }
    }

    /**
     * draw both children and the separator line
     * @param g the graphics needed to draw
     */
    @Override
    public void draw(Graphics g) {
        child1.draw(g);
        child2.draw(g);
        if(child1.x == child2.x){
            // same x so horizontal line on lowest y
            g.drawLine(child1.x, this.linePosition, child1.x + child1.width, this.linePosition);
        }else {
            // not same x so vertical line on highest x
            g.drawLine(this.linePosition, child1.y, this.linePosition, child1.y + child1.height);
        }
    }

    /**
     * sets the focus on this pane
     */
    @Override
    protected void setInFocus() {
        // it will fix focus on children later on
        this.isInFocus = true;
    }

    /**
     * sets this and both children out of focus
     */
    @Override
    protected void setOutFocus() {
        this.isInFocus = false;
        this.child1.setOutFocus();
        this.child2.setOutFocus();
    }

    /**
     * get the pane in focus
     * @return  the pane in focus
     */
    @Override
    public ChildPane getFocusedPane() {
        if (child1.isInFocus) {
            return child1.getFocusedPane();
        }
        else {
            return child2.getFocusedPane();
        }
    }

    /**
     * remove the given child from these children
     * @param childPane the child that should be removed
     */
    public void removeChild(ChildPane childPane) {
        if(this.child1 == childPane){
            removeThisSubPane(this.child2);
        }else{
            removeThisSubPane(this.child1);
        }
    }

    /**
     * remove the given pane from children and remove this parent as a sub pane
     * @param child the child to be removed
     */
    private void removeThisSubPane(Pane child) {
        //we remove this parent pane
        //we set the parent pane of the remaining child as this parent
        child.setParentPane(this.parentPane);
        //we then switch this for the given pane as child in this parent
        if(this.parentPane == null){
            this.screen.setPane(child);
        }else{
            this.parentPane.switchChild(this, child);
        }
        // we set the dimension off the new child as this dimension
        child.updateDimensions(this.x, this.y, this.width, this.height);
    }

    /**
     * switch the given child with the new child
     * @param c1    the given child
     * @param child the new child
     */
    private void switchChild(Pane c1, Pane child) {
        if(this.child1 == c1){
            this.child1 = child;
        }else{
            this.child2 = child;
        }
    }
}
