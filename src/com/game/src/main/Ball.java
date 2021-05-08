package com.game.src.main;

import com.game.src.main.classes.EntityBall;

import java.awt.*;

public class Ball extends GameObject implements EntityBall {


    private double velX = 0;
    private double velY = 0;
    private Game game;

    private Textures tex;
    public Ball(double x, double y, Textures tex,Game game) {
        super(x, y);
        this.game = game;
        this.tex = tex;

    }

    public void tick() {

        y+=velY;
        x+=velX;

        if(x <= 0){
            x = 0;
            velX = -velX;
        }
        if(x >= game.getWidth() - 33){
            x = game.getWidth() - 33;
            velX = -velX;
        }
        if(y <= 0){
            y = 0;
            velY = -velY;
        }
        if(y >= game.getHeight() - 33){
            game.loseGame();
        }

        if(Physics.Collision(this,game.entityBricks)){
            velY = -velY;
        }
        if(Physics.Collision(this,game.entityPlayer)){
            velY = -velY;
        }


    }

    public Rectangle getBounds(){
        return new Rectangle((int)x,(int)y,33,33);
    }
    public void render(Graphics g) {
        g.drawImage(tex.ball, (int) x, (int) y, null);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getVelY() {
        return velY;
    }

    public double getVelX() {
        return velX;
    }

    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }
    public void setVelX(double velX){
        this.velX = velX;
    }
    public void setVelY(double velY){
        this.velY = velY;
    }
}
