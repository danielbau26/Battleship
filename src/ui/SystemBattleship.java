package ui;

import java.util.Scanner;
import model.Battleship;

/**
 * Name: Daniel Felipe Bautista Sanchez
 * Version 1
 * 
 * This is a Naval Battle game where a human player and a machine 
 * take turns to place and shoot at ships on a 10x10 board. The goal 
 * is to sink all the opponent's ships first. 
 * 
 * The game has two modes: standard (fixed ships) and custom (player-defined ships). 
 * After each game, the system counts the wins for both players.
 */
public class SystemBattleship{

    private Scanner reader;
    private Battleship battleship;
    private int [] playerVictories;
    private int [] cpuVictories;

    public SystemBattleship(){
        this.reader = new Scanner(System.in);
        this.battleship = new Battleship("Player", 0, 10, 10);
        this.playerVictories = new int [2];
        this.cpuVictories = new int [2];
    }

    public static void main(String [] args){

        SystemBattleship system = new SystemBattleship();
        system.menu();
    }

    /**
     * Description: I give a brief set of rules, and he has a menu with the options he can play
     */
    public void menu(){

        System.out.println("¡Welcome to a Battleship simulator game!");
        System.out.println("");
        registerPlayer();

        while (true){
            System.out.println("Select the option that want to do");
            System.out.println("1. Play");
            System.out.println("2. Exit the game");
            int option = 0;
            //Check the option
            while (true){
                option = reader.nextInt();
                reader.nextLine();

                if(option==1 || option==2){
                    break;
                }else{
                    System.out.println("Number out of range.");
                }
            }

            System.out.println("");

            if(option==1){
                System.out.println("Select the game mode:");
                System.out.println("1. Standard Game");
                System.out.println("2. Custom Game");
                System.out.println("3. Show victorys");

                int modeGame = 0;
                //Check the option
                while (true){
                modeGame = reader.nextInt();
                reader.nextLine();

                if(modeGame==1 || modeGame==2 || modeGame==3){
                    break;
                }else{
                    System.out.println("Number out of range.");
                }
                }

                System.out.println("");

                System.out.println("Remember the following things:");
                System.out.println("You must indicate the coordinates for each ship or shot.");
                System.out.println("And keep in mind that ships must not overlap.");
                System.out.println("Now, start placing the ships!");
                System.out.println("");

                if(modeGame==1){
                    standardGame();
                }else if(modeGame==2){
                    customGame();
                }else if (modeGame==3){
                    showVictorys();
                }

            }else if(option==2){
                System.out.println("¡Thanks for playing!");
                System.out.println("");
                break;
            }
        }
    }

    /**
     * Description: Register the player's name for use in Controller
     */
    public void registerPlayer(){
        System.out.println("Enter your name: ");
        String name = reader.nextLine();

        this.battleship = new Battleship(name, 0, 10,10);
    }

    /**
     * Description: A normal game is played with the classic rules
     */
    public void standardGame(){

        locateShipsPlayer("Lancha", 1, "N/A");
        locateShipsPlayer("Medic Ship", 2, "VERTICAL");
        locateShipsPlayer("Supply Ship", 3, "HORIZONTAL");
        locateShipsPlayer("Ammo Ship", 3, "VERTICAL");
        locateShipsPlayer("WarShip", 4, "HORIZONTAL");
        locateShipsPlayer("Aircraft Carrier", 5, "VERTICAL");

        battleship.locateShipsMachine("Lancha", 1, "N/A");
        battleship.locateShipsMachine("Medic Ship", 2, "VERTICAL");
        battleship.locateShipsMachine("Supply Ship", 3, "HORIZONTAL");
        battleship.locateShipsMachine("Ammo Ship", 3, "VERTICAL");
        battleship.locateShipsMachine("WarShip", 4, "HORIZONTAL");
        battleship.locateShipsMachine("Aircraft Carrier", 5, "VERTICAL");

        System.out.println("");
        System.out.println("Finally, your board looks like this");
        battleship.showBoard(1);
        
        System.out.println("");
        System.out.println("Alright, now let's play");
        System.out.println("");

        while(true){
            playerShot();
            System.out.println("");
            System.out.println("This is how the enemy's board looks:");
            System.out.println("");
            battleship.showBoard(0);
            System.out.println("");

            String printCpu = battleship.machineShot();
            System.out.println(printCpu);

            System.out.println("");
            System.out.println("This is how your board looks:");
            System.out.println("");
            battleship.showBoard(1);
            System.out.println("");

            int numero = battleship.determineWinner();
            if (numero == 1){
                System.out.println("Congratulations! You won!");
                playerVictories[0] += 1;
                break;
            }else if (numero == 2){
                System.out.println("Game over! You lost");
                cpuVictories[0] += 1;
                break;
            }
        }

        System.out.println("You has won " + playerVictories[0]);
        System.out.println("The CPU has won " + cpuVictories[0]);
        System.out.println("");
        
    }

