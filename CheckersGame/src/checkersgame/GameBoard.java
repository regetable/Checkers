/*
 * The GUI class for Checkers
 */
package checkersgame;

import java.util.Arrays;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

/**
 *
 * @author TeamLime
 */

public class GameBoard extends JFrame{
    public int clickedX;
    public int clickedY;
        
    public boolean gameStarted = false;
    
    ImageIcon redPiece = new ImageIcon("src/redPieceWithBackground.png");
    ImageIcon blackPiece = new ImageIcon("src/blackPieceWithBackground.png");
    ImageIcon darkCell = new ImageIcon("src/darkCell.png");
    ImageIcon lightCell = new ImageIcon("src/lightCell.png");
    ImageIcon redKing = new ImageIcon("src/redKingWithBackground.png");
    ImageIcon blackKing = new ImageIcon("src/blackKingWithBackground.png");
    
    // The 2D array of buttons
    JButton[][] guiBoard = new JButton[8][8];
    
    // The panel that has all the buttons and labels
    JPanel gamePanel = new JPanel();
    
    // The CheckersGame that has the information about the current game
    CheckersGame game = new CheckersGame();
    
    // Whose turn it is: "Black" (first player) or "Red" (second player)
   // String turn; 
    
    // The buttons on the board
    JButton playButton = new JButton(); 
    JButton giveUpButton = new JButton();
            
    // The labels on the board
    JLabel turnLabel = new JLabel();
    JLabel scoreLabel = new JLabel();
    JLabel blackScoreLabel = new JLabel();
    JLabel redScoreLabel = new JLabel();
    JLabel turnPhaseLabel = new JLabel();
    
