package model;

public class Player {
    
    private String name;
    private int gamesWon;
    private Board boardPlayer;

    public Player(String name, int gamesWon, int x, int y){
        this.name = name;
        this.gamesWon = gamesWon;
        this.boardPlayer = new Board(y, x);
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public int getGamesWon(){
        return this.gamesWon;
    }
    public void setGamesWon(int gamesWon){
        this.gamesWon = gamesWon;
    }

    public Board getBoard(){
        return this.boardPlayer;
    }
    public void setBoard(Board board){
        this.boardPlayer = board;
    }
}