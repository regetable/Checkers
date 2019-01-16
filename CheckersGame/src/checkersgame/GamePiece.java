/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersgame;
import java.awt.Color;
import java.util.Arrays;
import javax.swing.JButton;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
/**
 *
 * @author caydenferguson
 */
public abstract class GamePiece {
    protected int xCoord;
    protected int yCoord;
    protected boolean inPlay;
    protected boolean isKing;
    protected String colour;
    protected ImageIcon sprite;
    
    /**
     * Used to determine if a piece is a king or not
     * @param peice a piece on the board
     * @return true if the piece is a king else false
     */
    public boolean isKing(GamePiece peice){
        return this.isKing;
    }
    /**
     * Used to determine a pieces colour
     * @param peice a piece on the board
     * @return a colour in the form of a String
     */
    public String getColour(GamePiece peice){
        return this.colour;
    }
}
