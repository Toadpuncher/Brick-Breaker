package com.game.src.main;

import com.game.src.main.classes.EntityPlayer;

import java.awt.*;

public class Player extends GameObject implements EntityPlayer {

    private double velX = 0;
    private Game game;
    private Textures tex;

    public Player(double x,double y,Textures tex,Game game){
        super(x,y);
        this.game = game;
        this.tex = tex;
    }

    public void tick(){
        x+=velX;

        if(x <= 0){
            x = 0;
        }
        if(x >= game.getWidth() - 154){
            x = game.getWidth() - 154;
        }
    }
    public void render(Graphics g){
        g.drawImage(tex.player,(int)x,(int)y,null);
    }
    public Rectangle getBounds(){
        return new Rectangle((int)x,(int)y,154,41);
    }

    public double getX(){
        return x;
    }public double getY(){
        return y;
    }public void setX(double x){
       this.x = x;
    }public void setY(double y){
        this.y = y;
    }
    public void setVelX(double velX){
        this.velX = velX;
    }


}
