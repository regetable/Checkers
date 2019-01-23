/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersgame;

/**
 *
 * @author caydenferguson
 */
public class CheckersGame {
    
    public boolean redTurn = false;
    public boolean firstMove = true;
    public static int blackScore = 0;
    public static int redScore = 0;
    public boolean isSelected = false;
    public static int blackPieceCount = 12;
    public static int redPieceCount = 12;
    public int xChoice;
    public int yChoice;
    

    public CheckersGame(){
    }

    /**
     *Resets the game
     */
    public void reset(){
        isSelected = false;
        blackPieceCount = 12;
        redPieceCount = 12;
        redTurn = false;
    }

    /**
     * Changes to the next players turn
     */
    public void nextTurn(){
        if (redTurn == true){
            redTurn =false;
        }
        else if(redTurn == false){
            redTurn = true;
        }
    }

    /**
     * Changes to the next stage in a turn (moving a piece)
     * @param stage 
     */
    public void nextStage(String stage){
        if (isSelected == false){
            isSelected = true;
            System.out.println("Next turn stage");
        }
        else{
            System.out.println("You are at the last stage");
            //This should never display
        }
    }

    /**
     * Changes to the previous stage in a turn (choosing a piece)
     * @param stage 
     */
    public void previousStage(String stage){

        if(isSelected == true){
            isSelected = false;
            System.out.println("Previous turn stage");
        }
        else{
            System.out.println("You are at the first turn stage");
            //This should never display
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new GameBoard().setVisible(true);
        
        //Makes the Instructions visible on startup and centered
        Instructions rules = new Instructions();
        rules.setLocationRelativeTo(null);
        rules.setVisible(true);
    }
    
}
