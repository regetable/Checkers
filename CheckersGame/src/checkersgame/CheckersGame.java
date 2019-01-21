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
    
    public boolean redTurn = true;
    public int blackScore = 0;
    public int redScore = 0;
    public int turnStage = 1;
    public int blackPieceCount = 8;
    public int redPieceCount = 8;
    public int xChoice;
    public int yChoice;
    

    public CheckersGame(){

    }

    /**
     * Removes a piece from play
     * @param piece 
     */
    public void eliminate(GamePiece piece){

    }

    /**
     *Resets the game
     */
    public void reset(){
        turnStage = 1;
        blackPieceCount = 8;
        redPieceCount = 8;
        redTurn = true;
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
     * Displays winning message
     * 
     */
    public void win(){
        if (redPieceCount == 0){
            blackScore++;
            System.out.println("increased black score");
        }
        else if (blackPieceCount == 0){
            redScore++;
            System.out.println("increased red score");
        }
        else{
            System.out.println("No one has won!");
            //This should never display
        }
    }

    /**
     * Changes to the next stage in a turn (moving a piece)
     * @param stage 
     */
    public void nextStage(String stage){
        if (turnStage < 2 && turnStage > 0){
            turnStage++;
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

        if(turnStage > 1 && turnStage < 3){
            turnStage--;
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
        
    }
    
}
