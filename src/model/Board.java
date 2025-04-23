package model;

import java.util.ArrayList;;

public class Board {

    private int [][] board;
    private ArrayList <Ship> ships;

    public Board(int x, int y){
        this.board = new int [y][x];
        this.ships = new ArrayList<Ship>();
    }
    
    public int [][] getBoard(){
        return this.board;
    }

    public void setBoard(int x, int y, int value){
        this.board[y][x] = value;
    }

    public ArrayList<Ship> getShips(){
        return this.ships;
    }
    public void setShips(ArrayList<Ship> newShips){
        this.ships = newShips;
    }
}