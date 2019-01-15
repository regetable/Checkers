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
     * 
     * @param peice 
     */
    public void isKing(GamePiece peice){
        
    }
    /**
     * 
     * @param peice
     * @return 
     */
    public String getColour(GamePiece peice){
        return "";
    }
}
