package model;

public class Coord {
    
    private int coordX;
    private int coordY;

    public Coord(int coordX, int coordY){
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public int getCoordX(){
        return this.coordX;
    }
    public void setCoordX(int coordX){
        this.coordX = coordX;
    }

    public int getCoordY(){
        return this.coordY;
    }
    public void setCoordY(int coordY){
        this.coordY = coordY;
    }
}
