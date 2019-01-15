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
public class RedPiece extends GamePiece{
    public RedPiece(int xCoord, int yCoord){
        this.colour = "Black";
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.isKing = false;
    }
}