    /**
     * Description: A custom game is played with up to 10 ships, each with a maximum of 5 cells
     */
    public void customGame(){

        System.out.println("How many ships will there be on the board? (Up to 10)");
        int amount = 0;
        while (true){
            amount = reader.nextInt();
            reader.nextLine();

            if(amount>=1 && amount<=10){
                break;
            }else{
                System.out.println("Number out of range.");
            }
            }
        
        for (int i=0; i<amount; i++){
            System.out.println("Name of the ship");
            String name = reader.nextLine();
            System.out.println("How many cells does it occupy? (Up to 5)");
            int size = 0;
            while (true){
                size = reader.nextInt();
                reader.nextLine();
    
                if(size>=1 && size<=5){
                    break;
                }else{
                    System.out.println("Number out of range.");
                }
                }
            System.out.println("What is its orientation? (VERTICAL/HORIZONTAL)");
            String orientation = "";
            while (true){
                orientation = reader.nextLine().toUpperCase();
                if(orientation.equals("HORIZONTAL") || orientation.equals("VERTICAL")){
                    break;
                }else{
                    System.out.println("Should be (VERTICAL/HORIZONTAL).");
                }
            }
            locateShipsPlayer(name, size, orientation);
            battleship.locateShipsMachine(name, size, orientation);
        }
        System.out.println("");
        System.out.println("Finally, your board looks like this");
        battleship.showBoard(1);

        System.out.println("");
        System.out.println("Alright, now let's play");
        System.out.println("");

        while(true){
            playerShot();
            System.out.println("");
            System.out.println("This is how the enemy's board looks:");
            System.out.println("");
            battleship.showBoard(0);
            System.out.println("");

            String printCpu = battleship.machineShot();
            System.out.println(printCpu);

            System.out.println("");
            System.out.println("This is how your board looks:");
            System.out.println("");
            battleship.showBoard(1);
            System.out.println("");

            int numero = battleship.determineWinner();
            if (numero == 1){
                System.out.println("Congratulations! You won!");
                playerVictories[1] += 1;
                break;
            }else if (numero == 2){
                System.out.println("Game over! You lost");
                cpuVictories[1] += 1;
                break;
            }
        }

        System.out.println("You has won " + playerVictories[1]);
        System.out.println("The CPU has won " + cpuVictories[1]);
        System.out.println("");
        
    }

    /**
     * Descrption: I ask for the coordinates and they will be placed in the controller of the model
     * @param name - valid String
     * @param size - greater than 0
     * @param orientation - "HORIZONTAL" or "VERTICAL"
     */
    public void locateShipsPlayer(String name, int size, String orientation){

        int x,y;
        System.out.println("");
        System.out.println("Place your " + name + " (occupation " + size + " box)");
        if (!orientation.equals("N/A")){
            System.out.println("Place " + orientation + " on the board");
        }

        String print = "";

        while(true){
            System.out.println("Coordinate X (1-10):");
            x = reader.nextInt();
            System.out.println("Coordinate Y (1-10):");
            y = reader.nextInt();
            reader.nextLine();
            System.out.println("");

            if (x>=1 && y>=1 && x<=10 && y<=10){
                x-=1;
                y-=1;
                print = battleship.addLocateShip(name, x, y, orientation, size);
                if (print.equals("")){
                    System.out.println("That position is occupied or there is no space on the sides");
                }else{
                    System.out.println("The " + name + " has been placed at " + print);
                    break;
                }
            }else{
                System.out.println("Numbers out of range.");
            }
        }
        System.out.println("");
    }

    /**
     * Description: The player tells me the position they want to shoot at, and the turn is carried out turn by turn
     * 
     * @param amount - number of ships
     */
    public void playerShot(){
            while (true){
                System.out.println("Tell me the X coordinate to shoot (1-10)");
                int shotX = reader.nextInt();
                System.out.println("Tell me the Y coordinate to shoot (1-10)");
                int shotY = reader.nextInt();

                if (shotX>=1 && shotX<=10 && shotY>=1 && shotY<=10 ){
                    System.out.println("");
                    System.out.println("You attack the position: row " + shotX + ", column " + shotY);
                    String printPlayer = battleship.shotPlayer(shotX, shotY);
                    System.out.println(printPlayer);
                    break;
                }
                System.out.println("");
            }
    }

    /**
     * Description: Show the victories depending on the game mode
     */
    public void showVictorys(){

        System.out.println("");
        System.out.println("Choose mode the game for show the victories");
        System.out.println("1. Standard Game");
        System.out.println("2. Custom Game");
        int optionGame = 0;
        while(true){
            optionGame = reader.nextInt();
            if (optionGame == 1 || optionGame == 2){
                break;
            }else{
                System.out.println("Number out of range");
            }
        }

        if (optionGame == 1){
            System.out.println("You has won " + playerVictories[0]);
            System.out.println("The CPU has won " + cpuVictories[0]);
        }else{
            System.out.println("The player has won " + playerVictories[1]);
            System.out.println("The CPU has won " + cpuVictories[1]);
        }
    }
}
