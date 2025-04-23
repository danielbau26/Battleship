package model;

import java.util.ArrayList;
import java.util.Random;

public class Battleship {
    
    private Random random;
    private Player player;
    private Player cpu;

    public Battleship(String name, int gamesWon, int x, int y){
        this. random = new Random();
        this.player = new Player(name, gamesWon, x, y);
        this.cpu = new Player("CPU", gamesWon, x, y);
    }

    /**
     * Descrption: Locate the Player's boards
     * @param name - valid String
     * @param x - greater than or equal to 0 and Less than or equal to 10
     * @param y - greater than or equal to 0 and Less than or equal to 10
     * @param orientation - "HORIZONTAL" or "VERTICAL"
     * @param size - greater than 0 and Less than or equal to 5
     * @return String with the cells occupied by the ship
     */
    public String addLocateShip(String name, int x, int y, String orientation, int size){

        Ship ship = new Ship(name);
        Board board = player.getBoard();
        int[][] playerBoard = board.getBoard();

        //Si esta fuera de los limites se acomoda
        if (orientation.equals("HORIZONTAL")){
            if (x+size>10){
                x = 10-size;
            }
        }else{
            if (y+size>10){
                y = 10-size;
            }
        }

        boolean flag = checkLocateShips(orientation, size, playerBoard, x, y);

        String print = "";

        if (flag == true){ 
            //Locate the ship
            for (int i=0; i<size; i++){
                int newX = 0;
                int newY = 0;

                if (orientation.equals("HORIZONTAL")){
                    newX = x + i;
                    newY = y;
                }else{
                    newX = x;
                    newY = y + i;
                }

                ship.getCoords().add(new Coord(newX, newY));
                playerBoard[newY][newX] = 1;
                print += "(" + (newX+1) + "," + (newY+1) + ") ";
            }
            board.getShips().add(ship);
        }else{
            flag = false;
        }

        return print;
    }

    /**
     * Description: Check of locate the board
     * 
     * @param orientacion - "HORIZONTAL" or "VERTICAL"
     * @param size - greater than 0
     * @param board - initialized
     * @param x - greater than or equal to 0 and Less than or equal to 10
     * @param y - greater than or equal to 0 and Less than or equal to 10
     * @return - boolean for the vertification
     */
    public boolean checkLocateShips(String orientation, int size, int[][] board , int x, int y){

        for (int i=0; i<size; i++){
            
            int newX = 0;
            int newY = 0;

            if (orientation.equals("HORIZONTAL")){
                newX = x + i;
                newY = y;
            }else{
                newX = x;
                newY = y + i;
            }

            if (board[newY][newX] != 0){
                return false;
            }
        }
        return true;
    }

    /**
     * Descrption : Locate the CPU's boards
     * 
     * @param name - valid String
     * @param size - greater than 0 and Less than or equal to 5
     * @param orientation - "HORIZONTAL" or "VERTICAL"
     */
    public void locateShipsMachine(String name, int size, String orientation){

        Ship ship = new Ship(name);
        Board board = cpu.getBoard();
        int[][] cpuBoard = board.getBoard();


        boolean flag = true;
        while(flag){
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            if (orientation.equals("HORIZONTAL")){
                if (x+size>10){
                    x = 10-size;
                }
            }else{
                if (y+size>10){
                    y = 10-size;
                }
            }

            boolean flagCheck = checkLocateShips(orientation, size, cpuBoard, x, y);

            if (flagCheck == true){ 
                //Locate the ship
                for (int i=0; i<size; i++){
                    int newX = 0;
                    int newY = 0;
    
                    if (orientation.equals("HORIZONTAL")){
                        newX = x + i;
                        newY = y;
                    }else{
                        newX = x;
                        newY = y + i;
                    }
    
                    ship.getCoords().add(new Coord(newX, newY));
                    cpuBoard[newY][newX] = 1;
                }
                board.getShips().add(ship);
                flag = false;
            }else{
                flag = true;
            } 
        }
    }

    /**
     * Description: Displays the board of either the player or the CPU based on the option provided
     * 
     * @param option - greater than 0 and Less than or equal to 1
     */
    public void showBoard(int option){
        String print = "";
        if (option==1){
            Board boardP = player.getBoard();
            int[][] playerBoard = boardP.getBoard();
            for (int i=0; i<playerBoard.length; i++){
                for (int j=0; j<playerBoard[0].length; j++){
                    print += playerBoard[i][j] + " ";
                }
                print += "\n";
            }
        }else{
            Board boardC = cpu.getBoard();
            int[][] cpuBoard = boardC.getBoard();
            for (int i=0; i<cpuBoard.length; i++){
                for (int j=0; j<cpuBoard[0].length; j++){
                    if (cpuBoard[i][j] == 0 || cpuBoard[i][j] == 1){
                        print += "0" + " ";
                    }else if (cpuBoard[i][j] == 2){
                        print += "2" + " ";
                    }else if (cpuBoard[i][j] == 3){
                        print += "3" + " ";
                    }
                }
                print += "\n";
            }
        }

        System.out.println(print);
    }

