package com.game.src.main;

import com.game.src.main.classes.EntityBricks;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bricks extends GameObject implements EntityBricks {

    private Game game;
    private Textures tex;
    private BufferedImage brick;
    private Controller c;

    public Bricks(double x,double y,Textures tex,Game game,Controller c){
        super(x,y);
        this.game = game;
        this.tex = tex;
        this.c = c;
    }


    public void tick() {


        if(Physics.Collision(this,game.entityBall)){
            c.removeBrick(this);
            game.setBlockBreaken(game.getBlockBreaken()+1);
            game.incScore();
        }

    }
    public Rectangle getBounds(){
        return new Rectangle((int)x,(int)y,85,29);
    }

    public void render(Graphics g) {
       g.drawImage(tex.brick, (int) x, (int) y, null);
   }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public BufferedImage getBrick(){
        return brick;
    }

    public void setBrick(BufferedImage brick){
        this.brick = brick;
    }
}
