/*
 * The GUI class for Checkers
 */
package checkersgame;

import static checkersgame.CheckersGame.blackPieceCount;
import static checkersgame.CheckersGame.blackScore;
import static checkersgame.CheckersGame.redPieceCount;
import static checkersgame.CheckersGame.redScore;
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
    public static JLabel turnLabel = new JLabel();
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
                button.setBorder(null);
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
        playButton.setSize(240, 128); // modify if the image size changes
        playButton.setLocation(800, 50); 
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
        turnLabel.setLocation(800, 200);
        turnLabel.setForeground(Color.GRAY);
        
        turnLabel.setText("Your turn, red!");
        gamePanel.add(turnLabel);
        
        // Displays what the player should do next
        turnPhaseLabel.setSize(250, 100);
        turnPhaseLabel.setFont(new Font("Helvetica",Font.PLAIN, 13));
        turnPhaseLabel.setLocation(800, 180);
        turnPhaseLabel.setForeground(Color.GRAY);
        
        turnPhaseLabel.setText("Select a piece!");
        gamePanel.add(turnPhaseLabel);
        
        // Displays the word "Score"
        scoreLabel.setSize(200, 100);
        scoreLabel.setFont(new Font("Helvetica", Font.PLAIN, 30));
        scoreLabel.setLocation(800, 300);
        scoreLabel.setForeground(Color.GRAY);
        scoreLabel.setText("Score");
        gamePanel.add(scoreLabel);
        
        // Displays Black's score
        blackScoreLabel.setSize(200, 100);
        blackScoreLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        blackScoreLabel.setLocation(800, 350);
        blackScoreLabel.setForeground(Color.BLACK);
        blackScoreLabel.setText(String.format("Black: %s wins", game.blackScore));
        gamePanel.add(blackScoreLabel);
        
        // Displays Red's score
        redScoreLabel.setSize(200, 100);
        redScoreLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        redScoreLabel.setLocation(800, 400);
        redScoreLabel.setForeground(Color.RED);
        redScoreLabel.setText(String.format("Red: %s wins", game.redScore));
        gamePanel.add(redScoreLabel);
        
        // Displays the Give Up button
        giveUpButton.setSize(360, 152); // modify if the image size changes
        giveUpButton.setLocation(800, 550);
        ImageIcon giveUpImage = new ImageIcon("src/giveUpButton.png");
        giveUpButton.setIcon(giveUpImage);
        giveUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giveUpButtonPressed(evt);
            }
        });
        gamePanel.add(giveUpButton);
        giveUpButton.setEnabled(false);
        
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
                button.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 4, false));
            }
            
            //If you click on your own piece it deselects it
            else if(turnStage == 2 && clickedX == x && clickedY == y){
                game.turnStage = 1;
                turnPhaseLabel.setText("Piece deselected...");
                button.setBorder(null);
            }

            //This moves the piece and changes the turn to the next player
            else if(game.turnStage == 2 && clickedX != x && clickedY != y){
                boolean redTurn = game.redTurn;
                
                //TESTS
                //System.out.println("CHOICE Y" + clickedY);
                //System.out.println("CHOICE X" + clickedX);

                
                //Checks to see whos turn it is
                if(redTurn == true){
                    
                    // These are used to store the possible moves of a red piece once it is selected; default of each possible move is not possible (-1,-1)
                    int[] moveAttempt = {x,y};
                    int[] moveOne = {-1,-1};
                    int[] moveTwo = {-1,-1};
                    int[] moveThree = {-1,-1};
                    int[] moveFour = {-1,-1};
                    
                    JButton gamePiece = guiBoard[clickedX][clickedY];
                    ImageIcon gamePieceIcon = (ImageIcon)gamePiece.getIcon();
                    boolean isKing = false;
                    if (gamePieceIcon.getDescription().equals(redKing.getDescription())){
                        isKing = true;
                    }
                    // Prevents comparison of non-existant array elements.
                    if (clickedY > 0 && isKing) {
                        // Prevents comparison of non-existant array elements.
                        if (clickedX > 0) {
                            // Checks the icons of the two squares diagonally up and left and determines if a movement or a jump is possible.
                            ImageIcon upLeft = (ImageIcon)guiBoard[clickedX-1][clickedY-1].getIcon();
                            if (upLeft.getDescription().equals(blackPiece.getDescription()) 
                                    || upLeft.getDescription().equals(blackKing.getDescription())) {
                                // Prevents comparison of non-existant array elements.
                                if (clickedY > 1 && clickedX > 1) {
                                    ImageIcon jumpUpLeft = (ImageIcon)guiBoard[clickedX-2][clickedY-2].getIcon();
                                    if (jumpUpLeft.getDescription().equals(darkCell.getDescription())) {
                                        moveOne = new int[] {clickedX-2,clickedY-2};
                                    }
                                }                            
                            } else if (upLeft.getDescription().equals(darkCell.getDescription())) {
                                moveOne = new int[] {clickedX-1,clickedY-1};
                            }
                        }
                        
                        // Prevents comparison of non-existant array elements.
                        if (clickedX < 7) {
                            // Checks the icons of the two squares diagonally up and right and determines if a movement or a jump is possible.
                            ImageIcon upRight = (ImageIcon)guiBoard[clickedX+1][clickedY-1].getIcon();
                            if (upRight.getDescription().equals(blackPiece.getDescription()) 
                                    || upRight.getDescription().equals(blackKing.getDescription())) {
                                // Prevents comparison of non-existant array elements.
                                if (clickedY > 1 && clickedX < 6) {
                                    ImageIcon jumpUpRight = (ImageIcon)guiBoard[clickedX+2][clickedY-2].getIcon();
                                    if (jumpUpRight.getDescription().equals(darkCell.getDescription())) {
                                        moveTwo = new int[] {clickedX+2,clickedY-2};
                                    }
                                }  
                            } else if (upRight.getDescription().equals(darkCell.getDescription())) {
                            moveTwo = new int[] {clickedX+1,clickedY-1};
                            }
                        }
                        
                    }
                    
                    // Prevents comparison of non-existant array elements.
                    if (clickedY < 7) {
                        // Prevents comparison of non-existant array elements.
                        if (clickedX > 0) {
                            // Checks the icons of the two squares diagonally down and left and determines if a movement or a jump is possible.
                            ImageIcon downLeft = (ImageIcon)guiBoard[clickedX-1][clickedY+1].getIcon();
                            if (downLeft.getDescription().equals(blackPiece.getDescription()) 
                                    || downLeft.getDescription().equals(blackKing.getDescription())) {
                                // Prevents comparison of non-existant array elements.
                                if (clickedX > 1 && clickedY < 6 ) {
                                    ImageIcon jumpDownLeft = (ImageIcon)guiBoard[clickedX-2][clickedY+2].getIcon();
                                    if (jumpDownLeft.getDescription().equals(darkCell.getDescription())) {
                                        moveThree = new int[] {clickedX-2,clickedY+2};
                                    }
                                }
                                
                            } else if (downLeft.getDescription().equals(darkCell.getDescription())) {
                                moveThree = new int[] {clickedX-1,clickedY+1};
                            }
                        }
                        
                        // Prevents comparison of non-existant array elements.
                        if (clickedX < 7) {
                            // Checks the icons of the two squares diagonally down and right and determines if a movement or a jump is possible.
                            ImageIcon downRight = (ImageIcon)guiBoard[clickedX+1][clickedY+1].getIcon();
                            if (downRight.getDescription().equals(blackPiece.getDescription()) 
                                    || downRight.getDescription().equals(blackKing.getDescription())) {
                                // Prevents comparison of non-existant array elements.
                                if (clickedX < 6 && clickedY < 6) {
                                    ImageIcon jumpDownRight = (ImageIcon)guiBoard[clickedX+2][clickedY+2].getIcon();
                                    if (jumpDownRight.getDescription().equals(darkCell.getDescription())) {
                                        moveFour = new int[] {clickedX+2,clickedY+2};
                                    }
                                }                                
                            } else if (downRight.getDescription().equals(darkCell.getDescription())) {
                                moveFour = new int[] {clickedX+1,clickedY+1};
                            }
                        }
                        
                    }
                    
                    
                    //Checks to see if inputed movement is invalid
                    if(!Arrays.equals(moveAttempt,moveOne) && !Arrays.equals(moveAttempt,moveTwo) 
                            && !Arrays.equals(moveAttempt,moveThree) && !Arrays.equals(moveAttempt,moveFour)){
                        turnPhaseLabel.setText(turnPhaseLabel.getText() + " - invalid move");
                        System.out.println("Cannot move piece to this location!");
                        System.out.println(Arrays.toString(moveAttempt));
                        
                        
                    }
                    //Moves piece if everything is chosen correctly
                    else{
                        // Changes piece to king if far side is reached.   
                        if (y == 7){
                            gamePiece.setIcon(redKing);
                        }
                        gamePiece.setBorder(null);
                        movePiece(clickedX,clickedY,x,y);
                        System.out.println(clickedX + "," + clickedY + "-->" + x + " " + y);
                        // Eliminates the piece in between the start and finish location if a jump is made.
                        if (Math.abs(clickedX - x) == 2) eliminate((clickedX + x)/2, (clickedY + y)/2);
                        
                        
                        //Resets everything for the next players turn
                        game.nextTurn();
                        game.turnStage = 1;
                        System.out.println("Now blacks turn!");
                        turnPhaseLabel.setText("Select a piece!");
                    }
                }
                else if (redTurn == false){
                    
                    // These are used to store the possible moves of a red piece once it is selected; default of each possible move is not possible (-1,-1)
                    int[] moveAttempt = {x,y};
                    int[] moveOne = {-1,-1};
                    int[] moveTwo = {-1,-1};
                    int[] moveThree = {-1,-1};
                    int[] moveFour = {-1,-1};
                    
                    JButton gamePiece = guiBoard[clickedX][clickedY];
                    ImageIcon gamePieceIcon = (ImageIcon)gamePiece.getIcon();
                    boolean isKing = false;
                    if (gamePieceIcon.getDescription().equals(blackKing.getDescription())){
                        isKing = true;
                    }
                    
                    // Prevents comparison of non-existant array elements.
                    if (clickedY > 0) {
                        // Prevents comparison of non-existant array elements.
                        if (clickedX > 0) {
                            // Checks the icons of the two squares diagonally up and left and determines if a movement or a jump is possible.
                            ImageIcon upLeft = (ImageIcon)guiBoard[clickedX-1][clickedY-1].getIcon();
                            if (upLeft.getDescription().equals(redPiece.getDescription()) 
                                    || upLeft.getDescription().equals(redKing.getDescription())) {
                                // Prevents comparison of non-existant array elements.
                                if (clickedY > 1 && clickedX > 1) {
                                    ImageIcon jumpUpLeft = (ImageIcon)guiBoard[clickedX-2][clickedY-2].getIcon();
                                    if (jumpUpLeft.getDescription().equals(darkCell.getDescription())) {
                                        moveOne = new int[] {clickedX-2,clickedY-2};
                                    }
                                }                            
                            } else if (upLeft.getDescription().equals(darkCell.getDescription())) {
                                moveOne = new int[] {clickedX-1,clickedY-1};
                            }
                        }
                        
                        // Prevents comparison of non-existant array elements.
                        if (clickedX < 7) {
                            // Checks the icons of the two squares diagonally up and right and determines if a movement or a jump is possible.
                            ImageIcon upRight = (ImageIcon)guiBoard[clickedX+1][clickedY-1].getIcon();
                            if (upRight.getDescription().equals(redPiece.getDescription()) 
                                    || upRight.getDescription().equals(redKing.getDescription())) {
                                // Prevents comparison of non-existant array elements.
                                if (clickedY > 1 && clickedX < 6) {
                                    ImageIcon jumpUpRight = (ImageIcon)guiBoard[clickedX+2][clickedY-2].getIcon();
                                    if (jumpUpRight.getDescription().equals(darkCell.getDescription())) {
                                        moveTwo = new int[] {clickedX+2,clickedY-2};
                                    }
                                }  
                            } else if (upRight.getDescription().equals(darkCell.getDescription())) {
                            moveTwo = new int[] {clickedX+1,clickedY-1};
                            }
                        }
                        
                    }
                    
                    // Prevents comparison of non-existant array elements.
                    if (clickedY < 7 && isKing) {
                        // Prevents comparison of non-existant array elements.
                        if (clickedX > 0) {
                            // Checks the icons of the two squares diagonally down and left and determines if a movement or a jump is possible.
                            ImageIcon downLeft = (ImageIcon)guiBoard[clickedX-1][clickedY+1].getIcon();
                            if (downLeft.getDescription().equals(redPiece.getDescription()) 
                                    || downLeft.getDescription().equals(redKing.getDescription())) {
                                // Prevents comparison of non-existant array elements.
                                if (clickedX > 1 && clickedY < 6 ) {
                                    ImageIcon jumpDownLeft = (ImageIcon)guiBoard[clickedX-2][clickedY+2].getIcon();
                                    if (jumpDownLeft.getDescription().equals(darkCell.getDescription())) {
                                        moveThree = new int[] {clickedX-2,clickedY+2};
                                    }
                                }
                                
                            } else if (downLeft.getDescription().equals(darkCell.getDescription())) {
                                moveThree = new int[] {clickedX-1,clickedY+1};
                            }
                        }
                        
                        // Prevents comparison of non-existant array elements.
                        if (clickedX < 7) {
                            // Checks the icons of the two squares diagonally down and right and determines if a movement or a jump is possible.
                            ImageIcon downRight = (ImageIcon)guiBoard[clickedX+1][clickedY+1].getIcon();
                            if (downRight.getDescription().equals(redPiece.getDescription()) 
                                    || downRight.getDescription().equals(redKing.getDescription())) {
                                // Prevents comparison of non-existant array elements.
                                if (clickedX < 6 && clickedY < 6) {
                                    ImageIcon jumpDownRight = (ImageIcon)guiBoard[clickedX+2][clickedY+2].getIcon();
                                    if (jumpDownRight.getDescription().equals(darkCell.getDescription())) {
                                        moveFour = new int[] {clickedX+2,clickedY+2};
                                    }
                                }                                
                            } else if (downRight.getDescription().equals(darkCell.getDescription())) {
                                moveFour = new int[] {clickedX+1,clickedY+1};
                            }
                        }
                        
                    }

                    if(!Arrays.equals(moveAttempt,moveOne) && !Arrays.equals(moveAttempt,moveTwo) 
                            && !Arrays.equals(moveAttempt,moveThree) && !Arrays.equals(moveAttempt,moveFour)){
                        turnPhaseLabel.setText(turnPhaseLabel.getText() + " - invalid move");
                        System.out.println("Cannot move piece to this location!");
                    }
                    //Moves piece if everything is chosen correctly
                    else{
                        // Changes piece to king if far side is reached.   
                        if (y == 0){
                            gamePiece.setIcon(blackKing);
                        }
                        gamePiece.setBorder(null);
                        movePiece(clickedX,clickedY,x,y);
                        System.out.println(clickedX + "," + clickedY + "-->" + x + " " + y);
                        if (Math.abs(clickedX - x) == 2) eliminate((clickedX + x)/2, (clickedY + y)/2);
                        
                        
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
        
        if (redPieceCount == 0){
            game.blackScore++;
            turnLabel.setText("Black Wins!");
            endGame();
            System.out.println("increased black score");
        } else if (blackPieceCount == 0){
            game.redScore++;
            turnLabel.setText("Red Wins!");
            redScoreLabel.setText("Red: " + game.redScore + " wins");
            endGame();
            System.out.println("increased red score");
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
        giveUpButton.setEnabled(true);
        turnLabel.setText("Your turn, red!");
        turnPhaseLabel.setText("Select a piece!");
        resetBoard();
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
            turnLabel.setText("Red wins!");
            redScoreLabel.setText("Red: " + game.redScore + " wins");
        }
        else if(game.redTurn == true){
            game.blackScore++;
            turnLabel.setText("Black wins!");
            blackScoreLabel.setText("Black: " + game.blackScore + " wins");
        }
        endGame();
        game.nextTurn();
        game.reset();
    }
    
    /**
     * Describes the event of the game ending.
     */
    private void endGame(){
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                guiBoard[i][j].setEnabled(false);
            }     
        }
        giveUpButton.setEnabled(false);
        playButton.setEnabled(true);
        turnPhaseLabel.setText("");
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
        if (game.redTurn == true && click.getDescription().equals(blackKing.getDescription())){
            return false;
        }
        if(game.redTurn == false && click.getDescription().equals(redPiece.getDescription())){
           // System.out.println("RETURNED FALSE IN VALIDITY CHECK - blackturn/redpiece");
            return false;
        }
        if(game.redTurn == false && click.getDescription().equals(redKing.getDescription())){
            return false;
        }
      //  System.out.println("RETURNED TRUE IN VALIDITY CHECK");
        return true;
    }
    
    /**
     * 'Moves' the piece from location (oldXCoord, oldYCoord) to new location (newXCoord, newYCoord).
     * @param oldXCoord
     * @param oldYCoord
     * @param newXCoord
     * @param newYCoord 
     */
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
     * Removes the piece at the target location given by the input coordinates.
     * @param targetX
     * @param targetY 
     */
    private void eliminate(int targetX, int targetY){
        JButton target = guiBoard[targetX][targetY];
        target.setIcon(darkCell);
        if (game.redTurn == true){
            blackPieceCount--;
            System.out.println(CheckersGame.blackPieceCount);
        } else if (game.redTurn == false){
            redPieceCount--;
            System.out.println(CheckersGame.redPieceCount);
        }  
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