    /**
     * Description: Processes the player's shot on the CPU's board. Marks the board accordingly and checks if a ship was hit or sunk
     * 
     * @param x - greater than or equal to 0 and Less than or equal to 10
     * @param y - greater than or equal to 0 and Less than or equal to 10
     * @return Message confirming whether it hit something or not
     */
    public String shotPlayer (int x, int y){
        
        String print = "";
        Board board = cpu.getBoard();
        ArrayList <Ship> ships = board.getShips();
        int[][] cpuBoard = board.getBoard();

        x -=1;
        y-=1;

        if (cpuBoard[y][x] == 2 || cpuBoard[y][x] == 3){
            print += "You had already shot here, lost the turn";
        }

        if (cpuBoard[y][x] == 1){
            cpuBoard[y][x] = 2;

            Ship ship = findShip(ships, x, y);
            ArrayList <Coord> coords = ship.getCoords();
    
            if (ship != null){
                boolean flag = isShipSunk(coords, cpuBoard);
                if(flag == true){
                    for (int i=0; i<coords.size(); i++){
                        Coord coord = coords.get(i);
                        int coordX = coord.getCoordX();
                        int coordY = coord.getCoordY();
                        cpuBoard[coordY][coordX] = 3;
                    }
                    ship.setStatus(TypesStatus.SUNK);
                    print += "\n";
                    print += "¡You hit! and you have sunk the " + ship.getNameShip();
                }else{
                    print += "¡You hit! in the enemy ship " + ship.getNameShip();
                }
            }
        }else{
            print += "\n";
            print += "Miss! You didn't hit any ship";
        }
        
        return print;
    }

    /**
     * Description: find the enemy ship only if he hit it
     * 
     * @param ships - ArrayList initialized with values
     * @param shotCoord - Coord with 2 values greater than or equal to 0 and Less than or equal to 10
     * @return The enemy ship only if the coordinates matched
     */
    public Ship findShip (ArrayList<Ship> ships, int x, int y){
        for (int i=0; i<ships.size(); i++) {
            Ship ship = ships.get(i);
            ArrayList <Coord> coords = ship.getCoords();
            for (int j=0; j<coords.size(); j++){
                Coord coord = coords.get(j);
                int coordX = coord.getCoordX();
                int coordY = coord.getCoordY();
                if (coordX == x && coordY == y) {
                    return ship;
                }
            }
        }
        return null;
    }

    /**
     * Description: Checks if a ship has been completely hit in all its coordinates
     * 
     * @param coords - ArrayList initialized with values
     * @param cpuBoard - Matrix representing the board with the hits
     * @return A confirmation of whether the ship has been sunk
     */
    public boolean isShipSunk (ArrayList <Coord> coords, int [][] cpuBoard){
        boolean flag = true;
        for (int i=0; i<coords.size(); i++){
            Coord coord = coords.get(i);
            int coordX = coord.getCoordX();
            int coordY = coord.getCoordY();
            if (cpuBoard[coordY][coordX] != 2){
                flag = false;
                return flag;
            }
        }

        return flag;
    }

    /**
     * Description: Executes the CPU's shot on the player's board. Marks the board depending on hit, sunk or miss
     * 
     * @return Message with the result of the shot
     */
    public String machineShot(){
        String print = "";
        Board board = player.getBoard();
        int[][] playerBoard = board.getBoard();
        ArrayList<Ship> ships = board.getShips();

        int x=0;
        int y=0;

        do {
            x = random.nextInt(10);
            y = random.nextInt(10);
        }while(playerBoard[y][x] == 2 || playerBoard[y][x] == 3);

        if (playerBoard[y][x] == 1){
            playerBoard[y][x] = 2;

            Ship ship = findShip(ships, x, y);
    
            if (ship != null){
                ArrayList <Coord> coords = ship.getCoords();
                boolean flag = isShipSunk(coords, playerBoard);
                if(flag == true){
                    for (int i=0; i<coords.size(); i++){
                        Coord coord = coords.get(i);
                        int coordX = coord.getCoordX();
                        int coordY = coord.getCoordY();
                        playerBoard[coordY][coordX] = 3;
                    }
                    ship.setStatus(TypesStatus.SUNK);
                    print += "The CPU shot at (" + (x+1) + "," + (y+1) + ") - Sunk your " + ship.getNameShip();
                }else{
                    print += "The CPU shot at (" + (x+1) + "," + (y+1) + ") - It hit your ship " + ship.getNameShip();
                }
            }
        }else{
            print += "The CPU shot at (" + (x+1) + "," + (y+1) + ") - ¡MISSED!";
        }

        return print;
    }

    /**
     * Descrption: Determines whether the player or the CPU has lost all their ships
     * 
     * @return 1 if the player wins, 2 if the CPU wins, 0 if there's no winner yet
     */
    public int determineWinner() {

        Board boardPlayer = player.getBoard();
        ArrayList <Ship> playerShips = boardPlayer.getShips();

        boolean lostPlayer = true;
        for (int i=0; i<playerShips.size(); i++){
            Ship ship = playerShips.get(i);
            if (ship.getStatus() != TypesStatus.SUNK){
                lostPlayer = false;
                break;
            }
        }
        if (lostPlayer){
            return 2;
        }

        Board boardCpu = cpu.getBoard();
        ArrayList <Ship> cpuShips = boardCpu.getShips();

        boolean lostCpu = true;
        for (int i=0; i<cpuShips.size(); i++){
            Ship ship = cpuShips.get(i);
            if (ship.getStatus() != TypesStatus.SUNK){
                lostCpu = false;
                break;
            }
        }
        if (lostCpu){
            return 1;
        }

        return 0;

    }

}