    /**
     * Creates a new GameBoard form
     */
    public GameBoard() {
        // Sets the background of the board to white
        gamePanel.setLayout(null);
        gamePanel.setBackground(Color.WHITE);
       
        // Adds all 64 buttons that make up the cells of the game board
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                JButton button = new JButton();
                ImageIcon image;
                if ((i + j) % 2 == 0) {
                    image = new ImageIcon("src/darkCell.png"); 
                } else {
                    image = new ImageIcon("src/lightCell.png");
                }
                button.setEnabled(false);
                // Adds button to the 2D array of buttons
                guiBoard[i][j] = button;
                button.setIcon(image);
                button.setSize(92, 92);
                // Places the button at the correct location on the game board
                button.setLocation(92*i, 92*j);
                // Connects the action listener: buttonPressed()
                button.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        buttonPressed(evt);
                        
                        
                    }
                });
                gamePanel.add(button);
            }
        }
        
        // Displays the play button
        playButton.setSize(163, 79); // modify if the image size changes
        playButton.setLocation(887, 50); 
        ImageIcon playImage = new ImageIcon("src/playButton.png");
        playButton.setIcon(playImage);
        // Connects the action listener
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonPressed(evt);  
            }
        });
        gamePanel.add(playButton);
        
        // Displays "Your turn, [insert colour]!"
        turnLabel.setSize(200, 100);
        turnLabel.setFont(new Font("Helvetica", Font.ITALIC, 20));
        turnLabel.setLocation(887, 150);
        turnLabel.setForeground(Color.GRAY);
        
        turnLabel.setText("Your turn, red!");
        gamePanel.add(turnLabel);
        
        // Displays what the player should do next
        turnPhaseLabel.setSize(250, 100);
        turnPhaseLabel.setFont(new Font("Helvetica",Font.PLAIN, 13));
        turnPhaseLabel.setLocation(887, 180);
        turnPhaseLabel.setForeground(Color.GRAY);
        
        turnPhaseLabel.setText("Select a piece!");
        gamePanel.add(turnPhaseLabel);
        
        // Displays the word "Score"
        scoreLabel.setSize(200, 100);
        scoreLabel.setFont(new Font("Helvetica", Font.PLAIN, 30));
        scoreLabel.setLocation(887, 250);
        scoreLabel.setForeground(Color.GRAY);
        scoreLabel.setText("Score");
        gamePanel.add(scoreLabel);
        
        // Displays Black's score
        blackScoreLabel.setSize(200, 100);
        blackScoreLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        blackScoreLabel.setLocation(887, 350);
        blackScoreLabel.setForeground(Color.BLACK);
        blackScoreLabel.setText(String.format("Black: %s wins", game.blackScore));
        gamePanel.add(blackScoreLabel);
        
        // Displays Red's score
        redScoreLabel.setSize(200, 100);
        redScoreLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        redScoreLabel.setLocation(887, 450);
        redScoreLabel.setForeground(Color.RED);
        redScoreLabel.setText(String.format("Red: %s wins", game.redScore));
        gamePanel.add(redScoreLabel);
        
        // Displays the Give Up button
        giveUpButton.setSize(236, 91); // modify if the image size changes
        giveUpButton.setLocation(850, 600);
        ImageIcon giveUpImage = new ImageIcon("src/giveUpButton.png");
        giveUpButton.setIcon(giveUpImage);
        giveUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giveUpButtonPressed(evt);
            }
        });
        gamePanel.add(giveUpButton);
        
        // Do not modify unless otherwise indicated!
        this.add(gamePanel);
        this.setSize(1200, 758); // modifiable
        this.setResizable(false);
        this.setTitle("Retro Checkers"); // modifiable
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        resetBoard();
    }
    
    /**
     * Prints the coordinates of the button pressed to the console.
     * @param evt 
     */
    private void buttonPressed(java.awt.event.ActionEvent evt) {
        //Need to make it so player cannot choose to move pieces not of his/her colour
        //Need to restrict movement to only forward
        //Need to unlock movement for kings
        JButton button = (JButton)evt.getSource();
        int[] coord = getCoordinates(button);
        int x = coord[0];
        int y = coord[1];
        
        if(validSelection(x,y) == true){
        
            int turnStage = game.turnStage;


            //Prints buttons coords to console
            System.out.println(Arrays.toString(getCoordinates(button)) + " has been pressed.");

            //If player is in piece selection stage
            if (turnStage == 1){
                
                //Saves the coords on the selected piece for movement in stage 2
                clickedX = x;
                clickedY = y;
                
                game.turnStage = 2;
                turnPhaseLabel.setText("Moving piece...");
            }
            
            //If you click on your own piece it deselects it
            else if(turnStage == 2 && clickedX == x && clickedY == y){
                game.turnStage = 1;
                turnPhaseLabel.setText("Piece deselected...");
            }

            //This moves the piece and changes the turn to the next player
            else if(game.turnStage == 2 && clickedX != x && clickedY != y){
                boolean redTurn = game.redTurn;
                
                //TESTS
                //System.out.println("CHOICE Y" + clickedY);
                //System.out.println("CHOICE X" + clickedX);

                
                //Checks to see whos turn it is
                if(redTurn == true){
                    
                    //These are all the possible x & y coords for reds movement
                    //PUT A KING IF STATEMENT HERE WHEN KING IS ACTIVATED
                    int xMove1 = clickedX+1;
                    int xMove2 = clickedX-1;
                    int yMove = clickedY+1;
                    
                    //Checks to see if inputed movement is invalid
                    if(x != xMove1 && x != xMove2 || y != yMove){
                        turnPhaseLabel.setText(turnPhaseLabel.getText() + " - invalid move");
                        System.out.println("Cannot move piece to this location!");
                    }
                    //Moves piece if everything is chosen correctly
                    else{
                        movePiece(clickedX,clickedY,x,y);
                        System.out.println(clickedX + "," + clickedY + "-->" + x + " " + y);
                        
                        //Resets everything for the next players turn
                        game.nextTurn();
                        game.turnStage = 1;
                        System.out.println("Now blacks turn!");
                        turnPhaseLabel.setText("Select a piece!");
                    }
                }
                else if (redTurn == false){
                    int xMove1 = clickedX+1;
                    int xMove2 = clickedX-1;
                    int yMove = clickedY-1;
                    
                    //These are all the possible x & y coords for reds movement
                    //PUT A KING IF STATEMENT HERE WHEN KING IS ACTIVATED
                    if(x != xMove1 && x != xMove2 || y != yMove){
                        turnPhaseLabel.setText(turnPhaseLabel.getText() + " - invalid move");
                        System.out.println("Cannot move piece to this location!");
                    }
                    //Moves piece if everything is chosen correctly
                    else{
                        movePiece(clickedX,clickedY,x,y);
                        System.out.println(clickedX + "," + clickedY + "-->" + x + " " + y);
                        
                        //Resets everything for next players turn
                        game.nextTurn();
                        game.turnStage = 1;
                        System.out.println("Now reds turn!");
                        turnPhaseLabel.setText("Select a piece!");
                    }
                }
            }
        }
        else{
            turnPhaseLabel.setText(turnPhaseLabel.getText() + " invalid move");
            System.out.println("NO MOVEMENT OCCURED");
        }
        
        //Updates label
        if(game.redTurn == true){
           turnLabel.setText("Your turn, red!");
        }
        else{
            turnLabel.setText("Your turn, black!");
        }
        
        
        
    }
    
    /**
     * Prints "Play has been pressed." to the console.
     * @param evt 
     */
    private void playButtonPressed(java.awt.event.ActionEvent evt) {
        System.out.println("Play has been pressed.");
        gameStarted = true;
        playButton.setEnabled(false);
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                guiBoard[i][j].setEnabled(true);
            }
        }
    }
    
    /**
     * Prints "Give Up has been pressed." to the console.
     * @param evt 
     */
    private void giveUpButtonPressed(java.awt.event.ActionEvent evt) {
        System.out.println("Give Up has been pressed.");
        if(game.redTurn == false){
            game.redScore++;
            redScoreLabel.setText("Red: " + game.redScore + " wins");
        }
        else if(game.redTurn == true){
            game.blackScore++;
            blackScoreLabel.setText("Black: " + game.blackScore + " wins");
        }
        resetBoard();
        turnLabel.setText("Your turn, red!");
        turnPhaseLabel.setText("Select a piece!");
        game.nextTurn();
        game.reset();
    }
    
    /**
     * Finds the coordinates of the button using guiBoard.
     * @param button
     * @return the coordinates of the button as an int array with 2 elements
     */
    private int[] getCoordinates(JButton button) {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if (guiBoard[i][j] == button) {
                    int[] coordinates = {i, j};
                    return coordinates;
                }
            }
        }
        int[] coordinates = {-1, -1};
        return coordinates;
    }
    
    /**
     * Changes the image on the button at the given coordinates to piece 
     * of the given colour.
     * @param xCoord the x-coordinate of the location (0..8)
     * @param yCoord the y-coordinate of the location (0..8)
     * @param colour black, red, or blank
     */
    private void putPiece(int xCoord, int yCoord, String colour) {
        ImageIcon image;
        if (colour.equals("blank")) {
            image = new ImageIcon("src/darkCell.png");
        } else {
            image = new ImageIcon(String.format("src/%sPieceWithBackground.png", colour));
        }
        JButton button = guiBoard[xCoord][yCoord];
        button.setIcon(image);
    }
    
    /**
     * Changes the image on the buttons at the two locations. 
     * The current location gets changed to a blank piece, and the
     * new location gets changed to the piece that was at the old location.
     * Prints anything suspicious to the console.
     * @param oldXCoord the x-coordinate of the old location (0..8)
     * @param oldYCoord the y-coordinate of the old location (0..8)
     * @param newYCoord the x-coordinate of the new location (0..8)
     * @param newYCoord the y-coordinate of the new location (0..8)
     */
    
    private boolean validSelection(int x, int y){
        ImageIcon click = (ImageIcon)guiBoard[x][y].getIcon();
        
        if(game.turnStage == 1 && click.getDescription().equals(lightCell.getDescription())){
          //  System.out.println("RETURNED FALSE IN VALIDITY CHECK - lightcell/darkcell");
            return false;
        }
        if(game.turnStage == 1 && click.getDescription().equals(darkCell.getDescription())){
        //    System.out.println("RETURNED FALSE IN VALIDITY CHECK - lightcell/darkcell");
            return false;
        }
        if(game.redTurn == true && click.getDescription().equals(blackPiece.getDescription())){
          //  System.out.println("RETURNED FALSE IN VALIDITY CHECK - redturn/blackpiece");
            return false;
        }
        if(game.redTurn == false && click.getDescription().equals(redPiece.getDescription())){
           // System.out.println("RETURNED FALSE IN VALIDITY CHECK - blackturn/redpiece");
            return false;
        }
      //  System.out.println("RETURNED TRUE IN VALIDITY CHECK");
        return true;
    }
    private void movePiece(int oldXCoord, int oldYCoord, int newXCoord, int newYCoord) {
        JButton oldButton = guiBoard[oldXCoord][oldYCoord];        
        JButton newButton = guiBoard[newXCoord][newYCoord];
        
        ImageIcon oldLocation = (ImageIcon)oldButton.getIcon();
        ImageIcon newLocation = (ImageIcon)newButton.getIcon();
       // ImageIcon blankImage = new ImageIcon("src/darkCell.png");
        
        // Prints messages for cases that shouldn't happen
        if ((oldXCoord + oldYCoord) % 2 == 1) {
            System.out.println("The old location is a light cell.");
        } else if (oldLocation.getDescription().equals(lightCell.getDescription())) {
            System.out.println("The old location is blank.");
        }
        if ((newXCoord + newYCoord) % 2 == 1) {
            System.out.println("The new location is a light cell.");
        } else if (!newLocation.getDescription().equals(lightCell.getDescription())) {
            System.out.println("The new location is not blank.");
        } 
        
        oldButton.setIcon(darkCell);
        newButton.setIcon(oldLocation);
    }
    /**
     * 
     * @param x moving piece x
     * @param y moving piece y
     * @param x1 potential new x 1
     * @param x2 potential new x 2
     * @param y1 new y
     * @return 
     */
    private boolean eliminationCheck(int x1, int y1, int x2, int y2, boolean redTurn, boolean moveRight){
        
//        ImageIcon potentialPiece = (ImageIcon)guiBoard[x1][y1].getIcon();
//        ImageIcon jump = (ImageIcon)guiBoard[x2][y2].getIcon();
//        
//        if(potentialPiece.getDescription().equals(redPiece.getDescription()) || potentialPiece.getDescription().equals(blackPiece.getDescription())){
//            if(moveRight == true){
//                if(guiBoard[x1])
//            }
//            else{
//                
//            }
//        }
//        else{
//            return false;
//        }
//        
////        if(guiBoard[x1][y1]){
////            
////        }
//        if(game.redTurn == true){
//           if(move1.getDescription().equals(redPiece.getDescription())){
//                coords[0] = 8;
//                coords[1] = 8;
//                //Own piece found
//                return coords;
//            }
//            else if(move2.getDescription().equals(redPiece.getDescription())){
//                coords[0] = 8;
//                coords[1] = 8;
//                //Own piece found
//                return coords;
//            }
//            else{
//                
//            }
//        }
//        else{
//            if(move1.getDescription().equals(blackPiece.getDescription()) && game.redTurn == true){
//            return coords;
//            }
//            if(move1.getDescription().equals(blackPiece.getDescription()) && game.redTurn == true){
//            return coords;
//            }
//        }
//        if(move1.getDescription().equals(redPiece.getDescription()) && game.redTurn == false){
//            return coords;
//        }
//        if(move2.getDescription().equals(redPiece.getDescription()) && game.redTurn == true){
//            coords[0] = 8;
//            coords[1] = 8;
//            //Own piece found
//            return coords;
//        }
//        if(move1.getDescription().equals(blackPiece.getDescription()) && game.redTurn == true){
//            return coords;
//        }
//        if(move2.getDescription().equals(blackPiece.getDescription()) && game.redTurn == false){
//            coords[0] = 8;
//            coords[1] = 8;
//            //Own piece found
//            return coords;
//        }
        
    return true;   
    }
    
    /**
     * Changes the images on the buttons of the game board to match the initial
     * state of the game.
     */
    private void resetBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 3; j++) {
                if ((i + j) % 2 == 0) {
                    putPiece(i, j, "red");
                }
            }
            for (int k = 5; k < 8; k++) {
                if ((i + k) % 2 == 0) {
                    putPiece(i, k, "black");
                }
            }
            for (int h = 3; h < 5; h++) {
                if ((i + h) % 2 == 0) {
                    putPiece(i, h, "blank");
                }
            }
        }
    }

    /**
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       // GameBoard game = new GameBoard();
        
    }
}
