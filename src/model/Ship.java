package model;
import java.util.ArrayList;

public class Ship {

    private String nameShip;
    private ArrayList <Coord> coords;
    private TypesStatus status;
    
    
    public Ship(String nameShip){
        this.nameShip = nameShip;
        this.coords = new ArrayList <Coord> ();
        this.status = TypesStatus.FLOATING;
    }

    public String getNameShip(){
        return this.nameShip;
    }
    public void setNameShip(String nameShip){
        this.nameShip = nameShip;
    }

    public ArrayList<Coord> getCoords(){
        return this.coords;
    }
    public void setCoords(ArrayList<Coord> newCoords){
        this.coords = newCoords;
    }

    public TypesStatus getStatus(){
        return this.status;
    }

    public void setStatus(TypesStatus newStatus){
        this.status = newStatus;
    }
}
