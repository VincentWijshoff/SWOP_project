package gui;

import canvaswindow.CanvasWindow;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GUI extends CanvasWindow{
    ArrayList<GUIObject> drawnObjects = new ArrayList<>();

    public GUI(String title) {
        super(title);
    }


    @Override
    protected void handleShown() {
        repaint();
    }

    @Override
    protected void paint(Graphics g) {

        for (GUIObject object: drawnObjects) {
            object.draw(g);
        }
    }

    @Override
    protected void handleResize() {
        repaint();
    }

    public void draw(GUIObject object) {
        drawnObjects.add(object);
        repaint();
    }

    //TODO: maak unieke ID om objecten bij te houden?
    public void delete(int index) {
        drawnObjects.remove(index);
        repaint();
    }


}
