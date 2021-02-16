package gui;

public class GUIRectangle implements GUIObject{

    // Coords left-upper corner of rectangle
    int x;
    int y;

    int width;
    int heigth;

    public GUIRectangle(int x, int y, int width, int heigth) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
    }

    public boolean isInRectangle(int coordX, int coordY) {
        return (coordX >= this.x &&
                coordX <= this.x + this.width &&
                coordY >= this.y &&
                coordY <= this.y + this.heigth);
    }

    public void updateRectangle(int x, int y, int width, int heigth) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
    }

    public void updateWidth(int width) {
        this.width = width;
    }

}
