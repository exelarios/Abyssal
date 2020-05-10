package components;

public class Coordinate {

    private int posX;
    private int posY;

    public Coordinate(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public String toString() {
        return "(" + this.posX + ", " + this.posY + ")";
    }
}
